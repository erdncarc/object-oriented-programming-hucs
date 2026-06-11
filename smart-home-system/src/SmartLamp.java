/**
 * Class that holds the information of smart lamps.
 */
public class SmartLamp extends SmartDevice {

	int kelvin = 4000;
	int brightness = 100;
	String[] input;

	/**
	 * Assigns the received input line to a variable in this class.
	 *
	 * @param input - stores the required input line
	 */
	public SmartLamp(String[] input) {
		this.input = input;
	}

	/**
	 * This Function decides whether to accept the device by checking the input format.
	 *
	 * @return - true if the device is given in a correct format, false otherwise
	 */
	public boolean lamp() {
		try {
			if (input.length > 6 || input.length < 3) { // check input length
				throw new Exception("ERROR: Erroneous command!");
			} else if (input.length == 6) {
				kelvin = Integer.parseInt(input[4]);
				brightness = Integer.parseInt(input[5]);
			}

			for (int i = 0; i < input.length; i++) {
				switch (i) {
					case 3: // set status
						if (input[3].equals("On") || input[3].equals("Off")) {
							status = input[3];
						} else {
							throw new Exception("ERROR: Erroneous command!");
						}

						if (Main.names.contains(input[2])) { // check name if input length > 3
							throw new Exception("ERROR: There is already a smart device with same name!");
						}
						break;

					case 4: // set kelvin
						if (Integer.parseInt(input[4]) > 6500 || Integer.parseInt(input[4]) < 2000) {
							throw new Exception("ERROR: Kelvin value must be in range of 2000K-6500K!");
						}
						break;

					case 5: // set brightness
						if (Integer.parseInt(input[5]) > 100 || Integer.parseInt(input[5]) < 0) {
							throw new Exception("ERROR: Brightness must be in range of 0%-100%!");
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