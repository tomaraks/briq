package pages.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CraneWatchPage {
    private WebDriver driver;
    private By viewMap = By.cssSelector("div.hero-bar__content a.btn--wide");

    public CraneWatchPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getURL() {
        return driver.getCurrentUrl();
    }

    public void navigateToNewCityPage(String cityName) {
        driver.get("https://www.bizjournals.com/" + cityName + "/feature/crane-watch");
    }

    public MapPage clickViewMap() {
        driver.findElement(viewMap).click();
        return new MapPage(driver);
    }

}

