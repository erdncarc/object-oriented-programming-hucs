import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ExtendManagement implements Process {
	
	String[] input;
	
	ExtendManagement(String[] input) {
		this.input = input;
	}
	
	void extendBook() {
		try {
			if (!Orientation.bookList.containsKey(Integer.parseInt(input[1])) ||
					!Orientation.memberList.containsKey(Integer.parseInt(input[2])) ||
					!Orientation.bookList.get(Integer.parseInt(input[1])).borrower_reader.equals(input[2]) ||
					Orientation.bookList.get(Integer.parseInt(input[1])).numberExtend == 0) {
				throw new Exception("You cannot extend the deadline!");
			}
			
			String[] time = input[3].split("-");
			LocalDate extendTime = LocalDate.of(Integer.parseInt(time[0]), Integer.parseInt(time[1]), Integer.parseInt(time[2]));
			switch (Orientation.memberList.get(Integer.parseInt(input[2])).type) {
				
				case "S":
					long delayTime1 = ChronoUnit.DAYS.between(Orientation.bookList.get(Integer.parseInt(input[1])).statusChangeTime, extendTime);
					if (delayTime1 > 7) {
						throw new Exception("You cannot extend the deadline!");
					}
					break;
				
				case "A":
					long delayTime2 = ChronoUnit.DAYS.between(Orientation.bookList.get(Integer.parseInt(input[1])).statusChangeTime, extendTime);
					if (delayTime2 > 14) {
						throw new Exception("You cannot extend the deadline!");
					}
					break;
			}
			
			WriteFile.write("The deadline of book [" + input[1] + "] was extended by member [" + input[2] + "] at " + input[3]);
			
			Orientation.bookList.get(Integer.parseInt(input[1])).numberExtend = 0;
			
			switch (Orientation.memberList.get(Integer.parseInt(input[2])).type) {
				
				case "S":
					LocalDate extendTime1 = Orientation.bookList.get(Integer.parseInt(input[1])).statusChangeTime.plusDays(14);
					WriteFile.write("New deadline of book [" + input[1] + "] is " + extendTime1);
					Orientation.bookList.get(Integer.parseInt(input[1])).statusChangeTime = Orientation.bookList.get(Integer.parseInt(input[1])).statusChangeTime.plusDays(7);
					break;
				
				case "A":
					LocalDate extendTime2 = Orientation.bookList.get(Integer.parseInt(input[1])).statusChangeTime.plusDays(28);
					WriteFile.write("New deadline of book [" + input[1] + "] is " + extendTime2);
					Orientation.bookList.get(Integer.parseInt(input[1])).statusChangeTime = Orientation.bookList.get(Integer.parseInt(input[1])).statusChangeTime.plusDays(14);
					break;
			}
		} catch (Exception e) {
			WriteFile.write(e.getMessage());
		}
	}
}