package view.panels;

import view.panels.GraphPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.LinkedList;

/**
 * classe encarregada del panel de puntuació segons el mode de joc i jugador
 * @author Team Troner (grupC3)
 * @version v_final
 */
public class GraphSelectionPanel extends JPanel {
    private String[] usernames = {"Empty"};
    private JComboBox usersList;
    private JButton jb2Players, jb4Players, jbTournament;
    private GraphPanel graphPanel;

    /**
     * Constructor GraphSelectionPanel
     */
    public GraphSelectionPanel() {
        this.setBorder(new EmptyBorder(30, 40, 50, 40) );
        setLayout(new BorderLayout());

        JPanel jpOpcions = new JPanel();
        jpOpcions.setLayout(new GridLayout(2, 3, 40, 20));
        setLayout(new BorderLayout());
        JLabel jlSeleccionar = new JLabel ("Selecciona un usuari:");
        jpOpcions.add(jlSeleccionar);

        usersList = new JComboBox(usernames);
        usersList.setSelectedIndex(0);
        usersList.setPreferredSize(new Dimension(100,30));
        jpOpcions.add(usersList);
        jpOpcions.add(new JPanel());

        jb2Players = new JButton ("Mode 2X");
        jpOpcions.add(jb2Players);
        jb4Players = new JButton ("Mode 4X");
        jpOpcions.add(jb4Players);
        jbTournament = new JButton ("Mode torneig");
        jpOpcions.add(jbTournament);

        LinkedList<String[]> values = new LinkedList<String[]>();
        String[] aux = new String[2];
        aux[0] =  "" + 0;
        aux[1] = "";
        values.add(aux);

        this.graphPanel = new GraphPanel(values);
        this.add(graphPanel, BorderLayout.SOUTH);

        this.add(jpOpcions, BorderLayout.NORTH);

    }

    public void loadUsernames(String[] users){ this.usernames = users; }

    public String[] getUsernames(){ return this.usernames; }

    public JComboBox getComboBox() {return this.usersList;}

    /**
     * Mètode per donar un actionListener per a cada botó
     * @param actionListener
     */
    public void registerController(ActionListener actionListener){
        this.jb2Players.addActionListener(actionListener);
        this.jb2Players.setActionCommand("2X");
        this.jb4Players.addActionListener(actionListener);
        this.jb4Players.setActionCommand("4X");
        this.jbTournament.addActionListener(actionListener);
        this.jbTournament.setActionCommand("Tournament");
    }

    public void setGraphPanelData(LinkedList<String[]> data) {
        this.graphPanel.invalidate();
        this.graphPanel = new GraphPanel(data);
        this.add(graphPanel, BorderLayout.SOUTH);
        this.graphPanel.revalidate();
        this.graphPanel.repaint();
    }

    public GraphPanel getGraphPanel(){return graphPanel;}

}