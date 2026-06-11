import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class Lvl3 extends GameManagement {
	
	/**
	 * Constructs the Level 3 of the Duck Hunt game.
	 *
	 * @param stage      The primary stage of the JavaFX application.
	 * @param background The ImageView representing the background of the game.
	 * @param crosshair  The ImageView representing the crosshair in the game.
	 * @param foreground The ImageView representing the foreground of the game.
	 * @param width      The width of the game scene.
	 * @param height     The height of the game scene.
	 */
	Lvl3(Stage stage, ImageView background, ImageView crosshair, ImageView foreground, double width, double height) {
		super(stage, background, crosshair, foreground, width, height, 3, 2);
		
		// Create and display the ducks for Level 3
		ImageView duck1 = straightFlight(redDuck, (int) (scale * -70), (int) (scale * -70));
		ImageView duck2 = straightFlight(blueDuck, (int) (scale * 110), (int) (scale * -30));
		levelPage(new HashMap<ImageView, ArrayList<Image>>() {{
			put(duck1, redDuck);
			put(duck2, blueDuck);
		}});
	}
	
	/**
	 * Proceeds to the next level in the Duck Hunt game.
	 * This method is called when the player completes Level 3 and needs to progress to Level 4.
	 * The implementation of this method handles the necessary actions for transitioning to Level 4,
	 * such as updating the level number and defining the key event to start Level 4.
	 */
	@Override
	void nextLvl() {
		scene.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				stopMusic(medias);
				new Lvl4(stage, background, crosshair, foreground, width, height);
			}
		});
	}
}