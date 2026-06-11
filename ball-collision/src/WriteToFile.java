import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class WriteToFile {

    public static void writeFile(String message) {
        try {
            FileWriter myWriter = new FileWriter("output.txt", true); //create a txt file
            myWriter.write(message); //write to the file
            myWriter.close(); //close the file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /* Since the above code has "append: true", it deletes if there is a txt file with this name before
     running the code and writing something to the txt file because the above code adds text after each
     run without deleting old texts. */
    public static void deleteFile(){
        File file = new File("output.txt");
        if (file.exists()) {
            file.delete();
        }
    }
}