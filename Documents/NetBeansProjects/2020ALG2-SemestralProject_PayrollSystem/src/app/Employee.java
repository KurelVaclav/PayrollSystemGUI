package app;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

/**
 * Třída reprezentující zaměstnance data: id = identifikační číslo, jméno,
 * příjmení, datum narození, národnost, taxa - jaká pozice mu byla přidělena
 *
 * @author Václav Kurel
 */
public class Employee implements Comparable<Employee> {

    //data
    private int id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String nationality;
    private Tax tax;

    /**
     *
     * @param id - id zaměstnance
     * @param firstName - jméno zaměstnance
     * @param lastName - přijmení zaměstnance
     * @param day - int den narození
     * @param month - int měsíc narození
     * @param year - int rok narození
     * @param nationality - národnost
     * @param tax - hodinová taxa zaměstnance dle pozice
     */
    public Employee(int id, String firstName, String lastName, int day, int month, int year, String nationality, Tax tax) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = java.time.LocalDate.of(year, month, day);
        this.nationality = nationality;
        this.tax = tax;
    }

    public int getId() {
        return id;
    }

    public Tax getTax() {
        return tax;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getDayDateOfBirth() {
        int dd = dateOfBirth.getDayOfMonth();
        return dd;
    }

    public int getMonthDateOfBirth() {
        int mm = dateOfBirth.getMonthValue();
        return mm;
    }

    public int getYearDateOfBirth() {
        int yyyy = dateOfBirth.getYear();
        return yyyy;
    }

    public String getNationality() {
        return nationality;
    }

    public String getEmployeeToString() {
        String formattedDateOfBirth = dateOfBirth.format(DateTimeFormatter.ofPattern("dd MM yyyy"));
        return String.format("%-5d%-20s%-20s%-12s%-6s", id, firstName, lastName, formattedDateOfBirth, nationality);
    }

    @Override
    public String toString() {
        return getEmployeeToString() + tax.getTaxesToString();
    }

    @Override
    public int compareTo(Employee o) {
        return this.id - o.id;
    }

    /**
     * Metoda pro seřazení dle příjmení zaměstnance
     */
    public static Comparator<Employee> lastNameComparator = new Comparator<Employee>() {
        @Override
        public int compare(Employee o1, Employee o2) {
            return (int) (o1.getLastName().compareTo(o2.getLastName()));
        }
    };

    /**
     * Metoda pro seřazení dle jména zaměstnance
     */
    public static Comparator<Employee> firstNameComparator = new Comparator<Employee>() {
        @Override
        public int compare(Employee o1, Employee o2) {
            return (int) (o1.getFirstName().compareTo(o2.getFirstName()));
        }
    };
    
//    public static void main(String[] args) {
//        Tax vaclav = new Tax(250, "IT-spravce");
//        Employee e = new Employee(1, "Václav", "Kurel", 1997, 05, 21, "CZ", vaclav);
//        System.out.println(e);
//    }
}
