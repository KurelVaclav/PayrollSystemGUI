package app;

import java.text.Collator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Locale;

/**
 * This class represents Employee data: id = identification number, name,
 * lastname, date of birth, nationality, position, tax - depends on his position
 *
 * @author Václav Kurel
 * @since 1.0
 */
public class Employee implements Comparable<Employee> {

    //data
    private final int id;
    private final String firstName;
    private final String lastName;
    private final LocalDate dateOfBirth;
    private final String nationality;
    private final Tax tax;
    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd mm uuuu");

    /**
     *
     * @param id - emplyee's id 
     * @param firstName - first name
     * @param lastName - last name
     * @param day - int day of birth
     * @param month - int month of birth
     * @param year - int year of birth
     * @param nationality - String nationality
     * @param tax - hour's tax depends on employee's work position
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
        return String.format("%-5d%-20s%-20s%-15s%-15s", id, firstName, lastName, formattedDateOfBirth, nationality);
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
     * Method for sorting by employee last name 
     */
    public static Comparator<Employee> lastNameComparator = new Comparator<Employee>() {
        @Override
        public int compare(Employee o1, Employee o2) {
            Collator c = Collator.getInstance(new Locale("cs", "CZ"));
            return c.compare(o1.getLastName(), o2.getLastName());
        }
    };

    /**
     * Method for sorting by employee first name
     */
    public static Comparator<Employee> firstNameComparator = new Comparator<Employee>() {
        @Override
        public int compare(Employee o1, Employee o2) {
            Collator c = Collator.getInstance(new Locale("cs", "CZ"));
            return c.compare(o1.getFirstName(), o2.getFirstName());
        }
    };
    
//    public static void main(String[] args) {
//        Tax vaclav = new Tax(250, "IT-spravce");
//        Employee e = new Employee(1, "Václav", "Kurel", 1997, 05, 21, "CZ", vaclav);
//        System.out.println(e);
//    }
}
