package manager;

import model.GroupData;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ApplicationManager {
    protected static WebDriver driver;

    public void init() {
        if (driver == null) {
            driver = new FirefoxDriver();
            Runtime.getRuntime().addShutdownHook(new Thread(driver::quit));
            driver.get("http://localhost/addressbook/addressbook/");
            driver.manage().window().setSize(new Dimension(1132, 1062));
            driver.findElement(By.name("user")).sendKeys("admin");
            driver.findElement(By.name("pass")).sendKeys("secret");
            driver.findElement(By.xpath("//input[@value=\'Login\']")).click();
        }
    }

    public boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        }
        catch (NoSuchElementException exception) {
            return false;
        }
    }

    public void createGroup(GroupData group) {
        driver.findElement(By.name("new")).click();
        driver.findElement(By.name("name")).click();
        driver.findElement(By.name("name")).sendKeys(group.name());
        driver.findElement(By.name("header")).click();
        driver.findElement(By.name("header")).sendKeys(group.header());
        driver.findElement(By.name("footer")).click();
        driver.findElement(By.name("footer")).sendKeys(group.footer());
        driver.findElement(By.name("submit")).click();
        driver.findElement(By.linkText("group page")).click();
    }

    public void openGroupPage() {
        if (!isElementPresent(By.name("new"))) {
            driver.findElement(By.linkText("groups")).click();
        }
    }

    public boolean isGroupPresent() {
      return isElementPresent(By.name("selected[]"));
    }

    public void deleteGroup() {
      driver.findElement(By.xpath("//input[@name='selected[]']")).click();
      driver.findElement(By.name("delete")).click();
      driver.findElement(By.linkText("group page")).click();
    }
}
