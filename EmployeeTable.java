package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmployeeTable extends JTable {
    private EmployeeManager manager;
    private DefaultTableModel model;

    public EmployeeTable(EmployeeManager manager) {
        super();
        this.manager = manager;
        model = new DefaultTableModel(new Object[]{"ID", "Name", "Position", "Salary"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        setModel(model);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setShowGrid(true);
        setGridColor(Color.LIGHT_GRAY);
        setRowHeight(28);
        getTableHeader().setReorderingAllowed(false);
        refreshTable();
    }

    public void refreshTable() {
        model.setRowCount(0);
        List<Employee> employees = manager.getAllEmployees();
        for (Employee e : employees) {
            model.addRow(new Object[]{e.getId(), e.getName(), e.getPosition(), e.getSalary()});
        }
    }

    public Employee getSelectedEmployee() {
        int row = getSelectedRow();
        if (row >= 0) {
            String id = (String) getValueAt(row, 0);
            for (Employee e : manager.getAllEmployees()) {
                if (e.getId().equals(id)) return e;
            }
        }
        return null;
    }
}