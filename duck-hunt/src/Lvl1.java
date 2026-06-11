import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class Lvl1 extends GameManagement {
	
	/**
	 * Constructs the Level 1 of the Duck Hunt game.
	 *
	 * @param stage      The primary stage of the JavaFX application.
	 * @param background The ImageView representing the background of the game.
	 * @param crosshair  The ImageView representing the crosshair in the game.
	 * @param foreground The ImageView representing the foreground of the game.
	 * @param width      The width of the game scene.
	 * @param height     The height of the game scene.
	 */
	Lvl1(Stage stage, ImageView background, ImageView crosshair, ImageView foreground, double width, double height) {
		super(stage, background, crosshair, foreground, width, height, 1, 1);
		
		// Create and display the ducks for Level 1
		ImageView duck1 = straightFlight(blackDuck, (int) (scale * -70), (int) (scale * -70));
		levelPage(new HashMap<ImageView, ArrayList<Image>>() {{
			put(duck1, blackDuck);
		}});
	}
	
	/**
	 * Proceeds to the next level in the Duck Hunt game.
	 * This method is called when the player completes Level 1 and needs to progress to Level 2.
	 * The implementation of this method handles the necessary actions for transitioning to Level 2,
	 * such as updating the level number and defining the key event to start Level 2.
	 */
	@Override
	void nextLvl() {
		scene.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				stopMusic(medias);
				new Lvl2(stage, background, crosshair, foreground, width, height);
			}
		});
	}
}