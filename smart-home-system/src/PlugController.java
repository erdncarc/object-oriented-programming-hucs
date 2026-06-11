import java.time.LocalDateTime;

/**
 * It is the class that examines the plug-in and plug-out status of smart plugs.
 */
public class PlugController implements Process {

	String[] input;
	boolean control1 = true; // check device type
	boolean control2 = false;

	/**
	 * Assigns the received input line to a variable in this class.
	 *
	 * @param input - stores the required input line
	 */
	public PlugController(String[] input) {
		this.input = input;
	}

	/**
	 * This method ensures that the smart plug is plugged in if the command is true.
	 */
	public void controlIn() {
		try {
			if (input.length > 3) { // check input length
				throw new Exception("ERROR: Erroneous command!1");
			}

			if (!Main.names.contains(input[1])) { // check whether device exist
				throw new Exception("ERROR: There is not such a device!");
			}

			for (int i = 0; i < Main.smartPlugs.size(); i++) {
				if (Main.smartPlugs.get(i).name.equals(input[1])) {
					if (input.length == 3) { // check ampere
						if (Double.parseDouble(input[2]) <= 0) {
							throw new Exception("ERROR: Ampere value must be a positive number!");
						} else {
							control2 = true;
						}
					} else if (Main.smartPlugs.get(i).ampere == 0) {
						throw new Exception("ERROR: Erroneous command!");
					}

					if (Main.smartPlugs.get(i).plugIn) { // check plug in
						throw new Exception("ERROR: There is already an item plugged in to that plug!");
					} else { // set plug-in
						Main.smartPlugs.get(i).plugIn = true;
						Main.smartPlugs.get(i).inTime = Main.time;

						if (control2) { // set ampere
							Main.smartPlugs.get(i).ampere = Double.parseDouble(input[2]);
						}
					}
					control1 = false;
				}
			}

			if (control1) {
				throw new Exception("ERROR: This device is not a smart plug!");
			}

			/* prints required errors */
		} catch (ArrayIndexOutOfBoundsException e) {
			WriteTxt.write("ERROR: Erroneous command!\n");
		} catch (Exception e) {
			WriteTxt.write(e.getMessage() + "\n");
		}
	}

	/**
	 * This method ensures that the smart plug is plugged out if the command is true.
	 */
	public void controlOut() {
		try {
			if (input.length > 2) { // check input length
				throw new Exception("ERROR: Erroneous command!");
			}

			if (!Main.names.contains(input[1])) { // check whether device exist
				throw new Exception("ERROR: There is not such a device!");
			}

			for (int i = 0; i < Main.smartPlugs.size(); i++) {
				if (Main.smartPlugs.get(i).name.equals(input[1])) {
					if (Main.smartPlugs.get(i).plugIn.equals(false)) { // check plug-out
						throw new Exception("ERROR: This plug has no item to plug out from that plug!");
					} else {
						Main.smartPlugs.get(i).plugIn = false; // set plug-out
						Calculate calculate = new Calculate(input[1]);
						calculate.calculateEnergy(); // calculation is possible because it is a plug-out
						Main.smartPlugs.get(i).inTime = LocalDateTime.of(1, 1, 1, 0, 0, 0);
					}
					control1 = false;
				}
			}

			if (control1) {
				throw new Exception("ERROR: This device is not a smart plug!");
			}

			/* prints required errors */
		} catch (ArrayIndexOutOfBoundsException e) {
			WriteTxt.write("ERROR: Erroneous command!\n");
		} catch (Exception e) {
			WriteTxt.write(e.getMessage() + "\n");
		}
	}
}
