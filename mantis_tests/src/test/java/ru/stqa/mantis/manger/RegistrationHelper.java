package ru.stqa.mantis.manger;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;

public class RegistrationHelper extends HelperBase {

    public RegistrationHelper(ApplicationManager manager) {
        super(manager);
    }

    public void completedRegistration(String realname, String password, String password_confirm, String url) {
        manager.driver().get(url);
        manager.driver().manage().window().setSize(new Dimension(1132, 1073));
        type(By.name("realname"), realname);
        type(By.name("password"), password);
        type(By.name("password_confirm"), password_confirm);
        click(By.xpath("//span[@class='bigger-110' and text()='Update User']"));
    }
}
