package gui;

import javax.swing.*;
import java.awt.*;
import gui.AddEmployeeDialog;
import gui.UpdateEmployeeDialog;
import gui.SearchDialog;

public class Main {
    private JFrame frame;
    private EmployeeTable employeeTable;
    private EmployeeManager employeeManager;
    private JLabel statusBar;

    public Main() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        employeeManager = new EmployeeManager();
        employeeTable = new EmployeeTable(employeeManager);

        frame = new JFrame("Employee Search App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLayout(new BorderLayout());

        frame.add(new JScrollPane(employeeTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Employee");
        JButton updateButton = new JButton("Update Employee");
        JButton deleteButton = new JButton("Delete Employee");
        JButton searchButton = new JButton("Search Employee");

        addButton.setToolTipText("Add a new employee");
        updateButton.setToolTipText("Update selected employee");
        deleteButton.setToolTipText("Delete selected employee");
        searchButton.setToolTipText("Search for an employee");

        addButton.addActionListener(e ->
                new AddEmployeeDialog(employeeManager, employeeTable).setVisible(true)
        );

        updateButton.addActionListener(e -> {
            Employee selected = employeeTable.getSelectedEmployee();
            if (selected != null) {
                new UpdateEmployeeDialog(employeeManager, selected, employeeTable).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(frame, "Please select an employee to update.");
            }
        });

        deleteButton.addActionListener(e -> {
            Employee selected = employeeTable.getSelectedEmployee();
            if (selected != null) {
                employeeManager.deleteEmployee(selected);
                employeeTable.refreshTable();
                setStatus("Employee deleted.");
            } else {
                JOptionPane.showMessageDialog(frame, "Please select an employee to delete.");
            }
        });

        searchButton.addActionListener(e ->
                new SearchDialog(frame, employeeManager, employeeTable).setVisible(true)
        );

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        statusBar = new JLabel("Ready");
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        frame.add(statusBar, BorderLayout.NORTH);

        frame.setVisible(true);
    }

    private void setStatus(String message) {
        statusBar.setText(message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}