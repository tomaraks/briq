package tests.uiTests;

import base.BaseTest;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.ui.MapPage;
import utilities.ExcelWriter;

import java.util.ArrayList;
import java.util.HashMap;

public class DetailsTest extends BaseTest {
    // Kindly enter city name is small letter
    private final String CITY_NAME = "milwaukee";

    @Test
    public void getDataFromPinsTest() throws InterruptedException {
        // Moving to correct City check
        if (!craneWatchPage.getURL().contains(CITY_NAME)) {
            craneWatchPage.navigateToNewCityPage(CITY_NAME);
        }

        MapPage mapPage = craneWatchPage.clickViewMap();
        mapPage.waitForMapToLoad();
        System.out.println("Number of Pins are " + mapPage.getCountOfPins());
        ArrayList<String> headers = new ArrayList<String>();
        ArrayList<HashMap<String, String>> values = new ArrayList<HashMap<String, String>>();
        for (WebElement webElement : mapPage.getAllPins()) {
            try {
                mapPage.clickOnImage(webElement);
                headers.add(mapPage.getHeaderText());
                values.add(mapPage.getTableValues());
                mapPage.closePopUp();
            } catch (StaleElementReferenceException se) {
                headers.add("");
                values.add(new HashMap<String, String>());
            }
        }

        for (int i = 0; i < headers.size(); i++) {
            values.get(i).put("Title", headers.get(i));
        }

        ExcelWriter.writeMAPToExcel(values, CITY_NAME);
    }
}
