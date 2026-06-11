import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Most of the time-related details take place in this class.
 */
public class InitialTime implements Process {

	public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"); //required to write the time in the correct format
	LocalDateTime temporaryTime; //necessary to make a comparison between actual time and given time
	String[] input;

	/**
	 * Assigns the received input line to a variable in this class.
	 *
	 * @param input - stores the required input line
	 */
	public InitialTime(String[] input) {
		this.input = input;
	}

	/**
	 * 3 commands related to time reach this function and time is changed.
	 */
	public void dateTime() {
		try {
			switch (input[0]) {
				case "SetInitialTime": // sets the time as given at the beginning of the code
					Main.time = LocalDateTime.parse(input[1], formatter);
					WriteTxt.write("SUCCESS: Time has been set to " + formatter.format(Main.time) + "!\n");
					break;

				case "SetTime": // changes the actual time
					try {
						if (input.length != 2) {
							throw new Exception("ERROR: Erroneous command!");
						}
						this.temporaryTime = LocalDateTime.parse(input[1], formatter);
						if (temporaryTime.isBefore(Main.time)) {
							throw new Exception("ERROR: Time cannot be reversed!");
						} else if (temporaryTime.equals(Main.time)) {
							throw new Exception("ERROR: There is nothing to change!");
						} else {
							Main.time = temporaryTime;
							SwitchTimeManager switchTimeManager = new SwitchTimeManager(input);
							switchTimeManager.timeManager(); //makes any changes that may happen because time has changed
						}
					} catch (DateTimeParseException e) {
						WriteTxt.write("ERROR: Time format is not correct!\n");
					}
					break;

				case "SkipMinutes": // skips as many minutes as specified
					if (input.length != 2) {
						throw new Exception("ERROR: Erroneous command!");
					}
					if (Integer.parseInt(input[1]) < 0) {
						throw new Exception("ERROR: Time cannot be reversed!");
					} else if (Integer.parseInt(input[1]) == 0) {
						throw new Exception("ERROR: There is nothing to skip!");
					}
					Main.time = Main.time.plusMinutes(Integer.parseInt(input[1]));
					SwitchTimeManager switchTimeManager = new SwitchTimeManager(input);
					switchTimeManager.timeManager();
					break;
			}

			/* prints required errors */
		} catch (NumberFormatException e) {
			WriteTxt.write("ERROR: Erroneous command!\n");
		} catch (DateTimeParseException e) {
			WriteTxt.write("ERROR: Format of the initial date is wrong! Program is going to terminate!\n");
			System.exit(0);
		} catch (Exception e) {
			WriteTxt.write(e.getMessage() + "\n");
		}
	}
}
