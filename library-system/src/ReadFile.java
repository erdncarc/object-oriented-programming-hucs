import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ReadFile implements FileManager, Process {
	
	ReadFile(Orientation orientation) {
		try {
			List<String> lines = Files.readAllLines(Paths.get(Main.inputFileName));
			for (String line : lines) {
				orientation.process(line.split("\t"));
			}
		} catch (Exception e) {
			WriteFile.write("File not found!");
		}
	}
}