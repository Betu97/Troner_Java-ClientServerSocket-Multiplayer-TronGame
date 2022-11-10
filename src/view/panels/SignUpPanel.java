package view.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * classe encarregada de crear la vista del registre
 * @author Team Troner (grupC3)
 * @version v_final
 */
public class SignUpPanel extends JPanel {

    private GridBagConstraints c;
    private JTextField jtfUsername, jtfEmail;
    private JPasswordField jpfPassword, jpfPasswordRepeat;

    private JButton jbRegister;

    /**
     * Constructor JPRegistre
     */
    public SignUpPanel(){

        c = new GridBagConstraints();

        setLayout(new GridBagLayout());

        JLabel jlUsername = new JLabel ("Nom d'usuari");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        c.insets =  new Insets (0, 100, 0, 0);
        add(jlUsername, c);

        jtfUsername = new JTextField("");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 1;c.weightx = 1;
        c.insets =  new Insets (10,0,0,100);
        jtfUsername.setPreferredSize(new Dimension(200,30));
        add(jtfUsername, c);

        JLabel jlEmail = new JLabel ("Correu electrònic");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 2;
        c.insets =  new Insets (0, 100, 0, 0);
        add(jlEmail, c);

        jtfEmail = new JTextField("");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 2;
        c.insets =  new Insets (10,0,0,100);
        jtfEmail.setPreferredSize(new Dimension(200,30));
        add(jtfEmail, c);

        JLabel jlPassword = new JLabel ("Contrasenya");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 3;
        c.insets =  new Insets (0, 100, 0, 0);
        add(jlPassword, c);

        jpfPassword = new JPasswordField("");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 3;
        c.insets =  new Insets (10,0,0,100);
        jpfPassword.setPreferredSize(new Dimension(200,30));
        add(jpfPassword, c);

        JLabel jlPasswordRepeat = new JLabel ("Repeteix la contrasenya");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 4;
        c.insets =  new Insets (0, 100, 0, 0);
        add(jlPasswordRepeat, c);

        jpfPasswordRepeat = new JPasswordField("");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 4;
        c.insets =  new Insets (10,0,0, 100);
        jpfPasswordRepeat.setPreferredSize(new Dimension(200,30));
        add(jpfPasswordRepeat, c);

        jbRegister = new JButton("Crea el compte");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 5;
        c.gridwidth=2;
        c.insets =  new Insets (20, 100, 0, 100);
        add(jbRegister, c);

    } // tancament constructor

    public void registerController(ActionListener actionListener) {
        // Registrem el controlador als 5 botons de la GUI
        jbRegister.addActionListener(actionListener);
        jbRegister.setActionCommand("register");

        jpfPassword.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }
            @Override
            public void keyPressed(KeyEvent e) {
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

    }

    public String getTypedUsername() {
        return this.jtfUsername.getText();
    }

    public String getTypedEmail() {
        return this.jtfEmail.getText();
    }

    public char[] getTypedPassword() {
        return this.jpfPassword.getPassword();
    }

    public char[] getTypedPassword2() {
        return this.jpfPasswordRepeat.getPassword();
    }
} // tancament classe
