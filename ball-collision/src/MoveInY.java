public class MoveInY {

    public static String[][] moveOnBoard(String[][] board, String moves, int lenght, int i) {

        int sign = 0; //variable that decides the direction of movement
        int temporaryLocation = Main.yAxis; //variable that stores our initial location

        //I make the ball look like it's in a fake position to go to an area that doesn't exist
        if (moves.equals("D")) {
            sign = 1;
            if (Main.yAxis == lenght - 1) {
                Main.yAxis = -1;
            }
        }
        if (moves.equals("U")) {
            sign = -1;
            if (Main.xAxis == 0) {
                Main.xAxis = lenght;
            }
        }
        String newLocation = board[Main.yAxis + sign][Main.xAxis]; //new location of ball

        //the part for the colors we can score
        if (newLocation.equals("R") || newLocation.equals("Y") || newLocation.equals("B")) {
            if (newLocation.equals("R")) {
                Main.score += 10;
            } else if (newLocation.equals("Y")) {
                Main.score += 5;
            } else {
                Main.score -= 5;
            }
            board[temporaryLocation][Main.xAxis] = "X";
            board[Main.yAxis + sign][Main.xAxis] = "*";
            Main.yAxis += sign;

        } else if (newLocation.equals("W")) { //the part for the case of hitting the wall
            Main.yAxis = temporaryLocation;
            if (sign == 1) {
                moveOnBoard(board, "U", lenght, i);
            } else {
                moveOnBoard(board, "D", lenght, i);
            }

        } else if (newLocation.equals("H")) { //the part for the fall-in-the-hole part
            board[temporaryLocation][Main.xAxis] = " ";

        } else { //the part for colors that cannot be scored
            String temporaryColor = newLocation;
            board[Main.yAxis + sign][Main.xAxis] = "*";
            board[temporaryLocation][Main.xAxis] = temporaryColor;
            Main.yAxis += sign;
        }

        return board;
    }
}
