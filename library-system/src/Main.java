public class Main {
	
	static String inputFileName;
	static String outputFileName;
	
	public static void main(String[] args) {
		inputFileName = args[0];
		outputFileName = args[1];
		WriteFile.write();
		new ReadFile(new Orientation());
	}
}