package view.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * classe encarregada de crear la llista amb la puntuació de cada jugador
 * @author Team Troner (grupC3)
 * @version v_final
 */
public class RankingPanel extends JPanel {

    private String[] columnNames = {"Username", "Last Acces", "Score"};
    private Object[][] data;
    private JTable table;
    private DefaultTableModel model;

    /**
     * Constructor RankingPanel
     */
    public RankingPanel() {
        model = new DefaultTableModel(data, columnNames); //final
        table = new JTable(model) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.setFillsViewportHeight(true);


        //this.setLayout(new GridLayout(1,0));
        this.setLayout(new BorderLayout());
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
        //Add the scroll pane to this panel.
        this.add(scrollPane);
    } // tancament constructor

    public Object[][] getData() { return data; }

    public void setData(Object[][] data) {
        this.data = data;
    }

    public DefaultTableModel getModel() {
        return model;
    }

} // tancament classe
