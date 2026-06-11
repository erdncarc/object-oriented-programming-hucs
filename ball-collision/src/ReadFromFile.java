import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadFromFile {

    public static String[] readFile(String file) {
        try {
            int i = 0;
            int lenght = Files.readAllLines(Paths.get(file)).size();
            String[] results = new String[lenght];
            for (String line : Files.readAllLines(Paths.get(file))) {
                results[i++] = line;
            }
            return results;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}