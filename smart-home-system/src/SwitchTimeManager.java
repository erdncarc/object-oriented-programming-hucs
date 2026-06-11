import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 * This class changes the state of smart devices due to time transitions.
 */
public class SwitchTimeManager implements Process {

	String[] input;

	/**
	 * Assigns the received input line to a variable in this class.
	 *
	 * @param input - stores the required input line
	 */
	public SwitchTimeManager(String[] input) {
		this.input = input;
	}

	/**
	 * Function in which state changes are made depending on time transitions.
	 */
	public void timeManager() {
		try {

			/* The part required for the Nop command to jump to the next switch time. */
			if (input[0].equals("Nop")) {
				if (Orientation.switchTime.size() == 0) {
					throw new Exception("ERROR: There is nothing to switch!");
				}

				ArrayList<Integer> indexList = new ArrayList<>(); // list required to delete switch times that point before the actual time
				for (int i = 0; i < Orientation.switchTime.size(); i++) {
					if (!Orientation.switchTime.get(i).isAfter(Main.time)) {
						indexList.add(i);
					}
				}
				Collections.reverse(indexList);
				for (int i : indexList) {
					Orientation.switchTime.remove(i);
				}

				Collections.sort(Orientation.switchTime);
				Main.time = Orientation.switchTime.get(0);

				indexList = new ArrayList<>(); //if switch times are the same as real time, list required to delete them together
				for (int i = 0; i < Orientation.switchTime.size(); i++) {
					if (Orientation.switchTime.get(i).isEqual(Main.time)) {
						indexList.add(i);
					}
				}
				Collections.reverse(indexList);
				for (int i : indexList) {
					Orientation.switchTime.remove(i);
				}
			}

			/* The following loops change the states of the devices whose switch time has expired.
			It also performs calculations for camera and plug. */
			for (int i = 0; i < Main.smartPlugs.size(); i++) {
				if (!Main.smartPlugs.get(i).switchTime.isAfter(Main.time) && !Objects.equals(Main.smartPlugs.get(i).switchTime, LocalDateTime.of(1, 1, 1, 0, 0, 0))) {
					if (Main.smartPlugs.get(i).status.equals("Off")) {
						Main.smartPlugs.get(i).status = "On";
						Main.smartPlugs.get(i).onTime = Main.time;
					} else {
						Main.smartPlugs.get(i).status = "Off";
						if (Main.smartPlugs.get(i).plugIn) {
							Calculate calculate = new Calculate(Main.smartPlugs.get(i).name);
							calculate.calculateEnergy(); //redirects to the class where calculations are made
						}
						Main.smartPlugs.get(i).onTime = LocalDateTime.of(1, 1, 1, 0, 0, 0);
					}
					Orientation.switchTime.remove(Main.smartPlugs.get(i).switchTime);
					Main.smartPlugs.get(i).switchTime = LocalDateTime.of(1, 1, 1, 0, 0, 0);
				}
			}

			for (int i = 0; i < Main.smartCameras.size(); i++) {
				if (!Main.smartCameras.get(i).switchTime.isAfter(Main.time) && !Objects.equals(Main.smartCameras.get(i).switchTime, LocalDateTime.of(1, 1, 1, 0, 0, 0))) {
					if (Main.smartCameras.get(i).status.equals("Off")) {
						Main.smartCameras.get(i).status = "On";
						Main.smartCameras.get(i).onTime = Main.time;
					} else {
						Main.smartCameras.get(i).status = "Off";
						Calculate calculate = new Calculate(Main.smartCameras.get(i).name);
						calculate.calculateStorage(); //redirects to the class where calculations are made
						Main.smartCameras.get(i).onTime = LocalDateTime.of(1, 1, 1, 0, 0, 0);
					}
					Orientation.switchTime.remove(Main.smartCameras.get(i).switchTime);
					Main.smartCameras.get(i).switchTime = LocalDateTime.of(1, 1, 1, 0, 0, 0);
				}
			}

			for (int i = 0; i < Main.smartColorLambs.size(); i++) {
				if (!Main.smartColorLambs.get(i).switchTime.isAfter(Main.time) && !Objects.equals(Main.smartColorLambs.get(i).switchTime, LocalDateTime.of(1, 1, 1, 0, 0, 0))) {
					if (Main.smartColorLambs.get(i).status.equals("Off")) {
						Main.smartColorLambs.get(i).status = "On";
					} else {
						Main.smartColorLambs.get(i).status = "Off";
					}
					Orientation.switchTime.remove(Main.smartColorLambs.get(i).switchTime);
					Main.smartColorLambs.get(i).switchTime = LocalDateTime.of(1, 1, 1, 0, 0, 0);
				}
			}

			for (int i = 0; i < Main.smartLambs.size(); i++) {
				if (!Main.smartLambs.get(i).switchTime.isAfter(Main.time) && !Objects.equals(Main.smartLambs.get(i).switchTime, LocalDateTime.of(1, 1, 1, 0, 0, 0))) {
					if (Main.smartLambs.get(i).status.equals("Off")) {
						Main.smartLambs.get(i).status = "On";
					} else {
						Main.smartLambs.get(i).status = "Off";
					}
					Orientation.switchTime.remove(Main.smartLambs.get(i).switchTime);
					Main.smartLambs.get(i).switchTime = LocalDateTime.of(1, 1, 1, 0, 0, 0);
				}
			}

			/* prints required errors */
		} catch (NullPointerException e) {
			//Do nothing in case of error
		} catch (Exception e) {
			WriteTxt.write(e.getMessage() + "\n");
		}
	}
}
