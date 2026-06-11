import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class Lvl6 extends GameManagement {
	
	/**
	 * Constructs the Level 6 of the Duck Hunt game.
	 *
	 * @param stage      The primary stage of the JavaFX application.
	 * @param background The ImageView representing the background of the game.
	 * @param crosshair  The ImageView representing the crosshair in the game.
	 * @param foreground The ImageView representing the foreground of the game.
	 * @param width      The width of the game scene.
	 * @param height     The height of the game scene.
	 */
	Lvl6(Stage stage, ImageView background, ImageView crosshair, ImageView foreground, double width, double height) {
		super(stage, background, crosshair, foreground, width, height, 6, 3);
		
		// Create and display the ducks for Level 6
		ImageView duck1 = crossFlight(blackDuck, (int) (scale * -80), (int) (scale * -80));
		ImageView duck2 = crossFlight(blueDuck, (int) (scale * 100), 0);
		ImageView duck3 = crossFlight(redDuck, (int) (scale * -100), (int) (scale * 35));
		levelPage(new HashMap<ImageView, ArrayList<Image>>() {{
			put(duck1, blackDuck);
			put(duck2, blueDuck);
			put(duck3, redDuck);
		}});
	}
	
	/**
	 * Ends the game of Duck Hunt.
	 * This method is called when the player has completed 6 levels and needs to return to the title screen or restart the game.
	 * The implementation of this method handles the necessary actions for return to the title screen or restarting the game.
	 */
	@Override
	void nextLvl() {
		scene.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				stopMusic(medias);
				new Lvl1(stage, background, crosshair, foreground, width, height);
			}
			if (event.getCode() == KeyCode.ESCAPE) {
				stopMusic(medias);
				new TitleScreen(stage);
			}
		});
	}
}