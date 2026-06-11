public class Main {

    public static int score = 0; //game score
    public static int xAxis = 0; //x location of ball
    public static int yAxis = 0; //y location of ball
    public static boolean gameOver = false; //the state of the ball falling into the hole

    public static void main(String[] args) {

        ReadFromFile readFromFile = new ReadFromFile();

        String[] inputBoard = ReadFromFile.readFile(args[0]); //get gameboard from file
        String[] inputMoves = ReadFromFile.readFile(args[1]); //get moves from file

        int rowBoard = inputBoard.length; //number of rows of the game board
        int columnBoard = (inputBoard[0].length() + 1) / 2; //number of columns of the game board
        String[][] board = new String[rowBoard][columnBoard];

        //convert the game board into a two-dimensional list
        for (int i = 0; i < rowBoard; i++) {
            for (int k = 0; k < columnBoard; k++) {
                char color = inputBoard[i].charAt(2 * k);
                board[i][k] = String.valueOf(color);

                if (board[i][k].equals("*")) { //find the position of the ball
                    xAxis = k;
                    yAxis = i;
                }
            }
        }

        int numberOfMoves = (inputMoves[0].length() + 1) / 2; //number of moves
        String[] moves = new String[numberOfMoves];

        //convert the moves into a list
        for (int i = 0; i < numberOfMoves; i++) {
            char way = inputMoves[0].charAt(2 * i);
            moves[i] = String.valueOf(way);
        }

        MoveInX moveInX = new MoveInX();
        MoveInY moveInY = new MoveInY();

        //the game is played in this part
        for (int i = 0; i < numberOfMoves; i++) {
            if (moves[i].equals("R")) {
                MoveInX.moveOnBoard(board, "R", columnBoard, i);
            }
            if (moves[i].equals("L")) {
                MoveInX.moveOnBoard(board, "L", columnBoard, i);
            }
            if (moves[i].equals("U")) {
                MoveInY.moveOnBoard(board, "U", columnBoard, i);
            }
            if (moves[i].equals("D")) {
                MoveInY.moveOnBoard(board, "D", columnBoard, i);
            }

            //whether the ball falls into the hole or not is checked in this part
            for (int j = 0; j < rowBoard; j++) {
                for (int k = 0; k < columnBoard; k++) {
                    if (board[j][k].equals(" ")) {
                        gameOver = true;
                        break;
                    }
                }
            }
            if (gameOver) {
                break;
            }
        }

        WriteToFile writeToFile = new WriteToFile();

        //deletes the file with this name, if any, and prepares the ground for a new one
        WriteToFile.deleteFile();

        //this part prints the outputs to the file
        WriteToFile.writeFile("Game board:\n");
        for (String i : inputBoard) {
            WriteToFile.writeFile(i + "\n");
        }
        WriteToFile.writeFile("\nYour movement is:\n" + inputMoves[0] + "\n");
        WriteToFile.writeFile("\nYour output is:\n");
        for (int i = 0; i < rowBoard; i++) {
            for (int k = 0; k < columnBoard; k++) {
                if (k == columnBoard - 1) {
                    WriteToFile.writeFile(board[i][k] + "\n");
                } else {
                    WriteToFile.writeFile(board[i][k] + " ");
                }
            }
        }
        if (gameOver) {
            WriteToFile.writeFile("\nGame Over!");
        }
        WriteToFile.writeFile("\nScore: " + score + "\n");
    }

}