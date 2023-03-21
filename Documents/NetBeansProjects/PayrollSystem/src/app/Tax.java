package app;

/**
 * Class representing the work (job) position of the employee and his hour tax in the
 * company. Data: hourTax, positionName
 *
 * @author Václav Kurel
 */
public class Tax {

    //data
    private double hourTax;
    private String positionName;

    public Tax(double hourTax, String positionName) {
        this.hourTax = hourTax;
        this.positionName = positionName;
    }

    public double getHourTax() {
        return hourTax;
    }

    public String getPositionName() {
        return positionName;
    }

    public String getTaxesToString() {
        return String.format("%-15.2f%-20s", hourTax, positionName);
    }

    @Override
    public String toString() {
        return getTaxesToString();
    }

}
