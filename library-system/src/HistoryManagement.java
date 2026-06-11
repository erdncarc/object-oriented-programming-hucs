public class HistoryManagement implements Process {
	
	private int numberOfStudents = 0;
	private int numberOfAcademics = 0;
	private int numberOfPrinted = 0;
	private int numberOfHandwritten = 0;
	private int numberOfBorrowed = 0;
	private int numberOfRead = 0;
	
	void historyData() {
		for (MemberManager member : Orientation.memberList.values()) {
			if (member.type.equals("A")) {
				numberOfAcademics++;
			}
			if (member.type.equals("S")) {
				numberOfStudents++;
			}
		}
		for (BookManager book : Orientation.bookList.values()) {
			if (book.type.equals("P")) {
				numberOfPrinted++;
			}
			if (book.type.equals("H")) {
				numberOfHandwritten++;
			}
			if (book.situation.equals("R")) {
				numberOfRead++;
			}
			if (book.situation.equals("B")) {
				numberOfBorrowed++;
			}
		}
		
		WriteFile.write("History of library:\n\nNumber of students: " + numberOfStudents);
		for (MemberManager member : Orientation.memberList.values()) {
			if (member.type.equals("S")) {
				WriteFile.write("Student [id: " + member.id + "]");
			}
		}
		WriteFile.write("\nNumber of academics: " + numberOfAcademics);
		for (MemberManager member : Orientation.memberList.values()) {
			if (member.type.equals("A")) {
				WriteFile.write("Academic [id: " + member.id + "]");
			}
		}
		WriteFile.write("\nNumber of printed books: " + numberOfPrinted);
		for (BookManager book : Orientation.bookList.values()) {
			if (book.type.equals("P")) {
				WriteFile.write("Printed [id: " + book.id + "]");
			}
		}
		WriteFile.write("\nNumber of handwritten books: " + numberOfHandwritten);
		for (BookManager book : Orientation.bookList.values()) {
			if (book.type.equals("H")) {
				WriteFile.write("Handwritten [id: " + book.id + "]");
			}
		}
		WriteFile.write("\nNumber of borrowed books: " + numberOfBorrowed);
		for (BookManager book : Orientation.bookList.values()) {
			if (book.situation.equals("B")) {
				WriteFile.write("The book [" + book.id + "] was borrowed by member [" + book.borrower_reader + "] at " + book.statusChangeTimeForHistory);
			}
		}
		WriteFile.write("\nNumber of books read in library: " + numberOfRead);
		for (BookManager book : Orientation.bookList.values()) {
			if (book.situation.equals("R")) {
				WriteFile.write("The book [" + book.id + "] was read in library by member [" + book.borrower_reader + "] at " + book.statusChangeTimeForHistory);
			}
		}
	}
}
