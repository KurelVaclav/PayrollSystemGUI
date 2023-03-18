package app;

import java.util.Comparator;

/**
 * Třída reprezentující vypočtenou mzdu změstnanci data: zaměstnance, hrubá
 * mzda, super hrubí mzda, záloha na daň, odvody na sociální a zdravotní
 * pojištění, čistá mzda
 *
 * @author Václav Kurel
 */
public class Wage {

    //data
    private Employee employee;
    private int hours;
    private double grossWage; //hrubá mzda
    private double superGrossWage; // super hrubá mzda
    private double downPayment; //záloha na daň
    private double shInsurancePayment; // odvody na sociální a zdravodní pojištění
    private double netWage; //čistá mzda
    static final double ODV_ZAM = 0.34;
    static final double DAN_VYP = 0.15;
    static final double MAXDAN_SLEVA = 2070;
    static final double SOC = 0.065;
    static final double ZDRAV = 0.045;

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

    public double getSuperGrossWage() {
        return superGrossWage;
    }

    public double getDownPayment() {
        return downPayment;
    }

    public double getShInsurancePayment() {
        return shInsurancePayment;
    }

    public double getNetWage() {
        return netWage;
    }

    public void setSuperGrossWage() {
        double contributions = grossWage * ODV_ZAM;
        this.superGrossWage = grossWage + contributions;
    }

    public void setDownPayment() {
        double taxAdvance = DAN_VYP * superGrossWage;
        if (taxAdvance > MAXDAN_SLEVA) {
            this.downPayment = taxAdvance - MAXDAN_SLEVA;
        } else {
            this.downPayment = 0;
        }
    }

    public void setSHInsurancePayment() {
        double sInsurance = SOC * getGrossWage();
        double hInsurance = ZDRAV * getGrossWage();
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
     * Metoda pro výpis odpracovaných hodin a k tomu výpočet mzdy
     *
     * @return String
     */
    private String wageToString() {
        return String.format("%-10d%-10.2f%-10.2f%-10.2f%-10.2f%-10.2f", hours, grossWage, superGrossWage, downPayment, shInsurancePayment, netWage);
    }

    /**
     * Metoda pro výpis zaměstnance a jeho odpracované hodiny
     *
     * @return String zaměstnanec odpracované hodiny
     */
    public String toHourString() {
        return employee.toString() + " " + String.format("%-10d", hours);
    }

    /**
     * Metoda pro výpis ID a odpracovaný hodiny zaměstnance
     *
     * @return String ID odpracované hodiny
     */
    public String toIDHourString() {
        return employee.getId() + " " + String.format("%-10d", hours);
    }

    /**
     * Metoda pro seřazení dle id zaměstnanců
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
