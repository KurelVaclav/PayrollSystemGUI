package filehandling;

import app.Wage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Class Excel Writer. Uses external library POI apache
 *
 * @author Václav Kurel
 */
public class ExcelWiter extends Writer {

    @Override
    public void saveResults(String resultFile, List<Wage> wages) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        createHeaderRow(sheet);
        int rowCount = 0;
        for (Wage wage : wages) {
            Row row = sheet.createRow(++rowCount);
            writeBook(wage, row);
        }
        File resultF = new File(dataDirectory, resultFile);
        try (FileOutputStream fileOut = new FileOutputStream(resultF)) {
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        }
    }

    /**
     * Metoda pro vytvoření hlavičky dat
     *
     * @param sheet
     */
    private void createHeaderRow(Sheet sheet) {
        CellStyle cs = sheet.getWorkbook().createCellStyle();
        Row r = sheet.createRow(0);
        Cell cellID = r.createCell(1);
        cellID.setCellStyle(cs);
        cellID.setCellValue("ID");
        Cell cellFN = r.createCell(2);
        cellFN.setCellStyle(cs);
        cellFN.setCellValue("FirstName");
        Cell cellLN = r.createCell(3);
        cellLN.setCellStyle(cs);
        cellLN.setCellValue("LastName");
        Cell cellDD = r.createCell(4);
        cellDD.setCellStyle(cs);
        cellDD.setCellValue("dd");
        Cell cellMM = r.createCell(5);
        cellMM.setCellStyle(cs);
        cellMM.setCellValue("mm");
        Cell cellyy = r.createCell(6);
        cellyy.setCellStyle(cs);
        cellyy.setCellValue("yyyy");
        Cell cellNation = r.createCell(7);
        cellNation.setCellStyle(cs);
        cellNation.setCellValue("Country");
        Cell cellKcH = r.createCell(8);
        cellKcH.setCellStyle(cs);
        cellKcH.setCellValue("CZK/hour");
        Cell cellPoz = r.createCell(9);
        cellPoz.setCellStyle(cs);
        cellPoz.setCellValue("Position");
        Cell cellhod = r.createCell(10);
        cellhod.setCellStyle(cs);
        cellhod.setCellValue("Hours");
        Cell cellHM = r.createCell(11);
        cellHM.setCellStyle(cs);
        cellHM.setCellValue("GrossWage");
//        Cell cellSHM = r.createCell(12);
//        cellSHM.setCellStyle(cs);
//        cellSHM.setCellValue("SHM");
        Cell cellOdv = r.createCell(12);
        cellOdv.setCellStyle(cs);
        cellOdv.setCellValue("AdvanceTax");
        Cell cellIns = r.createCell(13);
        cellIns.setCellStyle(cs);
        cellIns.setCellValue("SocHealthInsurance");
        Cell cellNet = r.createCell(14);
        cellNet.setCellStyle(cs);
        cellNet.setCellValue("NetWage");
    }

    /**
     * Metda pro zápis do buněk excelu
     *
     * @param wage
     * @param row
     */
    private void writeBook(Wage wage, Row row) {
        Cell cell = row.createCell(1);
        cell.setCellValue(wage.getEmployee().getId());
        cell = row.createCell(2);
        cell.setCellValue(wage.getEmployee().getFirstName());
        cell = row.createCell(3);
        cell.setCellValue(wage.getEmployee().getLastName());
        cell = row.createCell(4);
        cell.setCellValue(wage.getEmployee().getDayDateOfBirth());
        cell = row.createCell(5);
        cell.setCellValue(wage.getEmployee().getMonthDateOfBirth());
        cell = row.createCell(6);
        cell.setCellValue(wage.getEmployee().getYearDateOfBirth());
        cell = row.createCell(7);
        cell.setCellValue(wage.getEmployee().getNationality());
        cell = row.createCell(8);
        cell.setCellValue(wage.getEmployee().getTax().getHourTax());
        cell = row.createCell(9);
        cell.setCellValue(wage.getEmployee().getTax().getPositionName());
        cell = row.createCell(10);
        cell.setCellValue(wage.getHours());
        cell = row.createCell(11);
        cell.setCellValue(wage.getGrossWage());
//        cell = row.createCell(12);
//        cell.setCellValue(wage.getSuperGrossWage());
        cell = row.createCell(12);
        cell.setCellValue(wage.getDownPayment());
        cell = row.createCell(13);
        cell.setCellValue(wage.getShInsurancePayment());
        cell = row.createCell(14);
        cell.setCellValue(wage.getNetWage());
    }

}
