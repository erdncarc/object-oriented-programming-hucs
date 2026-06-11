import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;

/**
 * The SceneManagement class provides utility methods for managing scenes and visual elements in the DuckHunt game.
 * It serves as an abstract base class for other scene management classes.
 */
public abstract class SceneManagement extends DuckHunt {
	Stage stage;
	ArrayList<Image> backgroundsList = new ArrayList<>();
	ArrayList<Image> crosshairsList = new ArrayList<>();
	ArrayList<Image> foregroundsList = new ArrayList<>();
	int backgroundIndex;
	int crosshairsIndex;
	ImageView background;
	ImageView crosshair;
	ImageView foreground;
	double width;
	double height;
	
	/**
	 * Constructs a SceneManagement object with the given stage.
	 *
	 * @param stage the primary stage for the JavaFX application
	 */
	SceneManagement(Stage stage) {
		this.stage = stage;
	}
	
	/**
	 * Displays the specified nodes in a scene and sets it as the active scene in the stage.
	 *
	 * @param nodes the nodes to be displayed in the scene
	 * @return the created scene
	 */
	Scene showScene(ArrayList<Node> nodes) {
		StackPane pane = new StackPane();
		for (Node node : nodes) {
			pane.getChildren().add(node);
		}
		Scene scene = new Scene(pane, width, height);
		stage.setScene(scene);
		stage.show();
		return scene;
	}
	
	/**
	 * Creates and configures a text node with the specified message, font size, position, and effect.
	 *
	 * @param message  the text message to be displayed
	 * @param sizeFont the font size of the text
	 * @param x        the x-coordinate of the text position
	 * @param y        the y-coordinate of the text position
	 * @param effect   a flag indicating whether to apply a fading effect to the text
	 * @return the created text
	 */
	Text writeText(String message, int sizeFont, int x, int y, boolean effect) {
		Text text = new Text(message);
		text.setFont(Font.font("Arial", FontWeight.BOLD, sizeFont));
		text.setFill(Color.ORANGE);
		text.setTranslateX(x);
		text.setTranslateY(y);
		
		if (effect) {
			FadeTransition opaqueTransition = new FadeTransition(Duration.seconds(0.0001), text);
			opaqueTransition.setFromValue(0.0);
			opaqueTransition.setToValue(1.0);
			
			FadeTransition transparentTransition = new FadeTransition(Duration.seconds(0.0001), text);
			transparentTransition.setFromValue(1.0);
			transparentTransition.setToValue(0.0);
			
			SequentialTransition sequentialTransition = new SequentialTransition(opaqueTransition, new PauseTransition(Duration.seconds(1)), transparentTransition, new PauseTransition(Duration.seconds(1)));
			sequentialTransition.setCycleCount(SequentialTransition.INDEFINITE);
			sequentialTransition.play();
		}
		return text;
	}
	
	/**
	 * Creates and configures an image view with the specified image.
	 *
	 * @param image the image to be displayed in the image view
	 * @return the created image view
	 */
	ImageView setPicture(Image image) {
		ImageView picture = new ImageView(image);
		picture.setPreserveRatio(true);
		picture.setScaleX(scale);
		picture.setScaleY(scale);
		return picture;
	}
	
	/**
	 * Plays the audio file specified by the given path using a media player.
	 *
	 * @param path the path to the audio file
	 * @return the media player used to play the audio
	 */
	MediaPlayer playMusic(String path) {
		MediaPlayer mediaPlayer = new MediaPlayer(new Media(new File(path).toURI().toString()));
		mediaPlayer.setCycleCount(1);
		mediaPlayer.setVolume(volume);
		mediaPlayer.play();
		return mediaPlayer;
	}
	
	/**
	 * Retrieves all PNG images from the specified directory and adds them to the given images list.
	 *
	 * @param mainFile the directory to search for PNG images
	 * @param images   the list to store the retrieved images
	 */
	void getPNGs(File mainFile, ArrayList<Image> images) {
		File[] files = mainFile.listFiles();
		assert files != null;
		for (File file : files) {
			if (file.getName().endsWith(".png")) {
				Image image = new Image(file.toURI().toString());
				images.add(image);
			}
		}
	}
}
