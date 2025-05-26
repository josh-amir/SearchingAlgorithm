package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateEmployeeDialog extends JDialog {
    public UpdateEmployeeDialog(EmployeeManager manager, Employee employee, EmployeeTable table) {
        setTitle("Update Employee");
        setModal(true);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField idField = new JTextField(employee.getId(), 20);
        idField.setEditable(false);
        JTextField nameField = new JTextField(employee.getName(), 20);
        JTextField positionField = new JTextField(employee.getPosition(), 20);
        JTextField salaryField = new JTextField(String.valueOf(employee.getSalary()), 20);

        String[] labels = {"ID:", "Name:", "Position:", "Salary:"};
        JTextField[] fields = {idField, nameField, positionField, salaryField};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            formPanel.add(new JLabel(labels[i]), gbc);
            gbc.gridx = 1;
            formPanel.add(fields[i], gbc);
        }

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton updateButton = new JButton("Update");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        updateButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String position = positionField.getText().trim();
            String salaryText = salaryField.getText().trim();
            if (!name.isEmpty() && !position.isEmpty() && !salaryText.isEmpty()) {
                try {
                    double salary = Double.parseDouble(salaryText);
                    Employee updated = new Employee(employee.getId(), name, position, salary);
                    manager.updateEmployee(updated);
                    table.refreshTable();
                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Salary must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dispose());

        setSize(400, 300);
        setLocationRelativeTo(null);
    }
}