package sa.gov.amana.alriyadh.job.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelGenerator {

    public static Boolean createExcelSheet(String fileName, String filePath, String sheetName, List<String> headers, List<String> arabicHeaders, List<Map<String, Object>> values) {

        if (headers == null || values == null || headers.isEmpty() || values.isEmpty()) {
            throw new IllegalArgumentException("Headers and values must not be empty");
        }

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet(sheetName);

            // Create and style header row
            XSSFRow headerRow = sheet.createRow(0);
            XSSFCellStyle headerStyle = createHeaderStyle(workbook);

            // Write header
            if (CollectionUtils.isNotEmpty(arabicHeaders)) {
                writeHeader(arabicHeaders, headerRow, headerStyle);
            } else {
                writeHeader(headers, headerRow, headerStyle);
            }

            // Write data
            writeData(sheet, headers, values);

            // Create table
            // createTable(workbook, sheet, arabicHeaders, headers.size(), values.size());

            // Apply auto-filter to the header row
            sheet.setAutoFilter(new CellRangeAddress(0, 0, 0, headers.size() - 1));

            // Auto-size columns
            for (int i = 0; i < headers.size(); i++) {
                sheet.autoSizeColumn(i);
            }

            // Write to file
            FileUtil.createFilePath(filePath + fileName);
            try (FileOutputStream fileOut = new FileOutputStream(filePath + fileName)) {
                workbook.write(fileOut);
                System.out.println("Excel file created successfully at: " + filePath);
            }

            return Boolean.TRUE;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Boolean.FALSE;
    }

    private static XSSFCellStyle createHeaderStyle(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        XSSFFont font = workbook.createFont();
        font.setBold(Boolean.TRUE);
        style.setFont(font);

        return style;
    }

    private static void writeHeader(List<String> arabicHeaders, XSSFRow headerRow, XSSFCellStyle headerStyle) {
        for (int i = 0; i < arabicHeaders.size(); i++) {
            XSSFCell header = headerRow.createCell(i);
            header.setCellValue(arabicHeaders.get(i));
            header.setCellStyle(headerStyle);
        }
    }

    private static void writeData(XSSFSheet sheet, List<String> headers, List<Map<String, Object>> values) {

        int rowNum = 1;
        for (Map<String, Object> row : values) {
            Row valueRow = sheet.createRow(rowNum);

            for (int i = 0; i < headers.size(); i++) {
                Cell cell = valueRow.createCell(i);
                cell.setCellValue(String.valueOf(row.get(headers.get(i))));
            }
            rowNum++;
        }
    }

    private static void createTable(XSSFWorkbook workbook, XSSFSheet sheet, List<String> arabicHeaders, int headersSize, int valuesSize) {

        // Define the table range
        String range = "A1:" + getColumnLetter(headersSize) + (valuesSize + 1);
        AreaReference areaRef = new AreaReference(range, workbook.getSpreadsheetVersion());

        // Create table
        XSSFTable table = sheet.createTable(areaRef);
        table.setDisplayName("Table1");
        table.setName("Table1");

        // Set a valid table ID
        table.getCTTable().setId(1);

        // Apply a valid built-in table style
//        table.setStyleName("TableStyleMedium6");

        // Ensure the table style is initialized correctly
//        if (table.getStyle() == null) {
//            table.getCTTable().addNewTableStyleInfo();
//        }

//        XSSFTableStyleInfo style = (XSSFTableStyleInfo) table.getStyle();
//        style.setShowColumnStripes(false);
//        style.setShowRowStripes(true);
//        style.setFirstColumn(false);
//        style.setLastColumn(false);
//
//        // Ensure TableColumns exist before adding
//        if (table.getCTTable().getTableColumns() == null) {
//            table.getCTTable().addNewTableColumns();
//        }
//        table.getCTTable().getTableColumns().setCount(headersSize);

        // Add the columns with the correct names
//        for (int i = 0; i < headersSize; i++) {
//            table.getCTTable().getTableColumns().addNewTableColumn().setId(i + 1);
//            table.getCTTable().getTableColumns().getTableColumnArray(i).setName(arabicHeaders.get(i));
//        }

    }

    private static String getColumnLetter(int columnNumber) {
        StringBuilder columnLetter = new StringBuilder();
        while (columnNumber > 0) {
            int remainder = (columnNumber - 1) % 26;
            columnLetter.insert(0, (char) (remainder + 'A'));
            columnNumber = (columnNumber - 1) / 26;
        }
        return columnLetter.toString();
    }


    public static void main(String[] args) throws IOException {

        List<String> headers = new ArrayList<>();
        headers.add("Name");
        headers.add("Age");
        headers.add("City");

        List<Map<String, Object>> resultSet = new ArrayList<Map<String, Object>>();
        Map<String, Object> row1 = new HashMap<String, Object>();
        row1.put("Name", "John Doe");
        row1.put("Age", "30");
        row1.put("City", "New York");
        resultSet.add(row1);

        Map<String, Object> row2 = new HashMap<>();
        row2.put("Name", "Jo");
        row2.put("Age", "40");
        row2.put("City", "York");
        resultSet.add(row2);

        Map<String, Object> row3 = new HashMap<String, Object>();
        row3.put("Name", "Doe");
        row3.put("Age", "50");
        row3.put("City", "ss");
        resultSet.add(row3);

        String fileName = "OutputTest" + FileUtil.generateCurrentDatePath(Boolean.TRUE) + ".xlsx";
        String filePath = "C:/img/SECReport/" + FileUtil.generateCurrentDatePath(Boolean.FALSE);
        String sheetName = "sheetTest";

//        Boolean workbookCreated = createExcelSheet(fileName, filePath, sheetName, headers, resultSet);

    }

}
