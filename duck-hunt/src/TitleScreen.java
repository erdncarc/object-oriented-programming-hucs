import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The TitleScreen class represents the title screen of the DuckHunt game.
 * It displays the welcome screen and handles user input for starting the game or exiting.
 */
public class TitleScreen extends SceneManagement {
	MediaPlayer title;
	
	/**
	 * Constructs a TitleScreen object with the given stage.
	 * It sets up the initial page of the title screen.
	 *
	 * @param stage the primary stage for the JavaFX application
	 */
	TitleScreen(Stage stage) {
		super(stage);
		titlePage();
	}
	
	/**
	 * Constructs a TitleScreen object with the given stage and media player.
	 * It sets up the initial page of the title screen and sets the provided media player for background music.
	 *
	 * @param stage       the primary stage for the JavaFX application
	 * @param mediaPlayer the media player to use for background music
	 */
	TitleScreen(Stage stage, MediaPlayer mediaPlayer) {
		super(stage);
		this.title = mediaPlayer;
		titlePage();
	}
	
	/**
	 * Sets up the page of the title screen.
	 * It sets the stage title, background image, and welcome text.
	 * If a media player is not provided, it plays the default background music.
	 */
	private void titlePage() {
		stage.setTitle("HUBBM Duck Hunt");
		stage.getIcons().add(new Image("assets/favicon/1.png"));
		
		if (title == null) title = playMusic("assets/effects/Title.mp3");
		title.setCycleCount(-1);
		
		ImageView titleBackground = setPicture(new Image("assets/welcome/1.png"));
		this.width = scale * titleBackground.getBoundsInLocal().getWidth();
		this.height = scale * titleBackground.getBoundsInLocal().getHeight();
		
		Text titleText1 = writeText("PRESS ENTER TO START", (int) (scale * 17.5), 0, (int) (scale * 55), true);
		Text titleText2 = writeText("PRESS ESC TO EXIT", (int) (scale * 17.5), 0, (int) (scale * 35), true);
		
		Scene scene = showScene(new ArrayList<>(Arrays.asList(titleBackground, titleText1, titleText2)));
		
		keyControl(scene);
	}
	
	/**
	 * Adds key control to the specified scene.
	 * It listens for key presses and handles ENTER and ESCAPE key events.
	 * If ENTER is pressed, it creates a new SelectionScreen.
	 * If ESCAPE is pressed, it closes the stage.
	 *
	 * @param scene the scene to add key control to
	 */
	private void keyControl(Scene scene) {
		scene.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				new SelectionScreen(stage, width, height, title);
			} else if (e.getCode() == KeyCode.ESCAPE) {
				stage.close();
			}
		});
	}
}
