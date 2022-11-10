package view.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * classe encarregada del panel de configuració
 * @author Team Troner (grupC3)
 * @version v_final
 */
public class ConfigurationPanel extends JPanel {
    private JLabel jlServerStatus;
    private JLabel jlIP, jlDatabaseName, jlUser, jlPassword, jlDatabasePort;
    private JTextField jtfServerPort;
    private JButton jbSave, jbConnect, jbDisconnect;

    /**
     * Constructor ConfigurationPanel
     * @param configuration
     */
    public ConfigurationPanel(ArrayList<String> configuration) {

        // En primer lloc definim el Layout del JPanel
        // Triem l'ús d'un GridBagLayout
        this.setLayout(new GridBagLayout());

        // A continuació definim un objecte GridBagConstraints que ens servirà a continuació
        // El reaprofitarem per a cada element que afegim al GridBagLayout
        // Ens permetrà especificar les següents característiques:
        // - La direcció en què es vol estirar l'element per tal d'ocupar l'espai de la cel·la
        // - La columna del JPanel amb GridBagLayout on es vol situar l'element
        // - La fila del JPanel amb GridBagLayout on es vol situar l'element
        // - Per a indicar que es desitja que l'element s'estiri (disseny responsive)
        // - La quantitat de columnes que ha d'ocupar la cel·la d'aquest element
        // - Els marges (top, left, bottom, right) de la cel·la d'aquest element
        GridBagConstraints c = new GridBagConstraints();

        // ------------------------------
        // INICI DE LA SECCIÓ DE L'ESTAT DEL SERVIDOR
        // ------------------------------
        JPanel jpServerStatus = new JPanel();
        jpServerStatus.setBorder(BorderFactory.createTitledBorder("Modificar l'estat del servidor"));
        jpServerStatus.setLayout(new GridBagLayout());
        c.fill = GridBagConstraints.HORIZONTAL;// La direcció en què es vol estirar l'element per tal d'ocupar l'espai de la cel·la
        c.gridx = 1;// La columna del JPanel amb GridBagLayout on es vol situar l'element
        c.gridy = 1;// La fila del JPanel amb GridBagLayout on es vol situar l'element
        c.weightx = 1;// Per a indicar que es desitja que l'element s'estiri (disseny responsive)
        c.gridwidth = 2;// La quantitat de columnes que ha d'ocupar la cel·la d'aquest element
        c.insets =  new Insets(0, 50, 0, 50);// Els marges (top, left, bottom, right) de la cel·la d'aquest element
        add(jpServerStatus, c);

        // ------------------------------
        // L'estat del servidor (activat / desactivat)
        // ------------------------------
        jlServerStatus = new JLabel("Servidor desactivat", SwingConstants.CENTER);
        c.fill = GridBagConstraints.HORIZONTAL;// La direcció en què es vol estirar l'element per tal d'ocupar l'espai de la cel·la
        c.gridx = 1;// La columna del JPanel amb GridBagLayout on es vol situar l'element
        c.gridy = 1;// La fila del JPanel amb GridBagLayout on es vol situar l'element
        c.weightx = 1;// Per a indicar que es desitja que l'element s'estiri (disseny responsive)
        c.gridwidth = 2;// La quantitat de columnes que ha d'ocupar la cel·la d'aquest element
        c.insets =  new Insets(15, 10, 0, 10);// Els marges (top, left, bottom, right) de la cel·la d'aquest element
        jpServerStatus.add(jlServerStatus, c);

        // ------------------------------
        // Botó per a activar el servidor
        // ------------------------------
        jbConnect = new JButton("Activar el servidor");
        jbConnect.setPreferredSize(new Dimension(100, 30));
        c.fill = GridBagConstraints.HORIZONTAL;// La direcció en què es vol estirar l'element per tal d'ocupar l'espai de la cel·la
        c.gridx = 1;// La columna del JPanel amb GridBagLayout on es vol situar l'element
        c.gridy = 2;// La fila del JPanel amb GridBagLayout on es vol situar l'element
        c.weightx = 1;// Per a indicar que es desitja que l'element s'estiri (disseny responsive)
        c.gridwidth = 1;// La quantitat de columnes que ha d'ocupar la cel·la d'aquest element
        c.insets =  new Insets(15, 10, 10, 5);// Els marges (top, left, bottom, right) de la cel·la d'aquest element
        jpServerStatus.add(jbConnect, c);

        // ------------------------------
        // Botó per a desactivar el servidor
        // ------------------------------
        jbDisconnect = new JButton("Desactivar el servidor");
        jbDisconnect.setEnabled(false);// Per defecte aquest botó es troba desactivat ja que per defecte el servidor ja es troba desactivat
        jbDisconnect.setPreferredSize(new Dimension(100,30));
        c.fill = GridBagConstraints.HORIZONTAL;// La direcció en què es vol estirar l'element per tal d'ocupar l'espai de la cel·la
        c.gridx = 2;// La columna del JPanel amb GridBagLayout on es vol situar l'element
        c.gridy = 2;// La fila del JPanel amb GridBagLayout on es vol situar l'element
        c.weightx = 1;// Per a indicar que es desitja que l'element s'estiri (disseny responsive)
        c.gridwidth = 1;// La quantitat de columnes que ha d'ocupar la cel·la d'aquest element
        c.insets =  new Insets(15, 5, 10, 10);// Els marges (top, left, bottom, right) de la cel·la d'aquest element
        jpServerStatus.add(jbDisconnect, c);

        // ------------------------------
        // INICI DE LA SECCIÓ DE LA CONFIGURACIÓ DEL SERVIDOR
        // ------------------------------
        JPanel jpServerConfiguration = new JPanel();
        jpServerConfiguration.setBorder(BorderFactory.createTitledBorder("Modificar la configuració del servidor"));
        jpServerConfiguration.setLayout(new GridBagLayout());
        c.fill = GridBagConstraints.HORIZONTAL;// La direcció en què es vol estirar l'element per tal d'ocupar l'espai de la cel·la
        c.gridx = 1;// La columna del JPanel amb GridBagLayout on es vol situar l'element
        c.gridy = 2;// La fila del JPanel amb GridBagLayout on es vol situar l'element
        c.weightx = 1;// Per a indicar que es desitja que l'element s'estiri (disseny responsive)
        c.gridwidth = 2;// La quantitat de columnes que ha d'ocupar la cel·la d'aquest element
        c.insets =  new Insets(20, 50, 0, 50);// Els marges (top, left, bottom, right) de la cel·la d'aquest element
        add(jpServerConfiguration, c);

        // ------------------------------
        // Port per a escoltar als clients
        // ------------------------------
        JLabel jlClientPort = new JLabel ("Port per a escoltar als clients: ");
        c.fill = GridBagConstraints.HORIZONTAL;// La direcció en què es vol estirar l'element per tal d'ocupar l'espai de la cel·la
        c.gridx = 1;// La columna del JPanel amb GridBagLayout on es vol situar l'element
        c.gridy = 1;// La fila del JPanel amb GridBagLayout on es vol situar l'element
        c.weightx = 1;// Per a indicar que es desitja que l'element s'estiri (disseny responsive)
        c.gridwidth = 1;// La quantitat de columnes que ha d'ocupar la cel·la d'aquest element
        c.insets =  new Insets (10, 10, 0, 0);// Els marges (top, left, bottom, right) de la cel·la d'aquest element
        jpServerConfiguration.add(jlClientPort, c);

        jtfServerPort = new JTextField(configuration.get(1));
        c.fill = GridBagConstraints.HORIZONTAL;// La direcció en què es vol estirar l'element per tal d'ocupar l'espai de la cel·la
        c.gridx = 2;// La columna del JPanel amb GridBagLayout on es vol situar l'element
        c.gridy = 1;// La fila del JPanel amb GridBagLayout on es vol situar l'element
        c.weightx = 1;// Per a indicar que es desitja que l'element s'estiri (disseny responsive)
        c.gridwidth = 1;// La quantitat de columnes que ha d'ocupar la cel·la d'aquest element
        c.insets =  new Insets (10, 0, 0, 10);// Els marges (top, left, bottom, right) de la cel·la d'aquest element
        jtfServerPort.setPreferredSize(new Dimension(200, 30));
        jpServerConfiguration.add(jtfServerPort, c);

        // ------------------------------
        // Direcció IP del servidor de la BD
        // ------------------------------
        JLabel jlIP = new JLabel ("Direcció IP del servidor de la BD: ");
        c.fill = GridBagConstraints.HORIZONTAL;// La direcció en què es vol estirar l'element per tal d'ocupar l'espai de la cel·la
        c.gridx = 1;// La columna del JPanel amb GridBagLayout on es vol situar l'element
        c.gridy = 2;// La fila del JPanel amb GridBagLayout on es vol situar l'element
        c.weightx = 1;// Per a indicar que es desitja que l'element s'estiri (disseny responsive)
        c.gridwidth = 1;// La quantitat de columnes que ha d'ocupar la cel·la d'aquest element
        c.insets =  new Insets (10, 10, 0, 0);// Els marges (top, left, bottom, right) de la cel·la d'aquest element
        jpServerConfiguration.add(jlIP, c);

        this.jlIP = new JLabel(configuration.get(0));
        c.fill = GridBagConstraints.HORIZONTAL;// La direcció en què es vol estirar l'element per tal d'ocupar l'espai de la cel·la
        c.gridx = 2;// La columna del JPanel amb GridBagLayout on es vol situar l'element
        c.gridy = 2;// La fila del JPanel amb GridBagLayout on es vol situar l'element
        c.weightx = 1;// Per a indicar que es desitja que l'element s'estiri (disseny responsive)
        c.gridwidth = 1;// La quantitat de columnes que ha d'ocupar la cel·la d'aquest element
        c.insets =  new Insets (10, 0, 0, 10);// Els marges (top, left, bottom, right) de la cel·la d'aquest element
        this.jlIP.setPreferredSize(new Dimension(200, 30));
        jpServerConfiguration.add(this.jlIP, c);

        // ------------------------------
        // Port de connexió amb la BD
        // ------------------------------
        JLabel jlDBPort = new JLabel ("Port de connexió amb la BD: ");
        c.fill = GridBagConstraints.HORIZONTAL;// La direcció en què es vol estirar l'element per tal d'ocupar l'espai de la cel·la
        c.gridx = 1;// La columna del JPanel amb GridBagLayout on es vol situar l'element
        c.gridy = 3;// La fila del JPanel amb GridBagLayout on es vol situar l'element
        c.weightx = 1;// Per a indicar que es desitja que l'element s'estiri (disseny responsive)
        c.gridwidth = 1;// La quantitat de columnes que ha d'ocupar la cel·la d'aquest element
        c.insets =  new Insets (10, 10, 0, 0);// Els marges (top, left, bottom, right) de la cel·la d'aquest element
        jpServerConfiguration.add(jlDBPort, c);

        jlDatabasePort = new JLabel(configuration.get(3));
        c.fill = GridBagConstraints.HORIZONTAL;// La direcció en què es vol estirar l'element per tal d'ocupar l'espai de la cel·la
        c.gridx = 2;// La columna del JPanel amb GridBagLayout on es vol situar l'element
        c.gridy = 3;// La fila del JPanel amb GridBagLayout on es vol situar l'element
        c.weightx = 1;// Per a indicar que es desitja que l'element s'estiri (disseny responsive)
        c.gridwidth = 1;// La quantitat de columnes que ha d'ocupar la cel·la d'aquest element
        c.insets =  new Insets (10, 0, 0, 10);// Els marges (top, left, bottom, right) de la cel·la d'aquest element
        jlDatabasePort.setPreferredSize(new Dimension(200,30));
        jpServerConfiguration.add(jlDatabasePort, c);

        // ------------------------------
        // Nom de la BD
        // ------------------------------
        JLabel jlDB = new JLabel ("Nom de la BD: ");
        c.fill = GridBagConstraints.HORIZONTAL;// La direcció en què es vol estirar l'element per tal d'ocupar l'espai de la cel·la
        c.gridx = 1;// La columna del JPanel amb GridBagLayout on es vol situar l'element
        c.gridy = 4;// La fila del JPanel amb GridBagLayout on es vol situar l'element
        c.weightx = 1;// Per a indicar que es desitja que l'element s'estiri (disseny responsive)
        c.gridwidth = 1;// La quantitat de columnes que ha d'ocupar la cel·la d'aquest element
        c.insets =  new Insets (10, 10, 0, 0);// Els marges (top, left, bottom, right) de la cel·la d'aquest element
        jpServerConfiguration.add(jlDB, c);

        jlDatabaseName = new JLabel(configuration.get(2));
        c.fill = GridBagConstraints.HORIZONTAL;// La direcció en què es vol estirar l'element per tal d'ocupar l'espai de la cel·la
        c.gridx = 2;// La columna del JPanel amb GridBagLayout on es vol situar l'element
        c.gridy = 4;// La fila del JPanel amb GridBagLayout on es vol situar l'element
        c.weightx = 1;// Per a indicar que es desitja que l'element s'estiri (disseny responsive)
        c.gridwidth = 1;// La quantitat de columnes que ha d'ocupar la cel·la d'aquest element
        c.insets =  new Insets (10, 0, 0, 10);// Els marges (top, left, bottom, right) de la cel·la d'aquest element
        jlDatabaseName.setPreferredSize(new Dimension(200, 30));
        jpServerConfiguration.add(jlDatabaseName, c);

        // ------------------------------
        // Usuari d’accés a la BD
        // ------------------------------
        JLabel jlUser = new JLabel ("Usuari d’accés a la BD: ");
        c.fill = GridBagConstraints.HORIZONTAL;// La direcció en què es vol estirar l'element per tal d'ocupar l'espai de la cel·la
        c.gridx = 1;// La columna del JPanel amb GridBagLayout on es vol situar l'element
        c.gridy = 5;// La fila del JPanel amb GridBagLayout on es vol situar l'element
        c.weightx = 1;// Per a indicar que es desitja que l'element s'estiri (disseny responsive)
        c.gridwidth = 1;// La quantitat de columnes que ha d'ocupar la cel·la d'aquest element
        c.insets =  new Insets (10, 10, 0, 0);// Els marges (top, left, bottom, right) de la cel·la d'aquest element
        jpServerConfiguration.add(jlUser, c);

        this.jlUser = new JLabel(configuration.get(4));
        c.fill = GridBagConstraints.HORIZONTAL;// La direcció en què es vol estirar l'element per tal d'ocupar l'espai de la cel·la
        c.gridx = 2;// La columna del JPanel amb GridBagLayout on es vol situar l'element
        c.gridy = 5;// La fila del JPanel amb GridBagLayout on es vol situar l'element
        c.weightx = 1;// Per a indicar que es desitja que l'element s'estiri (disseny responsive)
        c.gridwidth = 1;// La quantitat de columnes que ha d'ocupar la cel·la d'aquest element
        c.insets =  new Insets (10, 0, 0, 10);// Els marges (top, left, bottom, right) de la cel·la d'aquest element
        this.jlUser.setPreferredSize(new Dimension(200, 30));
        jpServerConfiguration.add(this.jlUser, c);

        // ------------------------------
        // Contrasenya d’accés a la BD
        // ------------------------------
        JLabel jlPassword = new JLabel ("Contrasenya d’accés a la BD: ");
        c.fill = GridBagConstraints.HORIZONTAL;// La direcció en què es vol estirar l'element per tal d'ocupar l'espai de la cel·la
        c.gridx = 1;// La columna del JPanel amb GridBagLayout on es vol situar l'element
        c.gridy = 6;// La fila del JPanel amb GridBagLayout on es vol situar l'element
        c.weightx = 1;// Per a indicar que es desitja que l'element s'estiri (disseny responsive)
        c.gridwidth = 1;// La quantitat de columnes que ha d'ocupar la cel·la d'aquest element
        c.insets =  new Insets (10, 10, 0, 0);// Els marges (top, left, bottom, right) de la cel·la d'aquest element
        jpServerConfiguration.add(jlPassword, c);

        this.jlPassword = new JLabel(configuration.get(5));
        c.fill = GridBagConstraints.HORIZONTAL;// La direcció en què es vol estirar l'element per tal d'ocupar l'espai de la cel·la
        c.gridx = 2;// La columna del JPanel amb GridBagLayout on es vol situar l'element
        c.gridy = 6;// La fila del JPanel amb GridBagLayout on es vol situar l'element
        c.weightx = 1;// Per a indicar que es desitja que l'element s'estiri (disseny responsive)
        c.gridwidth = 1;// La quantitat de columnes que ha d'ocupar la cel·la d'aquest element
        c.insets =  new Insets (10, 0, 0, 10);// Els marges (top, left, bottom, right) de la cel·la d'aquest element
        this.jlPassword.setPreferredSize(new Dimension(200,30));
        jpServerConfiguration.add(this.jlPassword, c);

        // ------------------------------
        // Botó per a aplicar la configuració
        // ------------------------------
        jbSave = new JButton("Guardar la configuració");
        c.fill = GridBagConstraints.HORIZONTAL;// La direcció en què es vol estirar l'element per tal d'ocupar l'espai de la cel·la
        c.gridx = 1;// La columna del JPanel amb GridBagLayout on es vol situar l'element
        c.gridy = 7;// La fila del JPanel amb GridBagLayout on es vol situar l'element
        c.weightx = 1;// Per a indicar que es desitja que l'element s'estiri (disseny responsive)
        c.gridwidth = 2;// La quantitat de columnes que ha d'ocupar la cel·la d'aquest element
        c.insets =  new Insets(15, 10, 10, 10);// Els marges (top, left, bottom, right) de la cel·la d'aquest element
        jpServerConfiguration.add(jbSave, c);
    }

    /**
     * Mètode per donar un actionListener per a cada botó
     * @param actionListener
     */
    public void registerController(ActionListener actionListener){
        jbConnect.addActionListener(actionListener);
        jbConnect.setActionCommand("connectServer");

        jbDisconnect.addActionListener(actionListener);
        jbDisconnect.setActionCommand("disconnectServer");

        jbSave.addActionListener(actionListener);
        jbSave.setActionCommand("saveConfigServer");
    }

    public ArrayList<String> getEnteredDataConfiguration(){
        ArrayList<String> config = new ArrayList<>();
        ArrayList<String> aux = new ArrayList<>();
        config.add(jlIP.getText());
        config.add(jtfServerPort.getText());
        config.add(jlDatabaseName.getText());
        config.add(jlUser.getText());
        config.add(jlPassword.getText());
        config.add(jlDatabasePort.getText());


        //abans de retornar tot ens asegurem que el port introduit es valid
        if(jtfServerPort.getText().matches("^\\d+$")){
        }else{
            return aux;
        }

        return config;
    }

    public void enableConnectServerButton() {
        jbConnect.setEnabled(true);
    }

    public void disableConnectServerButton() {
        jbConnect.setEnabled(false);
    }

    public void enableDisconnectServerButton() {
        jbDisconnect.setEnabled(true);
    }

    public void disableDisconnectServerButton() {
        jbDisconnect.setEnabled(false);
    }

    public void refreshServerStatus(boolean status) {
        if (status) {
            jlServerStatus.setText("Servidor activat");
        }
        else {
            jlServerStatus.setText("Servidor desactivat");
        }
    }
}