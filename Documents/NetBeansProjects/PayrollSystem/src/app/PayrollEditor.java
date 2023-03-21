package app;

import filehandling.BinaryWriter;
import filehandling.ExcelWiter;
import filehandling.TextWriter;
import filehandling.Writer;
import java.io.DataInputStream;
import java.io.EOFException;
import utils.PayrollInterface;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.DuplicateFormatFlagsException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.UnsupportedDataTypeException;

/**
 * Class PayrollEditor used to implement methods from the PayrollInterface.
 * Contains methods for loading, calculating and saving employee's and wage's
 * data.
 *
 * @author Václav Kurel
 */
public class PayrollEditor implements PayrollInterface {
    
    ArrayList<Employee> employees = new ArrayList<>();
    ArrayList<Wage> wages = new ArrayList<>();

    /**
     * Method for loading employees by file type
     *
     * @param employeeFile - the name of the employee file
     * @throws IOException
     */
    @Override
    public void loadEmployees(String employeeFile) throws IOException {
        if (employeeFile.endsWith(".txt")) {
            loadEmployeesTxt(employeeFile);
        } else if (employeeFile.endsWith(".dat")) {
            loadEmployeesBinary(employeeFile);
        } else {
            throw new UnsupportedDataTypeException("Unsupported file format");
        }
    }

    /**
     * Method for loading employees from .txt file
     *
     * @param employeeFile
     * @throws IOException
     */
    public void loadEmployeesTxt(String employeeFile) throws IOException {
        File eFile = new File(Writer.dataDirectory, employeeFile);
        if (eFile.exists()) {
            try (Scanner inData = new Scanner(eFile)) {
                while (inData.hasNext()) {
                    int id = inData.nextInt();
                    String firstName = inData.next();
                    String lastName = inData.next();
                    int day = inData.nextInt();
                    int month = inData.nextInt();
                    int year = inData.nextInt();
                    String nationality = inData.next();
                    double hourTax = inData.nextDouble();
                    String positionName = inData.next();
                    Tax tax = new Tax(hourTax, positionName);
                    Employee e = new Employee(id, firstName, lastName, day, month, year, nationality, tax);
                    employees.add(e);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(PayrollEditor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Method for loading emplyees from .dat file
     *
     * @param employeeFile
     * @throws IOException
     */
    public void loadEmployeesBinary(String employeeFile) throws IOException {
        BinaryWriter bw = new BinaryWriter();
        employeeFile = Character.toUpperCase(employeeFile.charAt(0)) + employeeFile.substring(1);
        bw.createEmployees(employeeFile);
        File eFile = new File(Writer.dataDirectory, employeeFile);
        try (DataInputStream dis = new DataInputStream(new FileInputStream(eFile))) {
            boolean isEnd = false;
            while (!isEnd) {
                try {
                    int id = dis.readInt();
                    String firstName = dis.readUTF();
                    String lastName = dis.readUTF();
                    int day = dis.readInt();
                    int month = dis.readInt();
                    int year = dis.readInt();
                    String nationality = dis.readUTF();
                    double houTax = dis.readDouble();
                    String positionName = dis.readUTF();
                    Tax tax = new Tax(houTax, positionName);
                    Employee e = new Employee(id, firstName, lastName, day, month, year, nationality, tax);
                    employees.add(e);
                } catch (EOFException ex) {
                    isEnd = true;
                }
            }
        }
    }

    /**
     * Method for obtaining information about employees
     *
     * @return String emplyees info
     */
    @Override
    public String getEmployeesInfo() {
        try {
            Collections.sort(employees);
        } catch (Exception e) {
            System.err.println("Sorting failed");
        }
        StringBuilder sb = new StringBuilder("");
        sb.append(String.format("%-5s%-20s%-20s%-15s%-15s%-15s%-20s%n", "ID", "FirstName", "LastName", "BirthDate", "Country", "CZK/hour", "Position"));
        for (Employee employee : employees) {
            sb.append(employee).append("\n");
        }
        return sb.toString();
    }

    /**
     * Method for obtaining information about employees sorted by ID
     *
     * @return String employees info
     */
    @Override
    public String getEmployeesInfoSortedByID() {
        Collections.sort(employees);
        StringBuilder sb = new StringBuilder("");
        sb.append(String.format("%-5s%-20s%-20s%-15s%-15s%-15s%-20s%n", "ID", "FirstName", "LastName", "BirthDate", "Country", "CZK/hour", "Position"));
        for (Employee employee : employees) {
            sb.append(employee).append("\n");
        }
        return sb.toString();
    }

    /**
     * Method for obtaining information about employees sorted by last name
     *
     * @return String employees info
     */
    @Override
    public String getEmployeesInfoSortedByLastName() {
        Collections.sort(employees, Employee.lastNameComparator);
        StringBuilder sb = new StringBuilder("");
        sb.append(String.format("%-5s%-20s%-20s%-15s%-15s%-15s%-20s%n", "ID", "FirstName", "LastName", "BirthDate", "Country", "CZK/hour", "Position"));
        for (Employee employee : employees) {
            sb.append(employee).append("\n");
        }
        return sb.toString();
    }

    /**
     * Method for obtaining information about employees sorted by first name
     *
     * @return String employees info
     */
    @Override
    public String getEmployeesInfoSortedByFirstName() {
        Collections.sort(employees, Employee.firstNameComparator);
        StringBuilder sb = new StringBuilder("");
        sb.append(String.format("%-5s%-20s%-20s%-15s%-15s%-15s%-20s%n", "ID", "FirstName", "LastName", "BirthDate", "Country", "CZK/hour", "Position"));
        for (Employee employee : employees) {
            sb.append(employee).append("\n");
        }
        return sb.toString();
    }

    /**
     * Method for finding employee by ID
     *
     * @param id -int
     * @return found Employee
     * @throws NoSuchElementException
     */
    @Override
    public Employee findEmployee(int id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        throw new NoSuchElementException("Employee with ID: " + id + " is not in list!");
    }

    /**
     * Method for adding employee to list
     *
     * @param e - Employee
     */
    @Override
    public void addEmployeeToList(Employee e) {
        ArrayList<Integer> ids = new ArrayList<>();
        for (Employee employee : employees) {
            ids.add(employee.getId());
        }
        boolean isduplicitID = ids.contains(e.getId());
        if (!isduplicitID) {
            employees.add(e);
        } else {
            throw new DuplicateFormatFlagsException("This ID: " + e.getId() + " already exists");
        }
        
    }

    /**
     * Method for saving added employee
     *
     * @param employeeFile - employee file name
     * @throws IOException
     */
    @Override
    public void saveAddedEmployees(String employeeFile) throws IOException {
        TextWriter w;
        w = new TextWriter();
        w.saveUpdateEmployees(employeeFile, employees);
    }

    /**
     * Method for loading working hours by file type
     *
     * @param wagesFile - file name with working hours
     * @throws IOException
     */
    @Override
    public void loadHours(String wagesFile) throws IOException {
        if (wagesFile.endsWith(".txt")) {
            loadHoursTxt(wagesFile);
        } else if (wagesFile.endsWith(".dat")) {
            loadHoursBinary(wagesFile);
        } else {
            throw new UnsupportedDataTypeException("Unsupported file format");
        }
    }

    /**
     * Method for loading working hours from .txt file
     *
     * @param wagesFile
     * @throws FileNotFoundException
     */
    public void loadHoursTxt(String wagesFile) throws FileNotFoundException {
        File wFile = new File(Writer.dataDirectory, wagesFile);
        try (Scanner inData = new Scanner(wFile)) {
            int id, hours;
            while (inData.hasNext()) {
                id = inData.nextInt();
                Employee e = findEmployee(id);
                hours = inData.nextInt();
                Wage w = new Wage(e, hours);
                wages.add(w);
            }
        }
    }

    /**
     * Method for loading working hours from .dat file
     *
     * @param wagesFile
     * @throws IOException
     */
    public void loadHoursBinary(String wagesFile) throws IOException {
        BinaryWriter bw = new BinaryWriter();
        wagesFile = Character.toUpperCase(wagesFile.charAt(0)) + wagesFile.substring(1);
        bw.createHours(wagesFile);
        File wFile = new File(Writer.dataDirectory, wagesFile);
        try (DataInputStream dis = new DataInputStream(new FileInputStream(wFile))) {
            boolean isEnd = false;
            while (!isEnd) {
                try {
                    int id = dis.readInt();
                    Employee e = findEmployee(id);
                    int hours = dis.readInt();
                    Wage w = new Wage(e, hours);
                    wages.add(w);
                } catch (EOFException ex) {
                    isEnd = true;
                }
            }
        }
        
    }

    /**
     * Method for obtainig info about employee's working hours
     *
     * @return String
     */
    @Override
    public String getHoursInfo() {
        Collections.sort(wages, Wage.idComparator);
        StringBuilder sb = new StringBuilder("");
        sb.append(String.format("%-5s%-20s%-20s%-15s%-15s%-15s%-20s%-10s%n", "ID", "FirstName", "LastName", "BirthDate", "Country", "CZK/hour", "Position", "Hours"));
        for (Wage wage : wages) {
            sb.append(wage.toHourString()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Method for saving added working hours
     *
     * @param wagesFile - file with working hours
     * @throws IOException
     */
    @Override
    public void saveAddedHours(String wagesFile) throws IOException {
        TextWriter w;
        w = new TextWriter();
        w.saveUpdateHours(wagesFile, wages);
    }

    /**
     * Method for calculating wages
     */
    @Override
    public void calculateWages() {
        for (Wage wage : wages) {
            int hours = wage.getHours();
            Employee e = wage.getEmployee();
            wage.setGrossWage(hours, e);
//            wage.setSuperGrossWage();
            wage.setDownPayment();
            wage.setSHInsurancePayment();
            wage.setNetWage();
        }
    }

    /**
     * Method for obtaining info about employees and their calculated wages
     *
     * @return String
     */
    @Override
    public String getWagesInfo() {
        Collections.sort(wages, Wage.idComparator);
        StringBuilder sb = new StringBuilder("");
        sb.append(String.format("%-5s%-20s%-20s%-15s%-15s%-15s%-20s%-10s%-20s%-20s%-20s%-20s%n", "ID", "FirstName", "LastName", "BirthDate", "Country", "CZK/hour", "Position", "Hours", "GrossWage", "AdvanceTax", "SocHealthI", "NetWage"));
        for (Wage wage : wages) {
            sb.append(wage).append("\n");
        }
        return sb.toString();
    }

    /**
     * Method to save employee results with calculated wages
     *
     * @param resultFile - result file name
     * @throws IOException
     */
    @Override
    public void saveWages(String resultFile) throws IOException, IllegalArgumentException {
        Collections.sort(employees);
        Writer w;
        if (resultFile.endsWith(".txt")) {
            w = new TextWriter();
        } else if (resultFile.endsWith(".dat")) {
            w = new BinaryWriter();
        } else if (resultFile.endsWith(".xlsx")) {
            w = new ExcelWiter();
        } else {
            throw new IllegalArgumentException("Such a file extention format is not supported");
        }
        w.saveResults(resultFile, wages);
        
    }

    /**
     * Method to add wage to list
     *
     * @param wage
     */
    @Override
    public void addWageToList(Wage wage) {
        if (wage != null) {
            wages.add(wage);
        }
    }

    /**
     * Method to save data to Excel
     *
     * @param resultFile - result file name (.xlsx) to save the results to
     * @throws IOException
     */
    @Override
    public void saveResultToExcel(String resultFile) throws IOException {
        Writer w = new ExcelWiter();
        w.saveResults(resultFile, wages);
    }
    
}
