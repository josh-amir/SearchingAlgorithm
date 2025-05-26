package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class SearchDialog extends JDialog {
    private JTextField searchField;
    private JButton searchButton;
    private JTextArea resultArea;
    private JComboBox<String> searchTypeComboBox;
    private EmployeeManager employeeManager;

    public SearchDialog(Window parent, EmployeeManager employeeManager, EmployeeTable employeeTable) {
        super(parent, "Search Employee", ModalityType.APPLICATION_MODAL);
        this.employeeManager = employeeManager;
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Enter Employee ID or Name:"));
        searchField = new JTextField(15);
        inputPanel.add(searchField);

        searchTypeComboBox = new JComboBox<>(new String[]{"Linear Search", "Binary Search"});
        inputPanel.add(searchTypeComboBox);

        searchButton = new JButton("Search");
        inputPanel.add(searchButton);

        add(inputPanel, BorderLayout.NORTH);

        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        searchButton.addActionListener((ActionEvent e) -> performSearch());

        setSize(400, 300);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void performSearch() {
        String searchTerm = searchField.getText().trim();
        String searchType = (String) searchTypeComboBox.getSelectedItem();
        List<Employee> employees = employeeManager.getAllEmployees();
        List<Employee> results;

        if ("Linear Search".equals(searchType)) {
            results = SearchUtils.linearSearch(employees, searchTerm);
        } else {
            results = SearchUtils.binarySearch(employees, searchTerm);
        }

        displayResults(results);
    }

    private void displayResults(List<Employee> results) {
        resultArea.setText("");
        if (results == null || results.isEmpty()) {
            resultArea.append("No employees found.\n");
        } else {
            for (Employee emp : results) {
                resultArea.append(emp.toString() + "\n");
            }
        }
    }
}
