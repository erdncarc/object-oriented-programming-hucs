import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ReturnManagement implements Process {
	
	String[] input;
	
	ReturnManagement(String[] input) {
		this.input = input;
	}
	
	void returnBook() {
		try {
			if (!Orientation.bookList.containsKey(Integer.parseInt(input[1])) ||
					!Orientation.memberList.containsKey(Integer.parseInt(input[2])) ||
					!Orientation.bookList.get(Integer.parseInt(input[2])).borrower_reader.equals(input[2])) {
				throw new Exception("You cannot return this book!");
			}
			
			Orientation.bookList.get(Integer.parseInt(input[1])).situation = "L";
			Orientation.bookList.get(Integer.parseInt(input[1])).borrower_reader = "";
			Orientation.bookList.get(Integer.parseInt(input[1])).numberExtend = 1;
			Orientation.memberList.get(Integer.parseInt(input[2])).numberBarrow++;
			
			String[] time = input[3].split("-");
			LocalDate returnTime = LocalDate.of(Integer.parseInt(time[0]), Integer.parseInt(time[1]), Integer.parseInt(time[2]));
			long delayTime = ChronoUnit.DAYS.between(Orientation.bookList.get(Integer.parseInt(input[1])).statusChangeTime, returnTime);
			switch (Orientation.memberList.get(Integer.parseInt(input[2])).type) {
				
				case "S":
					long fee1 = delayTime > 7 ? delayTime - 7 : 0;
					WriteFile.write("The book [" + input[1] + "] was returned by member [" + input[2] + "] at " + input[3] + " Fee: " + fee1);
					break;
				
				case "A":
					long fee2 = delayTime > 14 ? delayTime - 14 : 0;
					WriteFile.write("The book [" + input[1] + "] was returned by member [" + input[2] + "] at " + input[3] + " Fee: " + fee2);
					break;
			}
		} catch (Exception e) {
			WriteFile.write(e.getMessage());
		}
	}
}