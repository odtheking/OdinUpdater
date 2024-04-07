import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {

    public static void main(String[] args) {
        File oldJar = new File(args[0]);
        while (oldJar.exists()) {
            oldJar.delete();
        }

        File relaunchCommandFile = new File(args[1]);
        String relaunchCommand;
        try {
            relaunchCommand = String.join("", Files.readAllLines(relaunchCommandFile.toPath()));
            if (relaunchCommandFile.delete()) {
                Runtime.getRuntime().exec(relaunchCommand);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}