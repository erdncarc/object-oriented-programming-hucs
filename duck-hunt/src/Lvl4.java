import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class Lvl4 extends GameManagement {
	
	/**
	 * Constructs the Level 4 of the Duck Hunt game.
	 *
	 * @param stage      The primary stage of the JavaFX application.
	 * @param background The ImageView representing the background of the game.
	 * @param crosshair  The ImageView representing the crosshair in the game.
	 * @param foreground The ImageView representing the foreground of the game.
	 * @param width      The width of the game scene.
	 * @param height     The height of the game scene.
	 */
	Lvl4(Stage stage, ImageView background, ImageView crosshair, ImageView foreground, double width, double height) {
		super(stage, background, crosshair, foreground, width, height, 4, 2);
		
		// Create and display the ducks for Level 4
		ImageView duck1 = crossFlight(blackDuck, (int) (scale * -110), (int) (scale * -80));
		ImageView duck2 = crossFlight(blueDuck, (int) (scale * -80), (int) (scale * 80));
		levelPage(new HashMap<ImageView, ArrayList<Image>>() {{
			put(duck1, blackDuck);
			put(duck2, blueDuck);
		}});
	}
	
	/**
	 * Proceeds to the next level in the Duck Hunt game.
	 * This method is called when the player completes Level 4 and needs to progress to Level 5.
	 * The implementation of this method handles the necessary actions for transitioning to Level 5,
	 * such as updating the level number and defining the key event to start Level 5.
	 */
	@Override
	void nextLvl() {
		scene.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				stopMusic(medias);
				new Lvl5(stage, background, crosshair, foreground, width, height);
			}
		});
	}
}