package utils;

import app.Employee;
import app.Wage;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Václav Kurel
 */
public interface PayrollInterface {
    
    
    /**
     * Method to load employees from file
     *
     * @param employeeFile
     * @throws FileNotFoundException
     * @throws IOException
     */
    
    public void loadEmployees(String employeeFile) throws /**FileNotFoundException,**/ IOException;

    /**
     * Method to obtaining info about employees
     *
     * @return String
     */
    public String getEmployeesInfo();

    /**
     * Method for finding employee by ID
     *
     * @param id
     * @return
     */
    public Employee findEmployee(int id);

    /**
     * Method to adding employee to list
     *
     * @param e - Employee
     */
    public void addEmployeeToList(Employee e);

    /**
     * Method to save the added employee
     *
     * @param employeeFile
     * @throws IOException
     */
    public void saveAddedEmployees(String employeeFile) throws IOException;

    /**
     * Method for obtaining information about employees sorted by ID
     *
     * @return String
     */
    public String getEmployeesInfoSortedByID();

    /**
     * Method for obtaining information about employees sorted by last name
     *
     * @return String
     */
    public String getEmployeesInfoSortedByLastName();

    /**
     * Method for obtaining information about employees sorted by first name
     *
     * @return String
     */
    public String getEmployeesInfoSortedByFirstName();

    /**
     * Method for loading working hours by file type
     *
     * @param wagesFile
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void loadHours(String wagesFile) throws FileNotFoundException, IOException;

    /**
     * Method for saving added working hours
     *
     * @param wagesFile
     * @throws IOException
     */
    public void saveAddedHours(String wagesFile) throws IOException;

    /**
     * Method for obtainig info about employee's working hours
     *
     * @return String
     */
    public String getHoursInfo();

    /**
     * Method for calculating wages
     */
    public void calculateWages();

    /**
     * Method for obtaining info about calculated wages
     *
     * @return String
     */
    public String getWagesInfo();

    /**
     * Method to save calculated wages
     *
     * @param resultFile
     * @throws IOException
     */
    public void saveWages(String resultFile) throws IOException;

    /**
     * Method to add wage to list
     *
     * @param wage
     */
    public void addWageToList(Wage wage);

    /**
     * Method for saving results to Excel file
     *
     * @param resultFile
     * @throws IOException
     */
    public void saveResultToExcel(String resultFile) throws IOException;

}
