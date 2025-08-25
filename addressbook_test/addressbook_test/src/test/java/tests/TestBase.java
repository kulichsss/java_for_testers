package tests;

import manager.ApplicationManager;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.nio.file.Paths;
import java.util.Random;

public class TestBase {

    protected static ApplicationManager app;

    @BeforeEach
    public void setUp() {
        if (app == null) {
            app = new ApplicationManager();
        }
        app.init(System.getProperty("browser", "firefox"));
    }

    public static String randomFile(String dir) {
        var randomFile = new File(dir).list();
        var rnd = new Random();
        var index = rnd.nextInt(randomFile != null ? randomFile.length : null);
        return Paths.get(dir, randomFile[index]).toString();
    }

}
