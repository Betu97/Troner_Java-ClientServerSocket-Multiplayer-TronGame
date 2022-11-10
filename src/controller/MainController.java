package controller;

import controller.round.RoundThread;
import model.*;
import model.database.DBConnector;
import model.database.UserDAO;
import network.*;
import model.exceptions.IncorrectEmailFormatException;
import model.exceptions.IncorrectPasswordFormatException;
import model.exceptions.IncorrectUsernameFormatException;
import view.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Controlador principal del programa
 * escolta les peticions que es fan a la vista i executa les funcions que pertoquin
 * @author Team Troner (grupC3)
 * @version v_final
 */

public class MainController implements ActionListener {
    // Relació amb el model
    private Playroom model;
    // Relació amb la vista
    private MainWindow view;
    private Server server;
    private DBConnector dbConnector;

    // Threads que mantenen vius els jocs (els guardem com a referència per si els hem d'aturar)
    private Map<Round, RoundThread> gameThreads;

    /**
     * Constructor maincontroller, entre altres coses, s'ocuparà de crear la relació controlador->model,
     * controlador->vista
     */
    public MainController(Playroom model, MainWindow view) {
        // S'estableix la relació C->M
        this.model = model;
        // S'estableix la relació C->V
        this.view = view;

        // Inicialitzem el HashMap que guardarà els Threads que mantenen vius els jocs (els guardem com a referència per si els hem d'aturar)
        gameThreads = new HashMap<>();

        // Ens connectem a la BD
        try {
            this.dbConnector = new DBConnector(ConfFileManager.loadConfiguration().get(0), Integer.parseInt(ConfFileManager.loadConfiguration().get(3)), ConfFileManager.loadConfiguration().get(4), ConfFileManager.loadConfiguration().get(5), ConfFileManager.loadConfiguration().get(2));
            dbConnector.startConnection();
        }
        catch (Exception e) {
            // El servidor no ha pogut connectar-se a la base de dades
            // Cal avisar a l'usuari i demanar-li que torni a obrir el programa
            JOptionPane.showMessageDialog(this.view, "ERROR: No s'ha pogut establir una connexió amb la base de dades MySQL. Comprova que MySQL s'estigui executant i reinicia l'aplicació.", "Troner", JOptionPane.ERROR_MESSAGE);
            // Sortim del programa
            System.out.print(e.getMessage());
            System.exit(1);
        }

        // Mostrem la informació inicial
        try {
            this.view.refreshUsersList(UserDAO.getUsersList());
            this.view.refreshRankingList(UserDAO.getScoreList());
            this.view.refreshGraphic(UserDAO.getGraphicList());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Playroom getModel() {
        return model;
    }

    public MainWindow getView() {
        return view;
    }

    public void setModel(Playroom model) {
        this.model = model;
    }

    public void setView(MainWindow view) {
        this.view = view;
    }

    public Map<Round, RoundThread> getGameThreads() {
        return gameThreads;
    }

    public void setGameThreads(Map<Round, RoundThread> gameThreads) {
        this.gameThreads = gameThreads;
    }

    @Override
    /**
     * mètode que s'exexuta cada vegada que la vista genera un esdeveniment
     * @param event conté l'identificador de l'esdeveniment que en permetrà realitzar l'acció corresponent
     */
    public void actionPerformed(ActionEvent event) {

        // Entrem en aquest mètode perquè la vista ha generat un esdeveniment que ha de gestionar aquest controlador
        // Obtenim l'identificador de l'esdeveniment generat per tal de gestionar-lo d'una manera o d'una altra

        switch (event.getActionCommand()) {

            case "saveConfigServer":

                // Guardem la configuració amb el nou port
                if (view.getJpConfiguration().getEnteredDataConfiguration().size() < 1) {
                    JOptionPane.showMessageDialog(view, "ERROR: El format del port introduït no és correcte.", "Troner", JOptionPane.ERROR_MESSAGE);

                }
                else {
                    ConfFileManager.saveConfiguration(ConfFileManager.fromString(view.getJpConfiguration().getEnteredDataConfiguration()));

                    // Avisem a l'usuari de que cal iniciar / reiniciar el servidor per a aplicar els canvis
                    if (this.server != null && this.server.isAlive()) {
                        // El servidor està activat
                        // Per tant, diem "reiniciar"
                        JOptionPane.showMessageDialog(view, "Configuració guardada. Per a aplicar la nova configuració cal reiniciar el servidor.", "Troner", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else {
                        // El servidor NO està activat
                        // Per tant, diem "iniciar"
                        JOptionPane.showMessageDialog(view, "Configuració guardada. Per a aplicar la nova configuració cal iniciar el servidor.", "Troner", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                break;

            case "connectServer":

                // En primer lloc comprovem si el servidor es troba ja iniciat. Si ja es troba iniciat avisem a l'usuari i abandonem
                if (this.server != null && this.server.isAlive()) {
                    JOptionPane.showMessageDialog(view, "ERROR: No s'ha pogut iniciar el servidor perquè ja es troba iniciat.", "Troner", JOptionPane.ERROR_MESSAGE);
                    break;
                }

                // Creem el servidor
                this.server = new Server(this, model, view, Integer.parseInt(ConfFileManager.loadConfiguration().get(1)));

                // Iniciem el servidor
                this.server.startServer();

                // Ara que el servidor ja està activat a cal fer-ho evident en la UI (User Interface)
                // Per a fer-ho:
                // - Deshabilitem el botó d'activar el servidor
                // - Habilitem el botó de desactivar el servidor
                // - Canviem l'estat a "Servidor activat"
                this.view.getJpConfiguration().disableConnectServerButton();
                this.view.getJpConfiguration().enableDisconnectServerButton();
                this.view.getJpConfiguration().refreshServerStatus(true);

                break;

            case "disconnectServer":

                if (this.server != null && this.server.isAlive()) {
                    // TODO
                    server.stopServer();

                    // Cristian: NO SE PUEDE USAR STOP (server.stop())!!! HAY QUE USAR INTERRUPT
                }

                // Ara que el servidor ja està desactivat cal fer-ho evident en la UI (User Interface)
                // Per a fer-ho:
                // - Habilitem el botó d'activar el servidor
                // - Deshabilitem el botó de desactivar el servidor
                // - Canviem l'estat a "Servidor desactivat"
                this.view.getJpConfiguration().enableConnectServerButton();
                this.view.getJpConfiguration().disableDisconnectServerButton();
                this.view.getJpConfiguration().refreshServerStatus(false);

                break;

            case "register":
                try {

                    if (String.copyValueOf(this.view.getSignUpPanel().getTypedPassword()).equals(String.copyValueOf(this.view.getSignUpPanel().getTypedPassword2()))){
                        if (FormValidator.validateUsername(view.getSignUpPanel().getTypedUsername()) && FormValidator.validateEmail(view.getSignUpPanel().getTypedEmail()) &&FormValidator.validatePassword(String.copyValueOf(view.getSignUpPanel().getTypedPassword()))) {
                            Credentials credentials = new Credentials(view.getSignUpPanel().getTypedUsername(), view.getSignUpPanel().getTypedEmail(), view.getSignUpPanel().getTypedPassword());
                            // Ara farem persistir l'usuari en la base de dades
                            String aux = null;
                            String aux2 = null;
                            try {
                                aux = UserDAO.checkUsername(credentials);
                            } catch (SQLException e) {
                               // e.printStackTrace();
                                System.out.println("Ja existeix un usuari amb el mateix nom");
                            }

                            try{
                                aux2 = UserDAO.checkEmail(credentials);
                            }catch(SQLException ex){
                                System.out.println("Ja existeix un usuari amb el mateix email");
                            }

                            if (aux != null) {
                                JOptionPane.showMessageDialog(this.view, "Ja existeix un usuari amb el mateix nom", "Error de registro", JOptionPane.INFORMATION_MESSAGE);
                            }else if(aux2 != null){
                                JOptionPane.showMessageDialog(this.view, "Ja existeix un usuari amb el mateix email", "Error de registro", JOptionPane.INFORMATION_MESSAGE);
                            }else {
                                try {
                                    UserDAO.registerUser(credentials);
                                    JOptionPane.showMessageDialog(this.view, "S'ha guardat l'usuari " + credentials.getUsername() + " amb èxit", "Usuari desat a la base de dades", JOptionPane.INFORMATION_MESSAGE);
                                    view.refreshUsersList(UserDAO.getUsersList());
                                    view.refreshGraphic(UserDAO.getGraphicList());
                                    view.refreshRankingList(UserDAO.getScoreList());
                                }
                                catch(SQLException e){
                                    System.out.println("nom d'usuari ja existent a la base de dades");
                                }
                            }
                        }
                        else {
                            JOptionPane.showMessageDialog(this.view, "Credencials incorrectes", "Error de registro", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(this.view, "Les contrasenyes han de ser iguals", "Error de registro", JOptionPane.INFORMATION_MESSAGE);

                    }

                }
                catch (IncorrectUsernameFormatException e) {
                    e.printStackTrace();
                }
                catch (IncorrectEmailFormatException e) {
                    e.printStackTrace();
                }
                catch (IncorrectPasswordFormatException e) {
                    e.printStackTrace();
                }
                break;

            case "delete":

                try {
                    if(UserDAO.deleteUsers(view.getJpManagement().loadDeleteQueue())){
                        JOptionPane.showMessageDialog(this.view, "Èxit en l'execució", "Usuari esborrat de la base de dades", JOptionPane.INFORMATION_MESSAGE);
                        view.refreshUsersList(UserDAO.getUsersList());
                        view.refreshRankingList(UserDAO.getScoreList());
                    }else{
                        JOptionPane.showMessageDialog(this.view, "Error en l'execució.\nNo s'ha esborrat cap usuari", " DELETE ERROR", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            case "2X":

                try {
                    view.getGraphSelectionPanel().setGraphPanelData(UserDAO.getScore((String) view.getGraphSelectionPanel().getComboBox().getSelectedItem(), "2X"));
                    if(view.getGraphSelectionPanel().getGraphPanel().getData() == null){
                        JOptionPane.showMessageDialog(this.view, "No hi ha dades", "GRAPHIC ERROR", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "4X":
                try {
                    view.getGraphSelectionPanel().setGraphPanelData(UserDAO.getScore((String) view.getGraphSelectionPanel().getComboBox().getSelectedItem(), "4X"));
                    if(view.getGraphSelectionPanel().getGraphPanel().getData() == null){
                        JOptionPane.showMessageDialog(this.view, "No hi ha dades", "GRAPHIC ERROR", JOptionPane.INFORMATION_MESSAGE);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            case "Tournament":

                try {
                    view.getGraphSelectionPanel().setGraphPanelData(UserDAO.getScore((String) view.getGraphSelectionPanel().getComboBox().getSelectedItem(), "Tournament"));
                    if(view.getGraphSelectionPanel().getGraphPanel().getData() == null){
                        JOptionPane.showMessageDialog(this.view, "No hi ha dades", "GRAPHIC ERROR", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}