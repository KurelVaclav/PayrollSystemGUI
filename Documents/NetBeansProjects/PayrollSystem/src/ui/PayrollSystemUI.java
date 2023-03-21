package ui;

import app.Employee;
import java.util.Scanner;
import app.PayrollEditor;
import app.Tax;
import app.Wage;
import filehandling.Writer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;
import utils.IllegalInputFromUser;
import utils.PayrollInterface;

/**
 * This class represents the UI of the program, communicates with the user and
 * prints to the screen.
 *
 *
 * @author Václav Kurel
 */
public class PayrollSystemUI {

    public static Scanner sc = new Scanner(System.in);
    public static PayrollInterface pay;

    /**
     * class main to start UI program
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        pay = new PayrollEditor();
        boolean isEnd = false;
        int choice;
        String answer;
        while (!isEnd) {
            menu();
            answer = sc.next();
            try {
                choice = Integer.parseInt(answer);
                switch (choice) {
                    case 0:
                        isEnd = true;
                        break;
                    case 1:
                        employeeMenu();
                        break;
                    case 2:
                        wageMenu();
                        break;
                    default:
                        System.out.println(new IllegalInputFromUser("Nevalidní vstup"));
                }
            } catch (NumberFormatException e) {
                System.out.println(new IllegalInputFromUser("Nevalidní vstup"));
            }
        }
    }

    /**
     * A static method that prints the main menu to the screen
     */
    public static void menu() {
        System.out.println("************************************************************");
        System.out.println("PayrollSystem");
        System.out.println("Vyberte možnost(nejprve se musí načíst zaměstnanci): ");
        System.out.println("1 - správa seznamu zaměstnanců");
        System.out.println("2 - mzdový portál");
        System.out.println("0 - ukončení programu");
        System.out.println("************************************************************");
    }

    /**
     * static method that prints the employee management menu
     */
    public static void employeeMenu() {
        System.out.println("Správa seznamu zaměstnanců: ");
        try {
            loadEmployee();
            int choice;
            String answer;
            boolean isEnd = false;
            while (!isEnd) {
                System.out.println("Vyberte možnost(musí být celé číslo): ");
                System.out.println("1 - přidání nového zaměstnance");
                System.out.println("2 - výpis seznamu zaměstnanců");
                System.out.println("3 - najít zaměstnance dle ID");
                System.out.println("0 - ukončení správy seznamu zaměstnanců");
                answer = sc.next();
                try {
                    choice = Integer.parseInt(answer);
                    switch (choice) {
                        case 1:
                            addEmployeeMenu();
                            break;
                        case 2:
                            listOfEmployeesMenu();
                            break;
                        case 3:
                            searchEmployeeIDMenu();
                            break;
                        case 0:
                            isEnd = true;
                            break;
                        default:
                            System.out.println(new IllegalInputFromUser("nevalidní vstup"));
                    }
                } catch (NumberFormatException e) {
                    System.out.println(new IllegalInputFromUser("nevalidní vstup"));
                }
            }
//        } catch (FileNotFoundException ex) {
//            System.out.println("Soubor nebyl nalezen");
        } catch (IOException ex) {
            System.out.println("Neplatný formát souboru");
        }
    }

    /**
     * Static method to load the employee file
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void loadEmployee() throws IOException {
        System.out.println("Zadejte název souboru se zaměstnanci: ");
        String employeeFile = sc.next();
        File file = new File(Writer.dataDirectory, employeeFile);
        if (file.exists()) {
            pay.loadEmployees(employeeFile);
        } else {
            System.out.println("Zadaný soubor neexistuje.");
            System.out.println("Přejete si vytvořit nový soubor? a/n");
            String choice = sc.next();
            if (choice.toLowerCase().charAt(0) == 'a') {
                file.createNewFile();
            }
        }
        System.out.println(pay.getEmployeesInfo());
    }

    /**
     * Static method to add an employee to the employee list
     *
     * @return new Employee pokud se podaří naparsovat, jinak null
     */
    public static Employee addEmployee() {
        Employee e;
        try {
            System.out.println("Zadejte ID: ");
            String idAnswer = sc.next();
            System.out.println("Zadejte jméno: ");
            String firstName = sc.next();
            System.out.println("Zadejte příjmení: ");
            String lastName = sc.next();
            System.out.println("Zadejte den narození: ");
            String dayAnswer = sc.next();
            System.out.println("Zadejte měsíc narození: ");
            String monthAnswer = sc.next();
            System.out.println("Zadejte rok narození: ");
            String yearAnswer = sc.next();
            System.out.println("Zadejte národnost: ");
            String nationality = sc.next();
            System.out.println("Zadejte hodinovou sazbu: ");
            String hourTaxAnswer = sc.next();
            System.out.println("Zadejte název práce: ");
            sc.useDelimiter(Pattern.compile("[\\n;]"));
            String positionName = sc.next();
            if (positionName.matches("[a-zA-Z0-9._-]+[ ]+[a-zA-Z0-9._-]+")) {
                positionName = positionName.replace(" ", "_");
            }
            int id = Integer.parseInt(idAnswer);
            int day = Integer.parseInt(dayAnswer);
            int month = Integer.parseInt(monthAnswer);
            int year = Integer.parseInt(yearAnswer);
            double hourTax = Double.parseDouble(hourTaxAnswer);
            Tax tax = new Tax(hourTax, positionName);
            e = new Employee(id, firstName, lastName, day, month, year, nationality, tax);
            return e;
        } catch (NumberFormatException ex) {
            System.out.println(new IllegalInputFromUser("id,den,měsíc,rok,hodinová sazba musí být číslo"));
        }
        return null;
    }

    /**
     * Static method to add employees and save new data to the employees file
     *
     */
    public static void addEmployeeMenu() {
        System.out.println("Zadání nových zaměstnanců: ");
        boolean isEnd = false;
        try {
            while (!isEnd) {
                Employee e;
                e = addEmployee();
                pay.addEmployeeToList(e);
                System.out.println("Přídat dalšího zaměstnance? a/n");
                String choice = sc.next();
                isEnd = choice.toLowerCase().charAt(0) == 'n';
            }
            System.out.println(pay.getEmployeesInfo());
            System.out.println("Přejete si nové data uložit? a/n");
            String choice = sc.next();
            if (choice.toLowerCase().charAt(0) == 'a') {
                System.out.println("Zadejte název souboru se zaměstnanci: ");
                String employeeFile = sc.next();
                try {
                    pay.saveAddedEmployees(employeeFile);
                } catch (IOException ex) {
                    System.out.println("Soubor se nepodařilo uložit");
                }
            }
        } catch (Exception ex) {
            System.out.println("Nevalidní vstup");
        }
    }

    /**
     * Static method to print the list of employees, can be sorted according to
     * the user's choice
     */
    public static void listOfEmployeesMenu() {
        System.out.println("Seřadit dle (vyberte možnost): ");
        System.out.println("1 - ID");
        System.out.println("2 - Jména");
        System.out.println("3 - Příjmení");
        System.out.println("0 - Neseřazený seznam");
        String reply = sc.next();
        try {
            int option = Integer.parseInt(reply);
            switch (option) {
                case 0:
                    System.out.println(pay.getEmployeesInfo());
                    break;
                case 1:
                    System.out.println(pay.getEmployeesInfoSortedByID());
                    break;
                case 2:
                    System.out.println(pay.getEmployeesInfoSortedByFirstName());
                    break;
                case 3:
                    System.out.println(pay.getEmployeesInfoSortedByLastName());
                    break;
                default:
                    System.out.println(new IllegalInputFromUser("Nevalidní vstup"));
            }
        } catch (NumberFormatException e) {
            System.out.println(new IllegalInputFromUser("Nevalidní vstup"));
        }
    }

    /**
     * Static method for searching employees by ID
     */
    public static void searchEmployeeIDMenu() {
        boolean search = false;
        String answer;
        int id;
        while (!search) {
            try {
                System.out.println("Zadejte ID zaměstnance");
                answer = sc.next();
                try {
                    id = Integer.parseInt(answer);
                    Employee e = pay.findEmployee(id);
                    System.out.println(e.toString());
                    System.out.println("Najít dalšího zaměstnance? a/n");
                    String repl = sc.next();
                    search = repl.toLowerCase().charAt(0) == 'n';
                } catch (NumberFormatException e) {
                    System.out.println(new IllegalInputFromUser("Nevalidní vstup"));
                }
            } catch (NoSuchElementException e) {
                System.out.println(e);
            }
        }
    }

    /**
     * Static method for entering hours worked and calculating wages
     */
    public static void wageMenu() {
        System.out.println("Mzdový portál: ");
        try {
            loadHours();
            int choice;
            String answer;
            boolean isEnd = false;
            while (!isEnd) {
                System.out.println("Vyberte možnost(musí být celé číslo): ");
                System.out.println("1 - přidání odpracovaných hodin zaměstnanci");
                System.out.println("2 - výpis seznamu zaměstnanců s odpracovanými hodinami");
                System.out.println("3 - výpočet mezd");
                System.out.println("0 - ukončení správy seznamu zaměstnanců");
                answer = sc.next();
                try {
                    choice = Integer.parseInt(answer);
                    switch (choice) {
                        case 0:
                            isEnd = true;
                            break;
                        case 1:
                            addHoursMenu();
                            break;
                        case 2:
                            System.out.println(pay.getHoursInfo());
                            break;
                        case 3:
                            calculateWage();
                            break;
                        default:
                            System.out.println("nevalidní vstup");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Nevalidní vstup");
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("Nepodařilo se nalézt ID zaměstnance");
        } catch (IOException ex) {
            System.out.println("Nepodařilo se naparsovat");
        }
    }

    /**
     * Static method to load the hours worked file
     *
     * @throws FileNotFoundException
     */
    public static void loadHours() throws FileNotFoundException, IOException {
        System.out.println("Zadejte název souboru hodinových mezd: ");
        String wagesFile = sc.next();
//        try {
        pay.loadHours(wagesFile);
//        } catch (IOException ex) {
//            System.out.println("Nepodařilo se naparsovat");
//        }
        System.out.println(pay.getHoursInfo());
    }

    /**
     * Static method to add working hours to the employee
     *
     * @return new Wage if parsed successfully, else null
     */
    public static Wage addHours() {
        System.out.println("Zadejte ID: ");
        String idAnswer = sc.next();
        System.out.println("Zadejte počet odpracovaných hodin: ");
        String hoursAnswer = sc.next();
        try {
            int id = Integer.parseInt(idAnswer);
            int hours = Integer.parseInt(hoursAnswer);
            Employee e = pay.findEmployee(id);
            Wage wage = new Wage(e, hours);
            return wage;
        } catch (NumberFormatException ex) {
            System.out.println("Nepodařilo se naparsovat,id, hodiny musí být číslo");
        }
        return null;
    }

    /**
     * Static method for adding hours worked menu and saving them
     */
    public static void addHoursMenu() {
        System.out.println("Zadání nové pracovní hodiny zaměstnanci: ");
        boolean isEnd = false;
        while (!isEnd) {
            Wage wage = addHours();
            pay.addWageToList(wage);
            System.out.println("Přídat dalšího odpracované hodiny? a/n");
            String choice = sc.next();
            isEnd = choice.toLowerCase().charAt(0) == 'n';
        }
        System.out.println(pay.getHoursInfo());
        System.out.println("Přejete si nové data uložit? a/n");
        String choice = sc.next();
        if (choice.toLowerCase().charAt(0) == 'a') {
            System.out.println("Zadejte název souboru s odpracovanými hodiny: ");
            String wagesFile = sc.next();
            try {
                pay.saveAddedHours(wagesFile);
            } catch (IOException ex) {
                System.out.println("Soubor se nepodařilo uložit");
            }
        }
    }

    /**
     * Static method to calculate wages and save new results
     */
    public static void calculateWage() {
        pay.calculateWages();
        System.out.println(pay.getWagesInfo());
        System.out.println("Přejete si nové data uložit? a/n");
        String choice = sc.next();
        if (choice.toLowerCase().charAt(0) == 'a') {
            System.out.println("Zadejte název výstupního souboru: ");
            String resultFile = sc.next();
            try {
                pay.saveWages(resultFile);
            } catch (IOException ex) {
                System.out.println("Soubor se nepodařilo uložit");
            } catch (IllegalArgumentException iea) {
                System.out.println("Nepodporovaná koncový formát souboru");
            }
        }
    }

}
