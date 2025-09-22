package ru.stqa.mantis.manger;

import org.openqa.selenium.os.ExternalProcess;
import java.time.Duration;

public class JamesCliHelper extends HelperBase{
    public JamesCliHelper(ApplicationManager manager) {
        super(manager);
    }


    public void addUser(String username, String password) {
        ExternalProcess cmd = ExternalProcess.builder()
                .command("java", "-cp", "\"james-server-jpa-app.lib/*\"", "org.apache.james.cli.ServerCmd", "AddUser", username, password)
                .directory(manager.property("james.workDir"))
                .copyOutputTo(System.err)
                .start();
        try {
            boolean exitCode = cmd.waitFor(Duration.ofMinutes(2));
            if (exitCode) {
                System.out.println(cmd.getOutput());
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}
