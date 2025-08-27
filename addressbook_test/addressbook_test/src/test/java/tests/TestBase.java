package tests;

import manager.ApplicationManager;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Random;

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

    public static String randomFile(String dir) {
        var randomFile = new File(dir).list();
        var rnd = new Random();
        var index = rnd.nextInt(randomFile != null ? randomFile.length : null);
        return Paths.get(dir, randomFile[index]).toString();
    }

}
