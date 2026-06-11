import java.time.LocalDateTime;

/**
 * Class that holds the information of smart cameras.
 */
public class SmartCamera extends SmartDevice {

	Double megabytesPerRecord;
	LocalDateTime onTime = LocalDateTime.of(1, 1, 1, 0, 0, 0); //time to be on
	double storage = 0; //megabytes used
	String[] input;

	/**
	 * Assigns the received input line to a variable in this class.
	 *
	 * @param input - stores the required input line
	 */
	public SmartCamera(String[] input) {
		this.input = input;
	}

	/**
	 * This Function decides whether to accept the device by checking the input format.
	 *
	 * @return - true if the device is given in a correct format, false otherwise
	 */
	public boolean camera() {
		try {
			if (!(input.length == 5 || input.length == 4)) { // check input length
				throw new Exception("ERROR: Erroneous command!");
			}

			megabytesPerRecord = Double.parseDouble(input[3]);

			if (input.length == 5) { // set status
				if (input[4].equals("On") || input[4].equals("Off")) {
					status = input[4];
					if (input[4].equals("On")) {
						onTime = Main.time;
					}
				} else {
					throw new Exception("ERROR: Erroneous command!");
				}
			}

			if (Main.names.contains(input[2])) { /// check name
				throw new Exception("ERROR: There is already a smart device with same name!");
			}

			if (Double.parseDouble(input[3]) <= 0) { // check sign of number
				throw new Exception("ERROR: Megabyte value must be a positive number!");
			}

			name = input[2];
			return true;

			/* prints required errors */
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
			WriteTxt.write("ERROR: Erroneous command!\n");
			return false;
		} catch (Exception e) {
			WriteTxt.write(e.getMessage() + "\n");
			return false;
		}
	}
}
