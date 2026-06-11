import java.time.LocalDate;

public class BookManager extends DataManager implements Process {
	
	LocalDate statusChangeTimeForHistory = LocalDate.of(1, 1, 1);
	LocalDate statusChangeTime = LocalDate.of(1, 1, 1);
	String situation = "L"; // L = Library, B = Barrow, R = Read
	String borrower_reader = "";
	int numberExtend = 1;
	
	BookManager(String[] input) {
		this.input = input;
	}
	
	void addBook() {
		type = input[1];
		id = Orientation.bookList.size() + 1;
		write();
	}
	
	private void write() {
		switch (input[1]) {
			
			case "P":
				WriteFile.write("Created new book: Printed [id: " + id + "]");
				break;
			
			case "H":
				WriteFile.write("Created new book: Handwritten [id: " + id + "]");
				break;
		}
	}
}