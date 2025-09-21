package ru.stqa.mantis.tests;

import org.junit.jupiter.api.BeforeEach;
import ru.stqa.mantis.manger.ApplicationManager;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class TestBase {
    protected static ApplicationManager app;

    @BeforeEach
    public void setUp() throws IOException {
        if (app == null) {
            app = new ApplicationManager();
        }
        var properties = new Properties();
        properties.load(new FileReader(System.getProperty("target", "local.properties")));
        app.init(System.getProperty("browser", "firefox"), properties);
    }
}
