package view.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * classe encarregada de crear la llista amb l'informació de cada usuari que hiha a la BBDD
 * @author Team Troner (grupC3)
 * @version v_final
 */
public class ManagementPanel extends JPanel {

    private String[] columnNames;
    private Object[][] data;
    private JTable table;
    private JButton jbDelete;
    private JPanel jpButton;
    private DefaultTableModel model;

    /**
     * Constructor GraphSelectionPanel
     */
    public ManagementPanel() {
        this.columnNames = new String[]{"Username", "Score", "Last Acces", "Data Register", "Selecciona"};
        this.jbDelete = new JButton("Delete");
        this.jpButton = new JPanel();

        model = new DefaultTableModel(data, columnNames);
        table = new JTable(model){
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;
                    case 3:
                        return String.class;
                    default:
                        return Boolean.class;
                }
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }

            public void setValueAt(Object value, int row, int col) {
                if (value instanceof Boolean && col == 4) {
                    data[row][col] = !(boolean) data[row][col];
                    model.setNumRows(0);
                    for(int i = 0; i < data.length; i++) {
                        model.addRow(data[i]);
                    }
                    model.fireTableDataChanged();
                }
            }

        };
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.setFillsViewportHeight(true);


        jpButton.setLayout(new FlowLayout(FlowLayout.CENTER));
        jpButton.add(jbDelete);

        this.setLayout(new BorderLayout());
        // Creem el panell de desplaçament i l'afegim la taula
        JScrollPane scrollPane = new JScrollPane(table);
        // Afegim el panell de desplaçament a aquest panell
        this.add(scrollPane);
        this.add(jpButton, BorderLayout.SOUTH);

    }

    /**
     * Mètode per donar un actionListener per a cada botó
     * @param actionListener
     */
    public void registerController(ActionListener actionListener){
        jbDelete.addActionListener(actionListener);
        jbDelete.setActionCommand("delete");
    }

    public void setData(Object[][] data) {
        this.data = data;
    }

    public Object[][] getData() {
        return data;
    }

    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getModel() {
        return model;
    }

    /**
     * Mètode per borrar un usuari de la taula
     * @return ArrayList amb l'nfo actualitzada
     */
    public ArrayList<String> loadDeleteQueue() {
        ArrayList deleteQueue = new ArrayList();
        for (int i = 0; i < this.data.length; i++) {
            System.out.println((boolean)this.data[i][4]);
            if ((boolean)this.data[i][4]) {
                deleteQueue.add(this.data[i][0]);
            }
        }
        return deleteQueue;
    }
}