/**
 * This is the class responsible for the settings of the smart lamp and smart color lamp.
 */
public class LambManager implements Process {

	String[] input;

	/**
	 * Assigns the received input line to a variable in this class.
	 *
	 * @param input - stores the required input line
	 */
	public LambManager(String[] input) {
		this.input = input;
	}

	/**
	 * This method is used to set the Kelvin value, brightness, or color code of a smart lamp or smart color lamp.
	 */
	public void setCBK() {
		try {
			if (input.length != 3) { // check input length
				throw new Exception("ERROR: Erroneous command!");
			}

			switch (input[0]) {
				case "SetKelvin":
					boolean test1 = true; // check device type
					int kelvin = Integer.parseInt(input[2]);

					if (!Main.names.contains(input[1])) { // check whether device exist
						throw new Exception("ERROR: There is not such a device!");
					}

					for (int i = 0; i < Main.smartColorLambs.size(); i++) {
						if (Main.smartColorLambs.get(i).name.equals(input[1])) {
							test1 = false;
							break;
						}
					}
					for (int i = 0; i < Main.smartLambs.size(); i++) {
						if (Main.smartLambs.get(i).name.equals(input[1])) {
							test1 = false;
							break;
						}
					}
					if (test1) {
						throw new Exception("ERROR: This device is not a smart lamp!");
					}

					if (Integer.parseInt(input[2]) > 6500 || Integer.parseInt(input[2]) < 2000) { //check kelvin
						throw new Exception("ERROR: Kelvin value must be in range of 2000K-6500K!");
					}

					for (int i = 0; i < Main.smartColorLambs.size(); i++) { // set kelvin
						if (Main.smartColorLambs.get(i).name.equals(input[1])) {
							Main.smartColorLambs.get(i).kelvin = kelvin;
							Main.smartColorLambs.get(i).colorMode = false;
						}
					}
					for (int i = 0; i < Main.smartLambs.size(); i++) { // set kelvin
						if (Main.smartLambs.get(i).name.equals(input[1])) {
							Main.smartLambs.get(i).kelvin = Integer.parseInt(input[2]);
						}
					}
					break;

				case "SetBrightness":
					boolean test2 = true; // check device type
					for (int i = 0; i < Main.smartColorLambs.size(); i++) {
						if (Main.smartColorLambs.get(i).name.equals(input[1])) {
							test2 = false;
							break;
						}
					}
					for (int i = 0; i < Main.smartLambs.size(); i++) {
						if (Main.smartLambs.get(i).name.equals(input[1])) {
							test2 = false;
							break;
						}
					}
					if (test2) {
						throw new Exception("ERROR: This device is not a smart lamp!");
					}

					if (Integer.parseInt(input[2]) > 100 || Integer.parseInt(input[2]) <= 0) { // check brightness
						throw new Exception("ERROR: Brightness must be in range of 0%-100%!");
					}

					for (int i = 0; i < Main.smartColorLambs.size(); i++) { // set brightness
						if (Main.smartColorLambs.get(i).name.equals(input[1])) {
							Main.smartColorLambs.get(i).brightness = Integer.parseInt(input[2]);
							break;
						}
					}
					for (int i = 0; i < Main.smartLambs.size(); i++) { // set brightness
						if (Main.smartLambs.get(i).name.equals(input[1])) {
							Main.smartLambs.get(i).brightness = Integer.parseInt(input[2]);
							break;
						}
					}
					break;

				case "SetColorCode":
					boolean test3 = true; // check device type
					for (int i = 0; i < Main.smartColorLambs.size(); i++) {
						if (Main.smartColorLambs.get(i).name.equals(input[1])) {
							test3 = false;
							break;
						}
					}
					if (test3) {
						throw new Exception("ERROR: This device is not a smart color lamp!");
					}

					if (Integer.parseInt(input[2].replace("0x", ""), 16) < 0x000000 || Integer.parseInt(input[2].replace("0x", ""), 16) > 0xFFFFFF) { // check color code
						throw new Exception("ERROR: Color code value must be in range of 0x0-0xFFFFFF!");
					}
					for (int i = 0; i < Main.smartColorLambs.size(); i++) { // set color code
						if (Main.smartColorLambs.get(i).name.equals(input[1])) {
							Main.smartColorLambs.get(i).color = Integer.parseInt(input[2].replace("0x", ""), 16);
							Main.smartColorLambs.get(i).colorMode = true;
						}
					}
					break;
			}

			/* prints required errors */
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
			WriteTxt.write("ERROR: Erroneous command!\n");
		} catch (Exception e) {
			WriteTxt.write(e.getMessage() + "\n");
		}
	}

	/**
	 * This method is used to set the Kelvin value, brightness, or color code of a smart lamp or smart color lamp.
	 */
	public void setWC() {
		try {
			if (input.length != 4) { // check input length
				throw new Exception("ERROR: Erroneous command!");
			}

			if (!Main.names.contains(input[1])) { // check whether device exist
				throw new Exception("ERROR: There is not such a device!");
			}

			switch (input[0]) {
				case "SetWhite":
					boolean test4 = true; // check device type
					for (int i = 0; i < Main.smartColorLambs.size(); i++) {
						if (Main.smartColorLambs.get(i).name.equals(input[1])) {
							test4 = false;
							break;
						}
					}
					for (int i = 0; i < Main.smartLambs.size(); i++) {
						if (Main.smartLambs.get(i).name.equals(input[1])) {
							test4 = false;
							break;
						}
					}
					if (test4) {
						throw new Exception("ERROR: This device is not a smart lamp!");
					}

					if (Integer.parseInt(input[2]) > 6500 || Integer.parseInt(input[2]) < 2000) { // check kelvin
						throw new Exception("ERROR: Kelvin value must be in range of 2000K-6500K!");
					}
					if (Integer.parseInt(input[3]) > 100 || Integer.parseInt(input[3]) < 0) { // check brightness
						throw new Exception("ERROR: Brightness must be in range of 0%-100%!");
					}

					for (int i = 0; i < Main.smartColorLambs.size(); i++) { // set kelvin and brightness
						if (Main.smartColorLambs.get(i).name.equals(input[1])) {
							Main.smartColorLambs.get(i).kelvin = Integer.parseInt(input[2]);
							Main.smartColorLambs.get(i).brightness = Integer.parseInt(input[3]);
							Main.smartColorLambs.get(i).colorMode = false;
							break;
						}
					}
					for (int i = 0; i < Main.smartLambs.size(); i++) { // set kelvin and brightness
						if (Main.smartLambs.get(i).name.equals(input[1])) {
							Main.smartLambs.get(i).kelvin = Integer.parseInt(input[2]);
							Main.smartLambs.get(i).brightness = Integer.parseInt(input[3]);
							break;
						}
					}
					break;

				case "SetColor":
					boolean test5 = true; // check device type
					for (int i = 0; i < Main.smartColorLambs.size(); i++) {
						if (Main.smartColorLambs.get(i).name.equals(input[1])) {
							test5 = false;
							break;
						}
					}
					if (test5) {
						throw new Exception("ERROR: This device is not a smart color lamp!");
					}

					boolean test6 = true; // checking for kelvin or color
					if (input[2].startsWith("0x")) { // check color code
						if (Integer.parseInt(input[2].replace("0x", ""), 16) < 0x000000 || Integer.parseInt(input[2].replace("0x", ""), 16) > 0xFFFFFF) {
							throw new Exception("ERROR: Color code value must be in range of 0x0-0xFFFFFF!");
						}
					} else { // check kelvin
						test6 = false;
						if (Integer.parseInt(input[2]) > 6500 || Integer.parseInt(input[2]) < 2000) {
							throw new Exception("ERROR: Kelvin value must be in range of 2000K-6500K!");
						}
					}

					if (Integer.parseInt(input[3]) > 100 || Integer.parseInt(input[3]) < 0) { // check brightness
						throw new Exception("ERROR: Brightness must be in range of 0%-100%!");
					}

					for (int i = 0; i < Main.smartColorLambs.size(); i++) { // set brightness and kelvin or color code
						if (Main.smartColorLambs.get(i).name.equals(input[1])) {
							Main.smartColorLambs.get(i).brightness = Integer.parseInt(input[3]);
							if (test6) {
								Main.smartColorLambs.get(i).color = Integer.parseInt(input[2].replace("0x", ""), 16);
								Main.smartColorLambs.get(i).colorMode = true;
							} else {
								Main.smartColorLambs.get(i).kelvin = Integer.parseInt(input[2]);
								Main.smartColorLambs.get(i).colorMode = false;
							}
						}
					}
					break;
			}

			/* prints required errors */
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
			WriteTxt.write("ERROR: Erroneous command!\n");
		} catch (Exception e) {
			WriteTxt.write(e.getMessage() + "\n");
		}
	}
}
