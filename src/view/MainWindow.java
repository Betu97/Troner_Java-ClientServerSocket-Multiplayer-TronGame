package view;

import view.panels.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.*;

/**
 * classe principal de la vista, afegeis les altres vistes en un cardLayout
 * @author Team Troner (grupC3)
 * @version v_final
 */
public class MainWindow extends JFrame {
    public final static int WIDTH = 850;
    public final static int HEIGHT = 600;
    private static final String tabRegister = "REGISTRE";
    private static final String tabManag = "GESTIÓ";
    private static final String tabConfig = "CONFIGURACIÓ";
    private static final String tabRank = "RANQUING";
    private static final String tabGraphic = "GRAFIC";

    private ManagementPanel jpManag;
    private SignUpPanel signUpPanel;
    private ConfigurationPanel jpConfig;
    private RankingPanel jpRank;
    private GraphSelectionPanel graphSelectionPanel;

    /**
     * Constructor MainWindow
     */
    public MainWindow(ArrayList<String> configuration) {

        // En primer lloc definim el Look and Feel
        // Farem que sigui el CrossPlatformLookAndFeel—this is the "Java L&F" (also called "Metal") that looks the same on all platforms.
        // D'aquesta manera aconseguirem el mateix aspecte que l'enunciat.
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch (Exception e) {
            // No fem res
        }

        JTabbedPane jTabbedPane = new JTabbedPane();
        getContentPane().add(jTabbedPane, BorderLayout.CENTER);

        signUpPanel = new SignUpPanel();
        jpManag =  new ManagementPanel();
        jpConfig = new ConfigurationPanel(configuration);
        jpRank = new RankingPanel();
        graphSelectionPanel = new GraphSelectionPanel();

        jTabbedPane.addTab(tabRegister, signUpPanel);
        jTabbedPane.addTab(tabManag, jpManag);
        jTabbedPane.addTab(tabConfig, jpConfig);
        jTabbedPane.addTab(tabRank, jpRank);
        jTabbedPane.addTab(tabGraphic, graphSelectionPanel);

        // Definim les dimensions mínimes de la vista, que faran impossible que la vista es pugui veure incorrectament
        this.setMinimumSize(new Dimension(560, 400));
        // Definim les dimensions inicials de la vista
        this.setSize(WIDTH, HEIGHT);
        // Definim el títol de la finestra com "Troner Server"
        this.setTitle("Troner Server");
        // Fem que la finestra es posicioni al mig de la pantalla
        this.setLocationRelativeTo(null);
        // Definim la operació que cal fer quan l'usuari faci click en el botó de sortir de la finestra: tancar el programa
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Mètode per donar un actionListener per a cada botó
     * @param actionListener
     */
    public void registerController(ActionListener actionListener) {
        // Registrem el controlador als 5 botons de la GUI
        signUpPanel.registerController(actionListener);
        jpManag.registerController(actionListener);
        jpConfig.registerController(actionListener);
        graphSelectionPanel.registerController(actionListener);
    }

    /**
     * Mètode per actualitzar la llista d'usuaris
     * @param users
     */
    public void refreshUsersList(LinkedList<String[]> users) {
        jpManag.setData(new Object[users.size()][5]);

        jpManag.getModel().setNumRows(0);
        for(int i = 0; i < users.size(); i++) {
            jpManag.getData()[i][0] = users.get(i)[1];
            jpManag.getData()[i][1] = users.get(i)[5];
            jpManag.getData()[i][2] = users.get(i)[4];
            jpManag.getData()[i][3] = users.get(i)[6];
            jpManag.getData()[i][4] = false;
            jpManag.getModel().addRow(jpManag.getData()[i]);
        }
        jpManag.getModel().fireTableDataChanged();
    }

    /**
     * Mètode per actualitzar el gràfic amb les dades noves
     * @param users
     */
    public void refreshGraphic (String[] users){
        graphSelectionPanel.getComboBox().removeAllItems();
        try {
            for (int i = 0; i < users.length; i++) {
                graphSelectionPanel.getComboBox().addItem(users[i]);
            }
        }catch(Exception e){

        }
    }

    /**
     * Mètode per actualitzar el rànking amb info nova
     * @param ranking
     */
    public void refreshRankingList(LinkedList<String[]> ranking) {
        jpRank.setData(new Object[ranking.size()][3]);

        jpRank.getModel().setNumRows(0);
        for(int i = 0; i < ranking.size(); i++) {
            jpRank.getData()[i][0] = ranking.get(i)[0];
            jpRank.getData()[i][1] = ranking.get(i)[1];
            jpRank.getData()[i][2] = ranking.get(i)[2];
            jpRank.getModel().addRow(jpRank.getData()[i]);
        }
        jpRank.getModel().fireTableDataChanged();
    }

    public SignUpPanel getSignUpPanel(){
        return this.signUpPanel;
    }

    public ManagementPanel getJpManagement(){
        return this.jpManag;
    }

    public ConfigurationPanel getJpConfiguration(){ return this.jpConfig; }

    public GraphSelectionPanel getGraphSelectionPanel(){return this.graphSelectionPanel; }
}