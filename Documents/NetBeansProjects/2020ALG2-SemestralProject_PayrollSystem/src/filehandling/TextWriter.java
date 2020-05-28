package filehandling;

import app.Employee;
import app.Wage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

/**
 * Třidá textový zapisovač, potomek zapisovače
 * @author Václav Kurel
 */
public class TextWriter extends Writer {

    @Override
    public void saveResults(String resultFile, List<Wage> wages) throws IOException {
        File resultF = new File(dataDirectory,resultFile);
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(resultF, true)))) {
            pw.println("Mzda ze dne: " + LocalDate.now());
            pw.format("%-5s%-20s%-20s%-12s%-6s%-8s%-20s%-10s%-10s%-10s%-10s%-10s%-10s%n", "ID", "jméno", "příjmění", "narození", "země", "Kč/hod", "pozice", "hodiny", "HM", "SHM", "ZnD", "OSZ", "CM");
            for (Wage wage : wages) {
                pw.println(wage.toString());
            }
        }
    }

    /**
     * Metoda pro uložení nově přidaných zaměstnanců
     * @param employeesFile
     * @param employees
     * @throws IOException
     */
    public void saveUpdateEmployees(String employeesFile, List<Employee> employees) throws IOException {
        File emplF = new File(dataDirectory,employeesFile);
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(emplF)))) {
            for (Employee employee : employees) {
                pw.println(employee.toString());
            }
        }
    }

    /**
     * Metoda pro uložení nově přidaných odpracovaných hodin
     * @param wagesFile
     * @param wages
     * @throws IOException
     */
    public void saveUpdateHours(String wagesFile, List<Wage> wages) throws IOException {
        File wF = new File(dataDirectory,wagesFile);
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(wF)))) {
            for (Wage wage : wages) {
                pw.println(wage.toIDHourString());
            }
        }
    }

}
