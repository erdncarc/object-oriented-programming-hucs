import java.time.LocalDateTime;

/**
 * Class that holds the information of smart plugs.
 */
public class SmartPlug extends SmartDevice {

	int volt = 220;
	double ampere = 0;
	Boolean plugIn = false;
	LocalDateTime onTime = LocalDateTime.of(1, 1, 1, 0, 0, 0); // time to be on
	LocalDateTime inTime = LocalDateTime.of(1, 1, 1, 0, 0, 0); // time to be plug in
	double energy = 0; // energy expended
	String[] input;

	/**
	 * Assigns the received input line to a variable in this class.
	 *
	 * @param input - stores the required input line
	 */
	public SmartPlug(String[] input) {
		this.input = input;
	}

	/**
	 * This Function decides whether to accept the device by checking the input format.
	 *
	 * @return - true if the device is given in a correct format, false otherwise
	 */
	public boolean plug() {
		try {
			if (input.length > 5 || input.length < 3) { // check input length
				throw new Exception("ERROR: Erroneous command!");
			} else if (input.length == 5) { // check input[4] type - int or str
				ampere = Double.parseDouble(input[4]);
				plugIn = true;
			}

			for (int i = 0; i < input.length; i++) {
				switch (i) {
					case 3: // set status
						if (input[3].equals("On") || input[3].equals("Off")) {
							status = input[3];
							if (input[3].equals("On")) {
								onTime = Main.time;
							}
						} else {
							throw new Exception("ERROR: Erroneous command!");
						}

						if (Main.names.contains(input[2])) { // check name if input length > 3
							throw new Exception("ERROR: There is already a smart device with same name!");
						}
						break;

					case 4: // set ampere
						if (Double.parseDouble(input[4]) <= 0) {
							throw new Exception("ERROR: Ampere value must be a positive number!");
						}
						break;
				}
			}

			if (Main.names.contains(input[2])) { // check name if input length == 3
				throw new Exception("ERROR: There is already a smart device with same name!");
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
