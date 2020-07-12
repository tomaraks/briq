package tests.utilitiesTests;

import org.testng.annotations.Test;
import utilities.ExcelReader;

public class ExcelToJsonTest {

    @Test
    public void convertExcelDataToJSON() {
        ExcelReader.covertExcelDataIntoJson("leads.xlsx", "test.json");
    }
}
