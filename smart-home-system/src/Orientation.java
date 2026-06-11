import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * This is the class where all redirects are made.
 */
public class Orientation implements Process{

	public static ArrayList<LocalDateTime> switchTime = new ArrayList<>(); //a list where switch times are stored
	String[] file;
	boolean lastCommand; //controls whether the last command is ZReport

	/**
	 * Assigns the input to a variable in this class.
	 *
	 * @param readTxt - gets the required input for routing
	 */
	public Orientation(ReadTxt readTxt) {
		this.file = readTxt.readFile(Main.inputName);
	}

	/**
	 * This section is the function where all assignments are made.
	 */
	public void orientation() {
		WriteTxt.write(); //if there is an output file, it deletes it to recreate it
		for (String line : file) {

			if (line.equals("")) { // skips blank lines
				continue;
			}

			WriteTxt.write("COMMAND: " + line + "\n");
			String[] input = line.split("\t");

			/* Part from this line to try loop, first command checks correctness. */
			if (input[0].equals("SetInitialTime")) {
				if (Main.time == null) {
					try {
						if (input.length != 2) {
							throw new Exception("ERROR: First command must be set initial time! Program is going to terminate!");
						}
						InitialTime initialTime = new InitialTime(input);
						initialTime.dateTime(); //The entered time expression is sent to the InitialTime class for transactions.
					} catch (Exception e) {
						WriteTxt.write(e.getMessage() + "\n");
						break;
					}
				} else {
					WriteTxt.write("ERROR: Erroneous command!\n");
				}
				continue;
			}
			if (Main.time == null) {
				WriteTxt.write("ERROR: First command must be set initial time! Program is going to terminate!\n");
				break;
			}
			lastCommand = true;

			/* Invokes the required action based on the input. */
			try {
				switch (input[0]) {
					case "Add":
						switch (input[1]) {
							case "SmartLamp":
								SmartLamp smartLamp = new SmartLamp(input);
								if (smartLamp.lamp()) {
									Main.smartLambs.add(smartLamp);
									Main.names.add(input[2]);
								}
								break;

							case "SmartCamera":
								SmartCamera smartCamera = new SmartCamera(input);
								if (smartCamera.camera()) {
									Main.smartCameras.add(smartCamera);
									Main.names.add(input[2]);
								}
								break;

							case "SmartPlug":
								SmartPlug smartPlug = new SmartPlug(input);
								if (smartPlug.plug()) {
									Main.smartPlugs.add(smartPlug);
									Main.names.add(input[2]);
								}
								break;

							case "SmartColorLamp":
								SmartColorLamp smartColorLamp = new SmartColorLamp(input);
								if (smartColorLamp.colorLamp()) {
									Main.smartColorLambs.add(smartColorLamp);
									Main.names.add(input[2]);
								}
								break;
							default:
								WriteTxt.write("ERROR: Erroneous command!\n");
						}
						break;

					case "PlugIn":
						PlugController plugController = new PlugController(input);
						plugController.controlIn();
						break;

					case "PlugOut":
						PlugController plugController2 = new PlugController(input);
						plugController2.controlOut();
						break;

					case "SetTime":

					case "SkipMinutes":
						InitialTime initialTime = new InitialTime(input);
						initialTime.dateTime();
						break;

					case "Remove":

					case "Switch":
						SwitchRemove findDeviceType = new SwitchRemove(input);
						findDeviceType.process();
						break;

					case "ChangeName":
						NameManager nameManager = new NameManager(input);
						nameManager.changeName();
						break;

					case "SetSwitchTime":
						SetSwitch setSwitch = new SetSwitch(input);
						setSwitch.setSwitch();
						break;

					case "SetColorCode":

					case "SetBrightness":

					case "SetKelvin":
						LambManager lambManager = new LambManager(input);
						lambManager.setCBK();
						break;

					case "SetWhite":

					case "SetColor":
						LambManager lambManager2 = new LambManager(input);
						lambManager2.setWC();
						break;

					case "Nop":
						SwitchTimeManager switchTimeManager = new SwitchTimeManager(input);
						switchTimeManager.timeManager();
						break;

					case "ZReport":
						ReportManager reportManager = new ReportManager();
						reportManager.zReport();
						lastCommand = false;
						break;

					default:
						WriteTxt.write("ERROR: Erroneous command!\n"); //if a process other than above is called

				}
			} catch (ArrayIndexOutOfBoundsException e) {
				WriteTxt.write("ERROR: Erroneous command!\n"); //
			}
		}

		/* creates a ZReport if the last command is not ZReport*/
		if (lastCommand) {
			WriteTxt.write("ZReport:\n");
			ReportManager reportManager = new ReportManager();
			reportManager.zReport();
		}
	}
}


