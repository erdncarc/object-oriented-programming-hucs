import java.time.LocalDateTime;
import java.util.Objects;

/**
 * This class does the tasks of switch and remove command.
 */
public class SwitchRemove implements Process {

	String[] input;

	/**
	 * Assigns the received input line to a variable in this class.
	 *
	 * @param input - stores the required input line
	 */
	public SwitchRemove(String[] input) {
		this.input = input;
	}

	/**
	 *
	 */
	public void process() {
		try {
			if ((input.length > 2 && input[0].equals("Remove")) || (input.length > 3 && input[0].equals("Switch"))) { // check input length
				throw new Exception("ERROR: Erroneous command!");
			}

			if (!Main.names.contains(input[1])) { // check whether device exist
				throw new Exception("ERROR: There is not such a device!");
			}

			if (input[0].equals("Remove")) { // remove current name from arraylist of all names
				Main.names.remove(input[1]);
				WriteTxt.write("SUCCESS: Information about removed smart device is as follows:\n");
			}

			for (int i = 0; i < Main.smartPlugs.size(); i++) {
				if (Main.smartPlugs.get(i).name.equals(input[1])) {
					if (input[0].equals("Remove")) {
						Calculate calculate = new Calculate(input[1]);
						calculate.calculateEnergy(); // all calculations must be made in the removal process

						/* the letter written for information on the removal process */
						Main.smartPlugs.get(i).status = "Off";
						String time = Objects.equals(Main.smartPlugs.get(i).switchTime, LocalDateTime.of(1, 1, 1, 0, 0, 0)) ? "null" : InitialTime.formatter.format(Main.smartPlugs.get(i).switchTime);
						WriteTxt.write("Smart Plug " + input[1] + " is o" + Main.smartPlugs.get(i).status.substring(1) +
								" and consumed " + String.format("%.2f", Main.smartPlugs.get(i).energy).replace(".", ",") +
								"W so far (excluding current device), and its time to switch its status is " +
								time + ".\n");

						Main.smartPlugs.remove(i); // deleting the removed device from the lists
						break;
					}

					if (input[0].equals("Switch")) {
						if (!Main.smartPlugs.get(i).status.equals(input[2])) {
							if (Main.smartPlugs.get(i).status.equals("On") && Main.smartPlugs.get(i).plugIn) {
								Calculate calculate = new Calculate(input[1]);
								calculate.calculateEnergy(); // calculation should be made for devices that switch from on to off and are in plug-in status at this time
								Main.smartPlugs.get(i).onTime = LocalDateTime.of(1, 1, 1, 0, 0, 0); // onTime is reset when the device is turned off

							} else if (Main.smartPlugs.get(i).status.equals("Off")) { // if the device goes on state, set its onTime
								Main.smartPlugs.get(i).onTime = Main.time;
							}

							Main.smartPlugs.get(i).status = input[2]; // set the new status of device

							if (!Objects.equals(Main.smartPlugs.get(i).switchTime, LocalDateTime.of(1, 1, 1, 0, 0, 0))) { // if the device already has a switch time, delete it
								Orientation.switchTime.remove(Main.smartPlugs.get(i).switchTime);
								Main.smartPlugs.get(i).switchTime = LocalDateTime.of(1, 1, 1, 0, 0, 0);
							}

						} else {
							throw new Exception("ERROR: This device is already switched o" + input[2].substring(1) + "!");
						}
					}
				}
			}

			for (int i = 0; i < Main.smartCameras.size(); i++) {
				if (Main.smartCameras.get(i).name.equals(input[1])) {
					if (input[0].equals("Remove")) {
						Calculate calculate = new Calculate(input[1]);
						calculate.calculateStorage(); // all calculations must be made in the removal process

						/* the letter written for information on the removal process */
						Main.smartCameras.get(i).status = "Off";
						String time = Objects.equals(Main.smartCameras.get(i).switchTime, LocalDateTime.of(1, 1, 1, 0, 0, 0)) ? "null" : InitialTime.formatter.format(Main.smartCameras.get(i).switchTime);
						WriteTxt.write("Smart Camera " + input[1] + " is o" + Main.smartCameras.get(i).status.substring(1) +
								" and used " + String.format("%.2f", Main.smartCameras.get(i).storage).replace(".", ",") +
								" MB of storage so far (excluding current status), and its time to switch its status is " +
								time + ".\n");

						Main.smartCameras.remove(i); // deleting the removed device from the lists
						break;
					}

					if (input[0].equals("Switch")) {
						if (!Main.smartCameras.get(i).status.equals(input[2])) {
							if (Main.smartCameras.get(i).status.equals("On")) {
								Calculate calculate = new Calculate(input[1]);
								calculate.calculateStorage(); // calculation should be made for devices that switch from on to off and are in plug-in status at this time
								Main.smartCameras.get(i).onTime = LocalDateTime.of(1, 1, 1, 0, 0, 0); // onTime is reset when the device is turned off

							} else if (Main.smartCameras.get(i).status.equals("Off")) { // if the device goes on state, set its onTime
								Main.smartCameras.get(i).onTime = Main.time;
							}

							Main.smartCameras.get(i).status = input[2]; // set the new status of device

							if (!Objects.equals(Main.smartCameras.get(i).switchTime, LocalDateTime.of(1, 1, 1, 0, 0, 0))) { // if the device already has a switch time, delete it
								Orientation.switchTime.remove(Main.smartCameras.get(i).switchTime);
								Main.smartCameras.get(i).switchTime = LocalDateTime.of(1, 1, 1, 0, 0, 0);
							}

						} else {
							throw new Exception("ERROR: This device is already switched o" + input[2].substring(1) + "!");
						}
					}
				}
			}

			for (int i = 0; i < Main.smartColorLambs.size(); i++) {
				if (Main.smartColorLambs.get(i).name.equals(input[1])) {
					if (input[0].equals("Remove")) {

						/* the letter written for information on the removal process */
						Main.smartColorLambs.get(i).status = "Off";
						String type = Main.smartColorLambs.get(i).colorMode ? "0x" + String.format("%06X", Main.smartColorLambs.get(i).color) : Main.smartColorLambs.get(i).kelvin + "K";
						String time = Objects.equals(Main.smartColorLambs.get(i).switchTime, LocalDateTime.of(1, 1, 1, 0, 0, 0)) ? "null" : InitialTime.formatter.format(Main.smartColorLambs.get(i).switchTime);
						WriteTxt.write("Smart Color Lamp " + input[1] + " is o" + Main.smartColorLambs.get(i).status.substring(1) + " and its color value is " +
								type + " with " + Main.smartColorLambs.get(i).brightness + "% brightness, and its time to switch its status is " +
								time + ".\n");

						Main.smartColorLambs.remove(i); // deleting the removed device from the lists
						break;
					}

					if (input[0].equals("Switch")) {
						if (!Main.smartColorLambs.get(i).status.equals(input[2])) {
							Main.smartColorLambs.get(i).status = input[2]; // set the new status of device

							if (!Objects.equals(Main.smartColorLambs.get(i).switchTime, LocalDateTime.of(1, 1, 1, 0, 0, 0))) { // if the device already has a switch time, delete it
								Orientation.switchTime.remove(Main.smartColorLambs.get(i).switchTime);
								Main.smartColorLambs.get(i).switchTime = LocalDateTime.of(1, 1, 1, 0, 0, 0);
							}

						} else {
							throw new Exception("ERROR: This device is already switched o" + input[2].substring(1) + "!");
						}
					}
				}
			}

			for (int i = 0; i < Main.smartLambs.size(); i++) {
				if (Main.smartLambs.get(i).name.equals(input[1])) {
					if (input[0].equals("Remove")) {

						/* the letter written for information on the removal process */
						Main.smartLambs.get(i).status = "Off";
						String time = Objects.equals(Main.smartLambs.get(i).switchTime, LocalDateTime.of(1, 1, 1, 0, 0, 0)) ? "null" : InitialTime.formatter.format(Main.smartLambs.get(i).switchTime);
						WriteTxt.write("Smart Lamp " + input[1] + " is o" + Main.smartLambs.get(i).status.substring(1) + " and its kelvin value is " +
								Main.smartLambs.get(i).kelvin + "K with " + Main.smartLambs.get(i).brightness + "% brightness, and its time to switch its status is " +
								time + ".\n");

						Main.smartLambs.remove(i); // deleting the removed device from the lists
						break;
					}

					if (input[0].equals("Switch")) {
						if (!Main.smartLambs.get(i).status.equals(input[2])) {
							Main.smartLambs.get(i).status = input[2]; // set the new status of device

							if (!Objects.equals(Main.smartLambs.get(i).switchTime, LocalDateTime.of(1, 1, 1, 0, 0, 0))) { // if the device already has a switch time, delete it
								Orientation.switchTime.remove(Main.smartLambs.get(i).switchTime);
								Main.smartLambs.get(i).switchTime = LocalDateTime.of(1, 1, 1, 0, 0, 0);
							}

						} else {
							throw new Exception("ERROR: This device is already switched o" + input[2].substring(1) + "!");
						}
					}
				}
			}

			/* prints required errors */
		} catch (ArrayIndexOutOfBoundsException e) {
			WriteTxt.write("ERROR: Erroneous command!\n");
		} catch (Exception e) {
			WriteTxt.write(e.getMessage() + "\n");
		}
	}
}
