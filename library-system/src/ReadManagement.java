public class ReadManagement implements Process {
	
	String[] input;
	
	ReadManagement(String[] input) {
		this.input = input;
	}
	
	void readBook() {
		try {
			if (!Orientation.bookList.containsKey(Integer.parseInt(input[1])) ||
					!Orientation.memberList.containsKey(Integer.parseInt(input[2])) ||
					!Orientation.bookList.get(Integer.parseInt(input[1])).situation.equals("L")) {
				throw new Exception("You can not read this book!");
			}
			
			if (Orientation.memberList.get(Integer.parseInt(input[2])).type.equals("S") &&
					Orientation.bookList.get(Integer.parseInt(input[1])).type.equals("H")) {
				throw new Exception("Students can not read handwritten books!");
			}
			
			Orientation.bookList.get(Integer.parseInt(input[1])).situation = "R";
			Orientation.bookList.get(Integer.parseInt(input[1])).borrower_reader = input[2];
			BorrowManagement.changeTime(input);
			
			WriteFile.write("The book [" + input[1] + "] was read in library by member [" + input[2] + "] at " + input[3]);
			
		} catch (Exception e) {
			WriteFile.write(e.getMessage());
		}
	}
}