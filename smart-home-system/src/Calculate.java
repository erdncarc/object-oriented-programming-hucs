import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * It is the class in which the storage calculation for the camera and the energy calculation for the plug are made.
 */
public class Calculate implements Process {

	String name;

	/**
	 * Assigns the received name to a variable in this class.
	 *
	 * @param name - stores the required name
	 */
	public Calculate(String name) {
		this.name = name;
	}

	/**
	 * This function calculates how many megabytes the camera stores.
	 */
	public void calculateStorage() {
		try {
			for (int i = 0; i < Main.smartCameras.size(); i++) {
				if (Main.smartCameras.get(i).name.equals(name)) {

					/* Calculates the device uptime and multiplies it by the megabytes consumed per minute by the camera. */
					if (!Objects.equals(Main.smartCameras.get(i).onTime, LocalDateTime.of(1, 1, 1, 0, 0, 0))) {
						long passingTime = Duration.between(Main.smartCameras.get(i).onTime, Main.time).toMinutes();
						Main.smartCameras.get(i).storage += Main.smartCameras.get(i).megabytesPerRecord * passingTime;
					}
				}
			}
		} catch (NullPointerException e) {
			//Do nothing in case of error
		}
	}

	/**
	 * This function calculates how much energy the plug consumes.
	 */
	public void calculateEnergy() {
		try {
			for (int i = 0; i < Main.smartPlugs.size(); i++) {
				if (Main.smartPlugs.get(i).name.equals(name)) {

					/* Calculates the operating time of the device and multiplies this value by (Ampere * Volt) / 60 */
					if (!Objects.equals(Main.smartPlugs.get(i).onTime, LocalDateTime.of(1, 1, 1, 0, 0, 0))) {
						LocalDateTime futureTime; // checks whether the time the plug is plugged in is longer or the time the plug stays on is longer
						if (Main.smartPlugs.get(i).onTime.isAfter(Main.smartPlugs.get(i).inTime)) {
							futureTime = Main.smartPlugs.get(i).onTime;
						} else {
							futureTime = Main.smartPlugs.get(i).inTime;
						}
						long passingTime = Duration.between(futureTime, Main.time).toMinutes();
						Main.smartPlugs.get(i).energy += Main.smartPlugs.get(i).volt * Main.smartPlugs.get(i).ampere * passingTime / 60;
					}
				}
			}

			/* prints required errors */
		} catch (NullPointerException e) {
			//Do nothing in case of error
		}
	}
}
