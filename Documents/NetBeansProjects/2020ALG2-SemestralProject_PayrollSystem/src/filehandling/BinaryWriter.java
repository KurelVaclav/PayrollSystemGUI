package filehandling;

import app.Wage;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Václav Kurel
 */
public class BinaryWriter extends Writer {

    @Override
    public void saveResults(String resultFile, List<Wage> wages) throws IOException {
        File resultF = new File(dataDirectory, resultFile);
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(resultF))) {
            dos.writeUTF("Mzda ze dne: " + LocalDate.now().toString());
            for (Wage wage : wages) {
                dos.writeInt(wage.getEmployee().getId());
                dos.writeUTF(wage.getEmployee().getFirstName());
                dos.writeUTF(wage.getEmployee().getLastName());
                dos.writeInt(wage.getEmployee().getDayDateOfBirth());
                dos.writeInt(wage.getEmployee().getMonthDateOfBirth());
                dos.writeInt(wage.getEmployee().getYearDateOfBirth());
                dos.writeUTF(wage.getEmployee().getNationality());
                dos.writeDouble(wage.getEmployee().getTax().getHourTax());
                dos.writeUTF(wage.getEmployee().getTax().getPositionName());
                dos.writeInt(wage.getHours());
                dos.writeDouble(wage.getGrossWage());
                dos.writeDouble(wage.getSuperGrossWage());
                dos.writeDouble(wage.getDownPayment());
                dos.writeDouble(wage.getShInsurancePayment());
                dos.writeDouble(wage.getNetWage());
            }
        }
    }

    /**
     * Metoda pro vytvoření souboru se zaměstnanci z dat na txt soubor a jeho
     * načtení
     *
     * @param employeeFile
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void createEmployees(String employeeFile) throws FileNotFoundException, IOException {
        File binaryFile = new File(dataDirectory, employeeFile);
        File textFile = new File(dataDirectory, employeeFile.replace(".dat", ".txt"));
        try (Scanner out = new Scanner(textFile)) {
            try (DataOutputStream in = new DataOutputStream(new FileOutputStream(binaryFile))) {
                while (out.hasNext()) {
                    in.writeInt(out.nextInt()); //ID
                    in.writeUTF(out.next()); //firstName
                    in.writeUTF(out.next()); //lastName
                    in.writeInt(out.nextInt()); //dd
                    in.writeInt(out.nextInt()); //mm
                    in.writeInt(out.nextInt()); //yyyy
                    in.writeUTF(out.next()); //nationality
                    in.writeDouble(out.nextDouble()); //hourTax
                    in.writeUTF(out.next()); //positionName
                }
            }
        }
    }

    /**
     * Metoda pro vytvoření souboru s odpracovanými hodinami z dat na txt soubor
     * a jeho načtení
     *
     * @param hoursFile
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void createHours(String hoursFile) throws FileNotFoundException, IOException {
        File binaryFile = new File(dataDirectory, hoursFile);
        File textFile = new File(dataDirectory, hoursFile.replace(".dat", ".txt"));
        try (Scanner out = new Scanner(textFile)) {
            try (DataOutputStream in = new DataOutputStream(new FileOutputStream(binaryFile))) {
                while (out.hasNext()) {
                    in.writeInt(out.nextInt()); //ID
                    in.writeInt(out.nextInt()); //hours
                }
            }
        }
    }

}
