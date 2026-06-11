import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

/**
 * a class that prints a report of all transactions
 */
public class ReportManager implements Process {

	/**
	 * this method sorts and prints the information of the devices
	 */
	public void zReport() {
		WriteTxt.write("Time is:\t" + InitialTime.formatter.format(Main.time) + "\n"); // the time that should be written at the beginning of the report

		ArrayList<SmartDevice> switchList = new ArrayList<>(); // list of devices with switch time
		ArrayList<SmartDevice> switchOnList = new ArrayList<>(); // list of devices that have a switch time but have passed the switch time
		ArrayList<SmartDevice> nullList = new ArrayList<>(); // list of devices that have never had a switch time

		/* this loop goes around all devices and decides which device to add to which list */
		for (String name : Main.names) {
			for (int i = 0; i < Main.smartPlugs.size(); i++) {
				if (Main.smartPlugs.get(i).name.equals(name)) {
					if (!Main.smartPlugs.get(i).situation) {
						nullList.add(Main.smartPlugs.get(i));
					} else if (Main.smartPlugs.get(i).switchTime.equals(LocalDateTime.of(1, 1, 1, 0, 0, 0))) {
						switchOnList.add(Main.smartPlugs.get(i));
					} else {
						switchList.add(Main.smartPlugs.get(i));
					}
				}
			}
			for (int i = 0; i < Main.smartCameras.size(); i++) {
				if (Main.smartCameras.get(i).name.equals(name)) {
					if (Main.smartCameras.get(i).name.equals(name)) {
						if (!Main.smartCameras.get(i).situation) {
							nullList.add(Main.smartCameras.get(i));
						} else if (Main.smartCameras.get(i).switchTime.equals(LocalDateTime.of(1, 1, 1, 0, 0, 0))) {
							switchOnList.add(Main.smartCameras.get(i));
						} else {
							switchList.add(Main.smartCameras.get(i));
						}
					}
				}
			}
			for (int i = 0; i < Main.smartColorLambs.size(); i++) {
				if (Main.smartColorLambs.get(i).name.equals(name)) {
					if (Main.smartColorLambs.get(i).name.equals(name)) {
						if (!Main.smartColorLambs.get(i).situation) {
							nullList.add(Main.smartColorLambs.get(i));
						} else if (Main.smartColorLambs.get(i).switchTime.equals(LocalDateTime.of(1, 1, 1, 0, 0, 0))) {
							switchOnList.add(Main.smartColorLambs.get(i));
						} else {
							switchList.add(Main.smartPlugs.get(i));
						}
					}
				}
			}
			for (int i = 0; i < Main.smartLambs.size(); i++) {
				if (Main.smartLambs.get(i).name.equals(name)) {
					if (Main.smartLambs.get(i).name.equals(name)) {
						if (!Main.smartLambs.get(i).situation) {
							nullList.add(Main.smartLambs.get(i));
						} else if (Main.smartLambs.get(i).switchTime.equals(LocalDateTime.of(1, 1, 1, 0, 0, 0))) {
							switchOnList.add(Main.smartLambs.get(i));
						} else {
							switchList.add(Main.smartLambs.get(i));
						}
					}
				}
			}
		}

		TimeSortableComparator timeComparator = new TimeSortableComparator();
		switchList.sort(timeComparator); // sorts devices with switch time according to switch time

		/* sorts the devices that have a switch time but have passed the switch time in reverse order according to the switch time */
		switchOnList.sort((o1, o2) -> o2.getTemporarySwitchTime().compareTo(o1.getTemporarySwitchTime()));

		for (SmartDevice smartDevice : switchList) { // prints devices with switch time
			for (int i = 0; i < Main.smartPlugs.size(); i++) {
				if (Main.smartPlugs.get(i).name.equals(smartDevice.name)) {

					String time = Objects.equals(Main.smartPlugs.get(i).switchTime, LocalDateTime.of(1, 1, 1, 0, 0, 0)) ? "null" : InitialTime.formatter.format(Main.smartPlugs.get(i).switchTime);
					WriteTxt.write("Smart Plug " + smartDevice.name + " is o" + Main.smartPlugs.get(i).status.substring(1) +
							" and consumed " + String.format("%.2f", Main.smartPlugs.get(i).energy).replace(".", ",") +
							"W so far (excluding current device), and its time to switch its status is " +
							time + ".\n");
					break;
				}
			}
			for (int i = 0; i < Main.smartCameras.size(); i++) {
				if (Main.smartCameras.get(i).name.equals(smartDevice.name)) {

					String time = Objects.equals(Main.smartCameras.get(i).switchTime, LocalDateTime.of(1, 1, 1, 0, 0, 0)) ? "null" : InitialTime.formatter.format(Main.smartCameras.get(i).switchTime);
					WriteTxt.write("Smart Camera " + smartDevice.name + " is o" + Main.smartCameras.get(i).status.substring(1) +
							" and used " + String.format("%.2f", Main.smartCameras.get(i).storage).replace(".", ",") +
							" MB of storage so far (excluding current status), and its time to switch its status is " +
							time + ".\n");
					break;
				}
			}
			for (int i = 0; i < Main.smartColorLambs.size(); i++) {
				if (Main.smartColorLambs.get(i).name.equals(smartDevice.name)) {

					String type = Main.smartColorLambs.get(i).colorMode ? "0x" + String.format("%06X", Main.smartColorLambs.get(i).color) : Main.smartColorLambs.get(i).kelvin + "K";
					String time = Objects.equals(Main.smartColorLambs.get(i).switchTime, LocalDateTime.of(1, 1, 1, 0, 0, 0)) ? "null" : InitialTime.formatter.format(Main.smartColorLambs.get(i).switchTime);
					WriteTxt.write("Smart Color Lamp " + smartDevice.name + " is o" + Main.smartColorLambs.get(i).status.substring(1) + " and its color value is " +
							type + " with " + Main.smartColorLambs.get(i).brightness + "% brightness, and its time to switch its status is " +
							time + ".\n");
					break;
				}
			}
			for (int i = 0; i < Main.smartLambs.size(); i++) {
				if (Main.smartLambs.get(i).name.equals(smartDevice.name)) {

					String time = Objects.equals(Main.smartLambs.get(i).switchTime, LocalDateTime.of(1, 1, 1, 0, 0, 0)) ? "null" : InitialTime.formatter.format(Main.smartLambs.get(i).switchTime);
					WriteTxt.write("Smart Lamp " + smartDevice.name + " is o" + Main.smartLambs.get(i).status.substring(1) + " and its kelvin value is " +
							Main.smartLambs.get(i).kelvin + "K with " + Main.smartLambs.get(i).brightness + "% brightness, and its time to switch its status is " +
							time + ".\n");
					break;
				}
			}
		}

		for (SmartDevice smartDevice : switchOnList) { // prints the devices that have a switch time but have passed the switch times
			for (int i = 0; i < Main.smartPlugs.size(); i++) {
				if (Main.smartPlugs.get(i).name.equals(smartDevice.name)) {

					String time = Objects.equals(Main.smartPlugs.get(i).switchTime, LocalDateTime.of(1, 1, 1, 0, 0, 0)) ? "null" : InitialTime.formatter.format(Main.smartPlugs.get(i).switchTime);
					WriteTxt.write("Smart Plug " + smartDevice.name + " is o" + Main.smartPlugs.get(i).status.substring(1) +
							" and consumed " + String.format("%.2f", Main.smartPlugs.get(i).energy).replace(".", ",") +
							"W so far (excluding current device), and its time to switch its status is " +
							time + ".\n");
					break;
				}
			}
			for (int i = 0; i < Main.smartCameras.size(); i++) {
				if (Main.smartCameras.get(i).name.equals(smartDevice.name)) {

					String time = Objects.equals(Main.smartCameras.get(i).switchTime, LocalDateTime.of(1, 1, 1, 0, 0, 0)) ? "null" : InitialTime.formatter.format(Main.smartCameras.get(i).switchTime);
					WriteTxt.write("Smart Camera " + smartDevice.name + " is o" + Main.smartCameras.get(i).status.substring(1) +
							" and used " + String.format("%.2f", Main.smartCameras.get(i).storage).replace(".", ",") +
							" MB of storage so far (excluding current status), and its time to switch its status is " +
							time + ".\n");
					break;
				}
			}
			for (int i = 0; i < Main.smartColorLambs.size(); i++) {
				if (Main.smartColorLambs.get(i).name.equals(smartDevice.name)) {

					String type = Main.smartColorLambs.get(i).colorMode ? "0x" + String.format("%06X", Main.smartColorLambs.get(i).color) : Main.smartColorLambs.get(i).kelvin + "K";
					String time = Objects.equals(Main.smartColorLambs.get(i).switchTime, LocalDateTime.of(1, 1, 1, 0, 0, 0)) ? "null" : InitialTime.formatter.format(Main.smartColorLambs.get(i).switchTime);
					WriteTxt.write("Smart Color Lamp " + smartDevice.name + " is o" + Main.smartColorLambs.get(i).status.substring(1) + " and its color value is " +
							type + " with " + Main.smartColorLambs.get(i).brightness + "% brightness, and its time to switch its status is " +
							time + ".\n");
					break;
				}
			}
			for (int i = 0; i < Main.smartLambs.size(); i++) {
				if (Main.smartLambs.get(i).name.equals(smartDevice.name)) {

					String time = Objects.equals(Main.smartLambs.get(i).switchTime, LocalDateTime.of(1, 1, 1, 0, 0, 0)) ? "null" : InitialTime.formatter.format(Main.smartLambs.get(i).switchTime);
					WriteTxt.write("Smart Lamp " + smartDevice.name + " is o" + Main.smartLambs.get(i).status.substring(1) + " and its kelvin value is " +
							Main.smartLambs.get(i).kelvin + "K with " + Main.smartLambs.get(i).brightness + "% brightness, and its time to switch its status is " +
							time + ".\n");
					break;
				}
			}
		}

		for (SmartDevice smartDevice : nullList) { // print devices that have never had a switch time
			for (int i = 0; i < Main.smartPlugs.size(); i++) {
				if (Main.smartPlugs.get(i).name.equals(smartDevice.name)) {

					String time = Objects.equals(Main.smartPlugs.get(i).switchTime, LocalDateTime.of(1, 1, 1, 0, 0, 0)) ? "null" : InitialTime.formatter.format(Main.smartPlugs.get(i).switchTime);
					WriteTxt.write("Smart Plug " + smartDevice.name + " is o" + Main.smartPlugs.get(i).status.substring(1) +
							" and consumed " + String.format("%.2f", Main.smartPlugs.get(i).energy).replace(".", ",") +
							"W so far (excluding current device), and its time to switch its status is " +
							time + ".\n");
					break;
				}
			}
			for (int i = 0; i < Main.smartCameras.size(); i++) {
				if (Main.smartCameras.get(i).name.equals(smartDevice.name)) {

					String time = Objects.equals(Main.smartCameras.get(i).switchTime, LocalDateTime.of(1, 1, 1, 0, 0, 0)) ? "null" : InitialTime.formatter.format(Main.smartCameras.get(i).switchTime);
					WriteTxt.write("Smart Camera " + smartDevice.name + " is o" + Main.smartCameras.get(i).status.substring(1) +
							" and used " + String.format("%.2f", Main.smartCameras.get(i).storage).replace(".", ",") +
							" MB of storage so far (excluding current status), and its time to switch its status is " +
							time + ".\n");
					break;
				}
			}
			for (int i = 0; i < Main.smartColorLambs.size(); i++) {
				if (Main.smartColorLambs.get(i).name.equals(smartDevice.name)) {

					String type = Main.smartColorLambs.get(i).colorMode ? "0x" + String.format("%06X", Main.smartColorLambs.get(i).color) : Main.smartColorLambs.get(i).kelvin + "K";
					String time = Objects.equals(Main.smartColorLambs.get(i).switchTime, LocalDateTime.of(1, 1, 1, 0, 0, 0)) ? "null" : InitialTime.formatter.format(Main.smartColorLambs.get(i).switchTime);
					WriteTxt.write("Smart Color Lamp " + smartDevice.name + " is o" + Main.smartColorLambs.get(i).status.substring(1) + " and its color value is " +
							type + " with " + Main.smartColorLambs.get(i).brightness + "% brightness, and its time to switch its status is " +
							time + ".\n");
					break;
				}
			}
			for (int i = 0; i < Main.smartLambs.size(); i++) {
				if (Main.smartLambs.get(i).name.equals(smartDevice.name)) {

					String time = Objects.equals(Main.smartLambs.get(i).switchTime, LocalDateTime.of(1, 1, 1, 0, 0, 0)) ? "null" : InitialTime.formatter.format(Main.smartLambs.get(i).switchTime);
					WriteTxt.write("Smart Lamp " + smartDevice.name + " is o" + Main.smartLambs.get(i).status.substring(1) + " and its kelvin value is " +
							Main.smartLambs.get(i).kelvin + "K with " + Main.smartLambs.get(i).brightness + "% brightness, and its time to switch its status is " +
							time + ".\n");
					break;
				}
			}
		}

	}
}