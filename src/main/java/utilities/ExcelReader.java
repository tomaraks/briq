package utilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ExcelReader {

    public static void covertExcelDataIntoJson(String excelFileName, String jsonFileName) {
        try {
            // Getting Excel File
            File file = new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + excelFileName);   //creating a new file instance
            FileInputStream fis = new FileInputStream(file);

            // Starting Reading of Excel File
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);
            Iterator<Row> itr = sheet.iterator();
            ArrayList<String> columns = new ArrayList<String>();
            HashMap<Integer, ArrayList> valuesByColumn = new HashMap<Integer, ArrayList>();
            while (itr.hasNext()) {
                ArrayList<String> values = new ArrayList<String>();
                Row row = itr.next();
                // Reading Columns First
                if (row.getRowNum() == 0) {
                    Iterator<Cell> cellIterator = row.cellIterator();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        columns.add(cell.getStringCellValue());
                    }
                } else {
                    // Now Reading values
                    for (int cn = 0; cn < columns.size(); cn++) {
                        Cell cell = row.getCell(cn, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        switch (cell.getCellType()) {
                            case STRING:
                                values.add(cell.getStringCellValue());
                                break;
                            case NUMERIC:
                                values.add(String.valueOf(cell.getNumericCellValue()));
                                break;
                            case _NONE:
                                values.add("");
                                break;
                            case BLANK:
                                values.add("");
                                break;
                            default:
                                values.add(cell.getStringCellValue());
                                break;
                        }
                    }
                    valuesByColumn.put(row.getRowNum(), values);
                }
            }

            // Creating JSON Array
            JSONArray jsonArray = new JSONArray();
            for (int j = 1; j <= valuesByColumn.size(); j++) {
                JSONObject jsonObject = new JSONObject();
                for (int k = 0; k < columns.size(); k++) {
                    jsonObject.put(columns.get(k), valuesByColumn.get(j).get(k));
                }
                jsonArray.put(j - 1, jsonObject);
            }

            // Writing JSON Array into JSON File
            FileWriter file1 = new FileWriter(System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + jsonFileName);   //creating a new file instance
            file1.write(jsonArray.toString());
            file1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

