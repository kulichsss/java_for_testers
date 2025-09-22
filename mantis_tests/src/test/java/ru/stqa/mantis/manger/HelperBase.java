package ru.stqa.mantis.manger;

import okhttp3.OkHttpClient;
import org.openqa.selenium.By;

import java.nio.file.Paths;

public class HelperBase {
    protected final ApplicationManager manager;

    public HelperBase (ApplicationManager manager) {
        this.manager = manager;

    }



    protected void click(By locator) {
        manager.driver().findElement(locator).click();
    }

    protected void type(By locator, String text) {
        click(locator);
        manager.driver().findElement(locator).sendKeys(text);
    }

    protected void attach(By locator, String file) {
        manager.driver().findElement(locator).sendKeys(Paths.get(file).toAbsolutePath().toString());
    }

    protected boolean isElementPresent(By locator) {
        return manager.driver().findElements(locator).size() > 0;
    }
}
