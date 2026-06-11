import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * This class sets the switch time given to the device with the given name.
 */
public class SetSwitch implements Process {

	String[] input;

	/**
	 * Assigns the received input line to a variable in this class.
	 *
	 * @param input - stores the required input line
	 */
	public SetSwitch(String[] input) {
		this.input = input;
	}

	/**
	 * This method sets the switch time of the device.
	 */
	public void setSwitch() {
		try {
			if (input.length != 3) { // check input length
				throw new Exception("ERROR: Erroneous command!");
			}

			if (!Main.names.contains(input[1])) { // check whether device exist
				throw new Exception("ERROR: There is not such a device!");
			}

			if (LocalDateTime.parse(input[2], InitialTime.formatter).isBefore(Main.time)) { // check for past date
				throw new Exception("ERROR: Switch time cannot be in the past!");
			}

			for (int i = 0; i < Main.smartPlugs.size(); i++) {
				if (Main.smartPlugs.get(i).name.equals(input[1])) {
					Main.smartPlugs.get(i).switchTime = LocalDateTime.parse(input[2], InitialTime.formatter); // set switch time
					Main.smartPlugs.get(i).temporarySwitchTime = LocalDateTime.parse(input[2], InitialTime.formatter); // set backup switch time
					Main.smartPlugs.get(i).situation = true; // verify assigned switch time

					if (LocalDateTime.parse(input[2], InitialTime.formatter).isEqual(Main.time)) { // if switch time and actual time are equal, take action immediately
						SwitchTimeManager switchTimeManager = new SwitchTimeManager(input);
						switchTimeManager.timeManager();
					}
				}
			}
			for (int i = 0; i < Main.smartCameras.size(); i++) {
				if (Main.smartCameras.get(i).name.equals(input[1])) {
					Main.smartCameras.get(i).switchTime = LocalDateTime.parse(input[2], InitialTime.formatter); // set switch time
					Main.smartCameras.get(i).temporarySwitchTime = LocalDateTime.parse(input[2], InitialTime.formatter); // set backup switch time
					Main.smartCameras.get(i).situation = true;// verify assigned switch time

					if (LocalDateTime.parse(input[2], InitialTime.formatter).isEqual(Main.time)) { // if switch time and actual time are equal, take action immediately
						SwitchTimeManager switchTimeManager = new SwitchTimeManager(input);
						switchTimeManager.timeManager();
					}
				}
			}
			for (int i = 0; i < Main.smartColorLambs.size(); i++) {
				if (Main.smartColorLambs.get(i).name.equals(input[1])) {
					Main.smartColorLambs.get(i).switchTime = LocalDateTime.parse(input[2], InitialTime.formatter); // set switch time
					Main.smartColorLambs.get(i).temporarySwitchTime = LocalDateTime.parse(input[2], InitialTime.formatter); // set backup switch time
					Main.smartColorLambs.get(i).situation = true;// verify assigned switch time
				}
			}
			for (int i = 0; i < Main.smartLambs.size(); i++) {
				if (Main.smartLambs.get(i).name.equals(input[1])) {
					Main.smartLambs.get(i).switchTime = LocalDateTime.parse(input[2], InitialTime.formatter); // set switch time
					Main.smartLambs.get(i).temporarySwitchTime = LocalDateTime.parse(input[2], InitialTime.formatter); // set backup switch time
					Main.smartLambs.get(i).situation = true;// verify assigned switch time
				}
			}

			Orientation.switchTime.add(LocalDateTime.parse(input[2], InitialTime.formatter)); // adds the new switch time to the arraylist of all switch times

			/* prints required errors */
		} catch (DateTimeParseException e) {
			WriteTxt.write("ERROR: Erroneous command!\n");
		} catch (Exception e) {
			WriteTxt.write(e.getMessage() + "\n");
		}
	}
}
