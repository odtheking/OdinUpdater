import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        File oldVersion = new File(args[0]);

        if (oldVersion.delete()) {
            String relaunchCommand = args[1];
            try {
                Runtime.getRuntime().exec(relaunchCommand);
            } catch (IOException ignored) {

            }
        }
    }

}