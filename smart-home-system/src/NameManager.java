/**
 * This is a class that is responsible for changing the name of a smart device.
 */
public class NameManager implements Process {

	String[] input;

	/**
	 * Assigns the received input line to a variable in this class.
	 *
	 * @param input - stores the required input line
	 */
	public NameManager(String[] input) {
		this.input = input;
	}

	/**
	 * This method is responsible for changing the name of a smart device.
	 */
	public void changeName() {
		try {
			if (input.length != 3) { // check input length
				throw new Exception("ERROR: Erroneous command!");
			}

			if (input[1].equals(input[2])) { // check names are same or not
				throw new Exception("ERROR: Both of the names are the same, nothing changed!");
			}

			if (!Main.names.contains(input[1])) { // check whether device exist
				throw new Exception("ERROR: There is not such a device!");
			}

			if (Main.names.contains(input[2])) { // check name
				throw new Exception("ERROR: There is already a smart device with same name!");
			}

			for (int i = 0; i < Main.smartPlugs.size(); i++) { // set name
				if (Main.smartPlugs.get(i).name.equals(input[1])) {
					Main.smartPlugs.get(i).name = input[2];
				}
			}
			for (int i = 0; i < Main.smartCameras.size(); i++) { // set name
				if (Main.smartCameras.get(i).name.equals(input[1])) {
					Main.smartCameras.get(i).name = input[2];
				}
			}
			for (int i = 0; i < Main.smartColorLambs.size(); i++) { // set name
				if (Main.smartColorLambs.get(i).name.equals(input[1])) {
					Main.smartColorLambs.get(i).name = input[2];
				}
			}
			for (int i = 0; i < Main.smartLambs.size(); i++) { // set name
				if (Main.smartLambs.get(i).name.equals(input[1])) {
					Main.smartLambs.get(i).name = input[2];
				}
			}

			Main.names.set(Main.names.indexOf(input[1]), input[2]);

			/* prints required errors */
		} catch (Exception e) {
			WriteTxt.write(e.getMessage() + "\n");
		}
	}
}
