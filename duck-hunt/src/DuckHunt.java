import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The main class for the DuckHunt game application.
 * This class extends the JavaFX Application class and is responsible for launching the game.
 */
public class DuckHunt extends Application {
	double scale = 3;
	double volume = 0.025;
	
	/**
	 * The main entry point for the DuckHunt game application.
	 * It launches the JavaFX application by calling the `launch` method.
	 *
	 * @param args the command line arguments passed to the application (not used in this case)
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * The start method of the JavaFX application.
	 * It is called when the application is launched and sets up the initial screen.
	 *
	 * @param stage the primary stage for the JavaFX application
	 */
	@Override
	public void start(Stage stage) {
		new TitleScreen(stage);
	}
}
