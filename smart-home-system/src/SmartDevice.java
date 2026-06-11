import java.time.LocalDateTime;

/**
 * Variables found in all smart devices are found in this class.
 */
public class SmartDevice implements Process {

	String name;
	String status = "Off";
	LocalDateTime switchTime = LocalDateTime.of(1, 1, 1, 0, 0, 0);
	LocalDateTime temporarySwitchTime = LocalDateTime.of(1, 1, 1, 0, 0, 0); // a backup switch time object
	boolean situation = false; // this variable determines whether a switch time is assigned to this device

	public LocalDateTime getSwitchTime() { // for TimeSortableComparator
		return switchTime;
	}

	public LocalDateTime getTemporarySwitchTime() { // for TimeSortableComparator
		return temporarySwitchTime;
	}
}
