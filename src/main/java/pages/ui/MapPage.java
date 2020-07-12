package pages.ui;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;

public class MapPage {
    private WebDriver driver;
    private By map = By.cssSelector("iframe.cw-map");
    private By closePopup = By.cssSelector("div.esriPopupWrapper .titleButton.close");
    private By enterPopUp = By.cssSelector("div.bx-wrap div.bx-step-1 input[type='email']");
    private By allPins = By.cssSelector("#main-page #map #map_layers > #map_gc > svg > g image");
    private By header = By.cssSelector("div.esriViewPopup .mainSection .header");
    private By table = By.cssSelector("div.esriViewPopup .mainSection .attrTable tbody tr");
    private By iframeMap = By.cssSelector("iframe.cw-map");
    private By attributeName = By.className("attrName");
    private By attributeValue = By.className("attrValue");

    public MapPage(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForMapToLoad() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 90);
        wait.until(ExpectedConditions.visibilityOfElementLocated(map));
        scrollToMap();
        WebElement enterElement = wait.until(ExpectedConditions.visibilityOfElementLocated(enterPopUp));
        enterElement.sendKeys("akshaySayAkki");
        Actions action = new Actions(driver);
        action.sendKeys(Keys.ESCAPE).build().perform();
        Thread.sleep(2000);
    }

    public int getCountOfPins() {
        String map = driver.findElement(iframeMap).getAttribute("src");
        driver.navigate().to(map);
        List<WebElement> webElements = driver.findElements(allPins);
        return webElements.size();
    }

    public List<WebElement> getAllPins() {
        return driver.findElements(allPins);
    }

    public String getHeaderText() {
        return driver.findElement(header).getText();
    }

    public void closePopUp() {
        driver.findElement(closePopup).click();
    }

    public HashMap<String, String> getTableValues() {
        List<WebElement> webElementList = driver.findElements(table);
        HashMap<String, String> tableValues = new HashMap<String, String>();
        for(WebElement webElement: webElementList) {
            tableValues.put(webElement.findElement(attributeName).getText(), webElement.findElement(attributeValue).getText());
        }
        return tableValues;
    }

    public void clickOnImage(WebElement webElement) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOf(webElement));
        Actions actions = new Actions(driver);
        actions.moveToElement(webElement).click().build().perform();
    }

    public void scrollToMap() {
        JavascriptExecutor je = (JavascriptExecutor) driver;
        je.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(map));
    }

}
