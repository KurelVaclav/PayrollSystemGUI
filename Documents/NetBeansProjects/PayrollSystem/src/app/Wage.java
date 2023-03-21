package app;

import java.util.Comparator;

/**
 * Class representing the calculated employee's wages. Data: employee, gross
 * salary, advance tax, social and health insurance payment/contributions, net,
 * wage
 *
 * @author Václav Kurel
 */
public class Wage {

    //data
    private Employee employee;
    private int hours;
    private double grossWage; //hrubá mzda
//    private double superGrossWage; // super hrubá mzda
    private double downPayment; //záloha na daň
    private double shInsurancePayment; // odvody na sociální a zdravodní pojištění
    private double netWage; //čistá mzda
//    static final double ODV_ZAM = 0.34;
    static final double TAX_CALCULATED = 0.15;
    static final double MAXTAX_DISCOUNT = 2570;
    static final double SOC = 0.065;
    static final double HEALTH = 0.045;

    public Wage(Employee employee, int hours) {
        this.employee = employee;
        this.hours = hours;
    }

    public void setGrossWage(int hours, Employee e) {
        this.hours = hours;
        Tax tax = e.getTax();
        double t = tax.getHourTax();
        this.grossWage = hours * t;
    }

    public Employee getEmployee() {
        return employee;
    }

    public int getHours() {
        return hours;
    }

    public double getGrossWage() {
        return grossWage;
    }

//    public double getSuperGrossWage() {
//        return superGrossWage;
//    }

    public double getDownPayment() {
        return downPayment;
    }

    public double getShInsurancePayment() {
        return shInsurancePayment;
    }

    public double getNetWage() {
        return netWage;
    }

//    public void setSuperGrossWage() {
//        double contributions = grossWage * ODV_ZAM;
//        this.superGrossWage = grossWage + contributions;
//    }
    public void setDownPayment() {
//        double taxAdvance = TAX_CALCULATED * superGrossWage;
        double taxAdvance = TAX_CALCULATED * grossWage;
        if (taxAdvance > MAXTAX_DISCOUNT) {
            this.downPayment = taxAdvance - MAXTAX_DISCOUNT;
        } else {
            this.downPayment = 0;
        }
    }

    public void setSHInsurancePayment() {
        double sInsurance = SOC * getGrossWage();
        double hInsurance = HEALTH * getGrossWage();
        this.shInsurancePayment = sInsurance + hInsurance;
    }

    public void setNetWage() {
        this.netWage = getGrossWage() - (getDownPayment() + getShInsurancePayment());
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    @Override
    public String toString() {
        return employee.toString() + wageToString();
    }

    /**
     * Method to extract working hours and calculated wages
     *
     * @return String
     */
//    private String wageToString() {
//        return String.format("%-10d%-10.2f%-10.2f%-10.2f%-10.2f%-10.2f", hours, grossWage, superGrossWage, downPayment, shInsurancePayment, netWage);
//    }
    private String wageToString() {
        return String.format("%-10d%-20.2f%-20.2f%-20.2f%-20.2f", hours, grossWage, downPayment, shInsurancePayment, netWage);
    }

    /**
     *Method to extract employee and his working hours
     *
     * @return String employee + wokring hours
     */
    public String toHourString() {
        return employee.toString() + " " + String.format("%-10d", hours);
    }

    /**
     * Method to extract ID and employee's working hours
     *
     * @return String ID + working hours
     */
    public String toIDHourString() {
        return employee.getId() + " " + String.format("%-10d", hours);
    }

    /**
     * Method to sort by employee's ID
     */
    public static Comparator<Wage> idComparator = new Comparator<Wage>() {
        @Override
        public int compare(Wage o1, Wage o2) {
            if (o1.getEmployee().getId() > o2.getEmployee().getId()) {
                return 1;
            } else if (o1.getEmployee().getId() == o2.getEmployee().getId()) {
                return 0;
            } else {
                return -1;
            }
        }
    };

}
