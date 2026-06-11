import java.util.LinkedHashMap;
import java.util.Map;

public class Orientation {
	
	static Map<Integer, BookManager> bookList = new LinkedHashMap<>();
	static Map<Integer, MemberManager> memberList = new LinkedHashMap<>();
	
	void process(String[] input) {
		switch (input[0]) {
			
			case "addBook":
				BookManager bookManager = new BookManager(input);
				bookManager.addBook();
				bookList.put(bookManager.id, bookManager);
				break;
			
			case "addMember":
				MemberManager memberManager = new MemberManager(input);
				memberManager.addMember();
				memberList.put(memberManager.id, memberManager);
				break;
			
			case "borrowBook":
				BorrowManagement borrowManagement = new BorrowManagement(input);
				borrowManagement.borrowBook();
				break;
			
			case "returnBook":
				ReturnManagement returnManagement = new ReturnManagement(input);
				returnManagement.returnBook();
				break;
			
			case "extendBook":
				ExtendManagement extendManagement = new ExtendManagement(input);
				extendManagement.extendBook();
				break;
			
			case "readInLibrary":
				ReadManagement readManagement = new ReadManagement(input);
				readManagement.readBook();
				break;
			
			case "getTheHistory":
				HistoryManagement historyManagement = new HistoryManagement();
				historyManagement.historyData();
				break;
			
			default:
				WriteFile.write("Wrong command!");
				break;
		}
	}
}