package ru.stqa.mantis.manger;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Properties;

public class ApplicationManager {
    private WebDriver driver;
    private String client;
    private Properties properties;
    private SessionHelper session;
    private HttpSessionHelper httpSessionHelper;
    private JamesCliHelper jamesCliHelper;

    public void init(String browser, Properties properties) {
        this.client = browser;
        this.properties = properties;

    }

    public WebDriver driver() {
        if (driver == null) {
            if ("firefox".equals(client)) {
                driver = new FirefoxDriver();
            }
            else if ("chrome".equals(client)) {
                driver = new ChromeDriver();
            }
            else {
                throw new IllegalArgumentException(String.format("Unknown browser %s", client));
            }
            Runtime.getRuntime().addShutdownHook(new Thread(driver::quit));
            driver.get(properties.getProperty("web.baseUrl"));
            driver.manage().window().setSize(new Dimension(1132, 1062));
        }
        return driver;
    }

    public SessionHelper session() {
        if (session == null) {
            session = new SessionHelper(this);
        }
        return session;
    }

    public HttpSessionHelper httpSession() {
        if (httpSessionHelper == null) {
            httpSessionHelper = new HttpSessionHelper(this);
        }
        return httpSessionHelper;
    }

    public JamesCliHelper jamesCli() {
        if (jamesCliHelper == null) {
            jamesCliHelper = new JamesCliHelper(this);
        }
        return jamesCliHelper;
    }

    public String property(String name) {
        return properties.getProperty(name);
    }


}
