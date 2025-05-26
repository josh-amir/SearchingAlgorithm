package gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchUtils {
    // Linear search by ID or name (case-insensitive)
    public static List<Employee> linearSearch(List<Employee> employees, String query) {
        List<Employee> results = new ArrayList<>();
        for (Employee e : employees) {
            if (e.getId().equalsIgnoreCase(query) || e.getName().equalsIgnoreCase(query)) {
                results.add(e);
            }
        }
        return results;
    }

    // Binary search by ID or name (assumes list is sorted by ID)
    public static List<Employee> binarySearch(List<Employee> employees, String query) {
        // Sort by ID for binary search
        List<Employee> sorted = new ArrayList<>(employees);
        Collections.sort(sorted, Comparator.comparing(Employee::getId, String.CASE_INSENSITIVE_ORDER));

        int left = 0, right = sorted.size() - 1;
        List<Employee> results = new ArrayList<>();
        while (left <= right) {
            int mid = (left + right) / 2;
            Employee midEmp = sorted.get(mid);
            int cmp = midEmp.getId().compareToIgnoreCase(query);
            if (cmp == 0) {
                results.add(midEmp);
                break;
            } else if (cmp < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        // Optionally, also search by name (linear, since binary search by name is not practical)
        for (Employee e : employees) {
            if (e.getName().equalsIgnoreCase(query)) {
                if (!results.contains(e)) results.add(e);
            }
        }
        return results;
    }
}
