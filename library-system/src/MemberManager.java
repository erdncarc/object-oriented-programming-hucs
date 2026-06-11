public class MemberManager extends DataManager implements Process {
	
	int numberBarrow;
	
	MemberManager(String[] input) {
		this.input = input;
	}
	
	void addMember() {
		type = input[1];
		id = Orientation.memberList.size() + 1;
		numberBarrow = type.equals("A") ? 4 : 2;
		write();
	}
	
	private void write() {
		switch (type) {
			
			case "A":
				WriteFile.write("Created new member: Academic [id: " + id + "]");
				break;
				
			case "S":
				WriteFile.write("Created new member: Student [id: " + id + "]");
				break;
		}
	}
}