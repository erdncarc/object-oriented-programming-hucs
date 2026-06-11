import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * It is the class where the smart home system starts working.
 *
 * @author erdinç
 */
public class Main {

	/**
	 * These are the arraylists that are required almost everywhere in the code.
	 */
	public static ArrayList<SmartLamp> smartLambs = new ArrayList<>(); //stores smart lamp classes
	public static ArrayList<SmartColorLamp> smartColorLambs = new ArrayList<>(); //stores smart color lamp classes
	public static ArrayList<SmartCamera> smartCameras = new ArrayList<>(); //stores smart camera classes
	public static ArrayList<SmartPlug> smartPlugs = new ArrayList<>(); //stores smart plug classes
	public static ArrayList<String> names = new ArrayList<>(); // stores the names of devices
	public static LocalDateTime time = null; //contains the actual time
	public static String inputName;
	public static String outputName;


	/**
	 * Takes inputs from file.
	 * It transfers the inputs to the Orientation class.
	 *
	 * @param args - takes filenames given from user
	 */
	public static void main(String[] args) {
		inputName = args[0];
		outputName = args[1];
		ReadTxt readTxt = new ReadTxt();
		Orientation orientation = new Orientation(readTxt);
		orientation.orientation();
	}
}
