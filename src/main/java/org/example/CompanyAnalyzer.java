package org.example;

import java.io.*;
import java.util.*;

public class CompanyAnalyzer {
    private Map<Integer, Employee> employees = new HashMap<>();
    private Map<Integer, List<Employee>> subordinates = new HashMap<>();

    public void readEmployeesFromCSV(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            int id = Integer.parseInt(values[0]);
            String firstName = values[1];
            String lastName = values[2];
            double salary = Double.parseDouble(values[3]);
            Integer managerId = null;
            if(values.length >=5) {
                managerId = values[4].isEmpty() ? null : Integer.parseInt(values[4]);
            }

            Employee employee = new Employee(id, firstName, lastName, salary, managerId);
            employees.put(id, employee);

            if (managerId != null) {
                subordinates.computeIfAbsent(managerId, k -> new ArrayList<>()).add(employee);
            }
        }
        reader.close();
    }

    public void analyzeManagers() {
        for (Map.Entry<Integer, List<Employee>> entry : subordinates.entrySet()) {
            Integer managerId = entry.getKey();
            List<Employee> subordinatesList = entry.getValue();

            if (!employees.containsKey(managerId)) continue;
            Employee manager = employees.get(managerId);

            double totalSubordinateSalary = 0;
            for (Employee subordinate : subordinatesList) {
                totalSubordinateSalary += subordinate.getSalary();
            }
            double averageSubordinateSalary = totalSubordinateSalary / subordinatesList.size();

            double minRequiredSalary = averageSubordinateSalary * 1.20;
            double maxAllowedSalary = averageSubordinateSalary * 1.50;

            if (manager.getSalary() < minRequiredSalary) {
                System.out.printf("Manager %s %s earns %.2f less than they should, by %.2f\n",
                        manager.getFirstName(), manager.getLastName(), manager.getSalary(), minRequiredSalary - manager.getSalary());
            } else if (manager.getSalary() > maxAllowedSalary) {
                System.out.printf("Manager %s %s earns %.2f more than they should, by %.2f\n",
                        manager.getFirstName(), manager.getLastName(), manager.getSalary(), manager.getSalary() - maxAllowedSalary);
            }
        }
    }

    public void analyzeReportingLines() {
        for (Employee employee : employees.values()) {
            if (!Objects.isNull(employee)) {
                if (employee.getManagerId() != null) {
                    int reportingLineLength = getReportingLineLength(employee);
                    if (reportingLineLength > 4) {
                        System.out.printf("Employee %s %s %s has a reporting line which is too long: %d managers\n",
                                employee.getId(),employee.getFirstName(), employee.getLastName(), reportingLineLength);
                    }
                }
            }
        }
    }

    private int getReportingLineLength(Employee employee) {
        int length = 0;
        while (employee.getManagerId() != null) {
            employee = employees.get(employee.getManagerId());
            length++;
            if(employee ==null){
                break;
            }
        }
        return length;
    }

    public static void main(String[] args) throws IOException {
        CompanyAnalyzer analyzer = new CompanyAnalyzer();
        analyzer.readEmployeesFromCSV("C:\\ProgramData\\core\\Deepti\\Swissre\\Test.csv");
        analyzer.analyzeManagers();
        analyzer.analyzeReportingLines();
    }
}