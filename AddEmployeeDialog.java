package gui;

import javax.swing.*;
import java.awt.*;

public class AddEmployeeDialog extends JDialog {
    public AddEmployeeDialog(EmployeeManager manager, EmployeeTable table) {
        setTitle("Add Employee");
        setModal(true);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField idField = new JTextField(20);
        JTextField nameField = new JTextField(20);
        JTextField positionField = new JTextField(20);
        JTextField salaryField = new JTextField(20);

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
        JButton addButton = new JButton("Add");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String position = positionField.getText().trim();
            String salaryText = salaryField.getText().trim();
            if (!id.isEmpty() && !name.isEmpty() && !position.isEmpty() && !salaryText.isEmpty()) {
                try {
                    double salary = Double.parseDouble(salaryText);
                    manager.addEmployee(new Employee(id, name, position, salary));
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