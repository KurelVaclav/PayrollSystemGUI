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
     * Metoda pro načtení zaměstnanců ze souboru
     *
     * @param employeeFile
     * @throws FileNotFoundException
     * @throws IOException
     */
    
    public void loadEmployees(String employeeFile) throws /**FileNotFoundException,**/ IOException;

    /**
     * Metoda pro získání informace o zaměstnancích
     *
     * @return String
     */
    public String getEmployeesInfo();

    /**
     * Metoda pro nalezení zaměstnance dle ID
     *
     * @param id
     * @return
     */
    public Employee findEmployee(int id);

    /**
     * Metoda pro přidání zaměstnance do listu
     *
     * @param e - Employee
     */
    public void addEmployeeToList(Employee e);

    /**
     * Metoda pro uložení přidaného zaměstnance
     *
     * @param employeeFile
     * @throws IOException
     */
    public void saveAddedEmployees(String employeeFile) throws IOException;

    /**
     * Metoda pro výpis zaměstnanců seřazených dle ID
     *
     * @return String
     */
    public String getEmployeesInfoSortedByID();

    /**
     * Metoda pro výpis zaměstnanců seřazených dle příjmení
     *
     * @return String
     */
    public String getEmployeesInfoSortedByLastName();

    /**
     * Metoda pro výpis zaměstnanců seřazených dle jména
     *
     * @return String
     */
    public String getEmployeesInfoSortedByFirstName();

    /**
     * Metoda pro naštení odpracovaných hodin zaměstnance
     *
     * @param wagesFile
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void loadHours(String wagesFile) throws FileNotFoundException, IOException;

    /**
     * Metoda pro uložení přidané odpracované hodiny
     *
     * @param wagesFile
     * @throws IOException
     */
    public void saveAddedHours(String wagesFile) throws IOException;

    /**
     * Metoda pro získání informace o odpracovaných hodinách zaměstnance
     *
     * @return String
     */
    public String getHoursInfo();

    /**
     * Metoda pro výpočet mezd
     */
    public void calculateWages();

    /**
     * Metoda pro získání informace o mzdě zaměstnance
     *
     * @return String
     */
    public String getWagesInfo();

    /**
     * Metoda pro uložení mezd
     *
     * @param resultFile
     * @throws IOException
     */
    public void saveWages(String resultFile) throws IOException;

    /**
     * Metoda pro přidání mzdy zaměstnance do listu
     *
     * @param wage
     */
    public void addWageToList(Wage wage);

    /**
     * Metoda pro uložení mezd do Excelu
     *
     * @param resultFile
     * @throws IOException
     */
    public void saveResultToExcel(String resultFile) throws IOException;

}
