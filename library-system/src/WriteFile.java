import java.io.FileWriter;

public class WriteFile implements FileManager, Process {
	
	static void write(String message) {
		try {
			FileWriter myWriter = new FileWriter(Main.outputFileName, true);
			myWriter.write(message + "\n");
			myWriter.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	static void write() {
		try {
			FileWriter myWriter = new FileWriter(Main.outputFileName, false);
			myWriter.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}