package gui;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeManager {
    private static final String FILE_PATH = "src/data/employees.txt";
    private List<Employee> employees = new ArrayList<>();

    public EmployeeManager() {
        loadEmployees();
    }

    private void loadEmployees() {
        employees.clear();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            // Create file if it doesn't exist
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    Employee employee = new Employee(data[0], data[1], data[2], Double.parseDouble(data[3]));
                    employees.add(employee);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveEmployees() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Employee employee : employees) {
                bw.write(employee.getId() + "," + employee.getName() + "," + employee.getPosition() + "," + employee.getSalary());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
        saveEmployees();
    }

    public void deleteEmployee(String id) {
        employees.removeIf(employee -> employee.getId().equals(id));
        saveEmployees();
    }

    public void deleteEmployee(Employee e) {
        employees.remove(e);
        saveEmployees();
    }

    public void updateEmployee(Employee updatedEmployee) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId().equals(updatedEmployee.getId())) {
                employees.set(i, updatedEmployee);
                break;
            }
        }
        saveEmployees();
    }

    public Employee getEmployeeById(String id) {
        for (Employee employee : employees) {
            if (employee.getId().equals(id)) {
                return employee;
            }
        }
        return null;
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }
}
