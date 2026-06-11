import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class Lvl5 extends GameManagement {
	
	/**
	 * Constructs the Level 5 of the Duck Hunt game.
	 *
	 * @param stage      The primary stage of the JavaFX application.
	 * @param background The ImageView representing the background of the game.
	 * @param crosshair  The ImageView representing the crosshair in the game.
	 * @param foreground The ImageView representing the foreground of the game.
	 * @param width      The width of the game scene.
	 * @param height     The height of the game scene.
	 */
	Lvl5(Stage stage, ImageView background, ImageView crosshair, ImageView foreground, double width, double height) {
		super(stage, background, crosshair, foreground, width, height, 5, 3);
		
		// Create and display the ducks for Level 5
		ImageView duck1 = straightFlight(blackDuck, (int) (scale * -80), (int) (scale * -80));
		ImageView duck2 = straightFlight(blueDuck, (int) (scale * 100), (int) (scale * -35));
		ImageView duck3 = crossFlight(redDuck, (int) (scale * -100), 0);
		levelPage(new HashMap<ImageView, ArrayList<Image>>() {{
			put(duck1, blackDuck);
			put(duck2, blueDuck);
			put(duck3, redDuck);
		}});
	}
	
	/**
	 * Proceeds to the next level in the Duck Hunt game.
	 * This method is called when the player completes Level 5 and needs to progress to Level 6.
	 * The implementation of this method handles the necessary actions for transitioning to Level 6,
	 * such as updating the level number and defining the key event to start Level 6.
	 */
	@Override
	void nextLvl() {
		scene.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				stopMusic(medias);
				new Lvl6(stage, background, crosshair, foreground, width, height);
			}
		});
	}
}