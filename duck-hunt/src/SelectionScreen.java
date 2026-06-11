import javafx.scene.Scene;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The SelectionScreen class represents the selection screen of the DuckHunt game.
 * It allows the user to navigate through options and select a game mode or exit the game.
 */
public class SelectionScreen extends SceneManagement {
	MediaPlayer intro;
	MediaPlayer title;
	
	/**
	 * Constructs a SelectionScreen object with the given stage, width, height, and media player.
	 * It sets up the page of the selection screen with the provided parameters.
	 *
	 * @param stage  the primary stage for the JavaFX application
	 * @param width  the width of the scene
	 * @param height the height of the scene
	 * @param title  the media player for the title screen background music
	 */
	SelectionScreen(Stage stage, double width, double height, MediaPlayer title) {
		super(stage);
		this.width = width;
		this.height = height;
		this.title = title;
		selectionPage();
	}
	
	/**
	 * Sets up the second page of the selection screen.
	 * It loads the available background, crosshair, and foreground images.
	 * Sets the initial images for background, crosshair, and foreground.
	 * Displays the game instructions and crosshair image on the scene.
	 * Sets up key control for navigation and selection.
	 */
	private void selectionPage() {
		getPNGs(new File("assets/background/"), backgroundsList);
		getPNGs(new File("assets/crosshair/"), crosshairsList);
		getPNGs(new File("assets/foreground/"), foregroundsList);
		
		this.background = setPicture(backgroundsList.get(backgroundIndex));
		this.crosshair = setPicture(crosshairsList.get(crosshairsIndex));
		this.foreground = setPicture(foregroundsList.get(backgroundIndex));
		
		Text gameText1 = writeText("USE ARROW KEYS TO NAVIGATE", (int) (scale * 7.5), 0, (int) (scale * -115), false);
		Text gameText2 = writeText("PRESS ENTER TO START", (int) (scale * 7.5), 0, (int) (scale * -105), false);
		Text gameText3 = writeText("PRESS ESC TO EXIT", (int) (scale * 7.5), 0, (int) (scale * -95), false);
		
		Scene scene = showScene(new ArrayList<>(Arrays.asList(background, gameText1, gameText2, gameText3, crosshair)));
		
		keyControl(scene);
	}
	
	/**
	 * Adds key control to the specified scene.
	 * It listens for key presses and handles different key events for navigation and selection.
	 * If the ENTER key is pressed, it stops the title screen background music and plays the intro music and the game starts.
	 * If the ESCAPE key is pressed, it goes back to the title screen.
	 * If the UP key is pressed, it changes the crosshair image to the next one in the list.
	 * If the DOWN key is pressed, it changes the crosshair image to the previous one in the list.
	 * If the RIGHT key is pressed, it changes the background image to the next one in the list.
	 * If the LEFT key is pressed, it changes the background image to the previous one in the list.
	 *
	 * @param scene the scene to add key control to
	 */
	private void keyControl(Scene scene) {
		scene.setOnKeyPressed(e -> {
			if (intro != null) return;
			switch (e.getCode()) {
				case ENTER:
					title.stop();
					intro = playMusic("assets/effects/Intro.mp3");
					intro.setOnEndOfMedia(() -> new Lvl1(stage, background, crosshair, foreground, width, height));
					break;
				case ESCAPE:
					new TitleScreen(stage, title);
					break;
				case UP:
					crosshairsIndex = (crosshairsIndex + 1) % crosshairsList.size();
					break;
				case DOWN:
					crosshairsIndex = (crosshairsIndex - 1 + crosshairsList.size()) % crosshairsList.size();
					break;
				case RIGHT:
					backgroundIndex = (backgroundIndex + 1) % backgroundsList.size();
					break;
				case LEFT:
					backgroundIndex = (backgroundIndex - 1 + backgroundsList.size()) % backgroundsList.size();
					break;
			}
			background.setImage(backgroundsList.get(backgroundIndex));
			crosshair.setImage(crosshairsList.get(crosshairsIndex));
			foreground.setImage(foregroundsList.get(backgroundIndex));
		});
	}
}
