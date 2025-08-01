import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
public class GroupDeletedTest {
  private WebDriver driver;
  private GroupHelper groupHelper;


  @BeforeEach
  public void setUp() {
    if (driver == null) {
      driver = new FirefoxDriver();
      Runtime.getRuntime().addShutdownHook(new Thread(driver::quit));
      driver.get("http://localhost/addressbook/addressbook/");
      driver.manage().window().setSize(new Dimension(1132, 1063));
      driver.findElement(By.name("user")).sendKeys("admin");
      driver.findElement(By.name("pass")).sendKeys("secret");
      driver.findElement(By.xpath("//input[@value=\'Login\']")).click();
    }
    groupHelper = new GroupHelper(driver);
  }


  @Test
  public void canDeletedGroup() {
    if (!groupHelper.isThereAnyGroup()) {
      groupHelper.createGroup();
    }

    driver.findElement(By.xpath("//input[@name='selected[]']")).click();
    driver.findElement(By.name("delete")).click();
    driver.findElement(By.linkText("group page")).click();
  }
}
