package app;

/**
 * Třída reprezentující pracovní pozici zaměstnance a jeho taxu ve firmě 
 * data: hourTax = hodinová sazba, název pozice
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
        return String.format("%-8.2f%-20s", hourTax, positionName);
    }

    @Override
    public String toString() {
        return getTaxesToString();
    }

}
