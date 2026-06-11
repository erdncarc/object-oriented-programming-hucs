import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReadTxt implements WorkWithFile {

	/**
	 * Reads the contents of a text file and returns them as an array of strings.
	 *
	 * @param file - file name to be read
	 * @return - an array of strings containing the contents of the file
	 */
	public String[] readFile(String file) {
		try {
			int i = 0;
			Path path = Paths.get(file);
			int lenght = Files.readAllLines(path).size();
			String[] results = new String[lenght];
			for (String line : Files.readAllLines(path)) {
				results[i++] = line;
			}
			return results;
		} catch (Exception e) {
			WriteTxt.write("ERROR: File not found!");
			System.exit(0);
			return null;
		}
	}
}