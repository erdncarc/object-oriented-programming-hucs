import java.time.LocalDate;

public class BorrowManagement implements Process {
	
	String[] input;
	
	BorrowManagement(String[] input) {
		this.input = input;
	}
	
	void borrowBook() {
		try {
			if (!Orientation.bookList.containsKey(Integer.parseInt(input[1])) ||
					!Orientation.memberList.containsKey(Integer.parseInt(input[2])) ||
					!Orientation.bookList.get(Integer.parseInt(input[1])).situation.equals("L") ||
					Orientation.bookList.get(Integer.parseInt(input[1])).type.equals("H")) {
				throw new Exception("You cannot borrow this book!");
			}
			
			if (Orientation.memberList.get(Integer.parseInt(input[2])).numberBarrow == 0) {
				throw new Exception("You have exceeded the borrowing limit!");
			}
			
			changeTime(input);
			Orientation.bookList.get(Integer.parseInt(input[1])).situation = "B";
			Orientation.bookList.get(Integer.parseInt(input[1])).borrower_reader = input[2];
			Orientation.memberList.get(Integer.parseInt(input[2])).numberBarrow--;
			
			WriteFile.write("The book [" + input[1] + "] was borrowed by member [" + input[2] + "] at " + input[3]);
			
		} catch (Exception e) {
			WriteFile.write(e.getMessage());
		}
	}
	
	static void changeTime(String[] input) {
		String[] time = input[3].split("-");
		Orientation.bookList.get(Integer.parseInt(input[1])).statusChangeTime = LocalDate.of(Integer.parseInt(time[0]), Integer.parseInt(time[1]), Integer.parseInt(time[2]));
		Orientation.bookList.get(Integer.parseInt(input[1])).statusChangeTimeForHistory = LocalDate.of(Integer.parseInt(time[0]), Integer.parseInt(time[1]), Integer.parseInt(time[2]));
	}
}