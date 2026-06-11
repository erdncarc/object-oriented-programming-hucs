import java.time.LocalDateTime;
import java.util.Comparator;

public class TimeSortableComparator implements Comparator<SmartDevice> {

	/**
	 * Compares two smart device based on their switch time.
	 *
	 * @param ts1 the first object to be compared.
	 * @param ts2 the second object to be compared.
	 * @return - if ts1's switch time is earlier than ts2's switch time (1), if ts1's switch time is later than ts2's switch time (2), if both switch times are equal (0)
	 */
	public int compare(SmartDevice ts1, SmartDevice ts2) {
		LocalDateTime dt1 = ts1.getSwitchTime();
		LocalDateTime dt2 = ts2.getSwitchTime();
		return dt1.compareTo(dt2);
	}
}
