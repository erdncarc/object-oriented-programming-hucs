public class MoveInX {

    public static String[][] moveOnBoard(String[][] board, String moves, int lenght, int i) {

        int sign = 0; //variable that decides the direction of movement
        int temporaryLocation = Main.xAxis; //variable that stores our initial location

        //I make the ball look like it's in a fake position to go to an area that doesn't exist
        if (moves.equals("R")) {
            sign = 1;
            if (Main.xAxis == lenght - 1) {
                Main.xAxis = -1;
            }
        }
        if (moves.equals("L")) {
            sign = -1;
            if (Main.xAxis == 0) {
                Main.xAxis = lenght;
            }
        }
        String newLocation = board[Main.yAxis][Main.xAxis + sign]; //new location of ball

        //the part for the colors we can score
        if (newLocation.equals("R") || newLocation.equals("Y") || newLocation.equals("B")) {
            if (newLocation.equals("R")) {
                Main.score += 10;
            } else if (newLocation.equals("Y")) {
                Main.score += 5;
            } else {
                Main.score -= 5;
            }
            board[Main.yAxis][temporaryLocation] = "X";
            board[Main.yAxis][Main.xAxis + sign] = "*";
            Main.xAxis += sign;

        } else if (newLocation.equals("W")) { //the part for the case of hitting the wall
            Main.xAxis = temporaryLocation;
            if (sign == 1) {
                moveOnBoard(board, "L", lenght, i);
            } else {
                moveOnBoard(board, "R", lenght, i);
            }

        } else if (newLocation.equals("H")) { //the part for the fall-in-the-hole part
            board[Main.yAxis][temporaryLocation] = " ";

        } else { //the part for colors that cannot be scored
            String temporaryColor = newLocation;
            board[Main.yAxis][Main.xAxis + sign] = "*";
            board[Main.yAxis][temporaryLocation] = temporaryColor;
            Main.xAxis += sign;
        }
        return board;
    }
}
