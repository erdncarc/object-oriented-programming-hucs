import java.io.FileWriter;
import java.io.IOException;

public class WriteTxt implements WorkWithFile {

	/**
	 * Writes a message to a text file.
	 *
	 * @param message - message to be written
	 */
	public static void write(String message) {
		try {
			FileWriter myWriter = new FileWriter(Main.outputName, true);
			myWriter.write(message);
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Clears the contents of a text file.
	 */
	public static void write() {
		try {
			FileWriter myWriter = new FileWriter(Main.outputName, false);
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}