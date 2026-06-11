import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * A class that represents the management of a game scene.
 * It extends the {@link SceneManagement} class.
 */
public abstract class GameManagement extends SceneManagement {
	ArrayList<Image> blackDuck = new ArrayList<>();
	ArrayList<Image> blueDuck = new ArrayList<>();
	ArrayList<Image> redDuck = new ArrayList<>();
	HashMap<ImageView, Timeline> timelines = new HashMap<>();
	Scene scene;
	int level;
	int duckNumber;
	int ammo;
	Text levelText;
	Text ammoText;
	Text situaionText;
	Text controlText1;
	Text controlText2;
	boolean controlShot = true;
	ArrayList<MediaPlayer> medias = new ArrayList<>();
	
	/**
	 * Constructs a GameManagement object with the specified parameters.
	 *
	 * @param stage      the stage to which the game scene belongs
	 * @param background the background image of the game scene
	 * @param crosshair  the crosshair image used for targeting
	 * @param foreground the foreground image of the game scene
	 * @param width      the width of the game scene
	 * @param height     the height of the game scene
	 * @param level      the level of the game
	 * @param duckNumber the number of ducks in the game
	 */
	GameManagement(Stage stage, ImageView background, ImageView crosshair, ImageView foreground, double width, double height, int level, int duckNumber) {
		super(stage);
		this.background = background;
		this.crosshair = crosshair;
		this.foreground = foreground;
		this.width = width;
		this.height = height;
		this.level = level;
		this.duckNumber = duckNumber;
		this.ammo = duckNumber * 3;
		
		getPNGs(new File("assets/duck_black/"), blackDuck);
		getPNGs(new File("assets/duck_blue/"), blueDuck);
		getPNGs(new File("assets/duck_red/"), redDuck);
	}
	
	/**
	 * Displays the level page with the specified ducks.
	 *
	 * @param ducks a mapping of ducks' image views to their respective image lists
	 */
	void levelPage(HashMap<ImageView, ArrayList<Image>> ducks) {
		levelText = writeText("LEVEL " + level + "/6", (int) (scale * 7.5), 0, (int) (scale * -115), false);
		ammoText = writeText("Ammo Left: " + ammo, (int) (scale * 7.5), (int) (scale * 100), (int) (scale * -115), false);
		situaionText = writeText("", (int) (scale * 15), 0, (int) (scale * -15), false);
		controlText1 = writeText("", (int) (scale * 15), 0, 0, true);
		controlText2 = writeText("", (int) (scale * 15), 0, (int) (scale * 15), true);
		
		ArrayList<Node> nodes = new ArrayList<>(Arrays.asList(background, foreground, levelText, ammoText, situaionText, controlText1, controlText2, crosshair));
		nodes.addAll(1, ducks.keySet());
		this.scene = showScene(nodes);
		
		mouse(ducks);
	}
	
	/**
	 * Handles the mouse events in the game scene.
	 *
	 * @param ducks a mapping of ducks' image views to their respective image lists
	 */
	private void mouse(HashMap<ImageView, ArrayList<Image>> ducks) {
		scene.setCursor(Cursor.NONE);
		scene.setOnMouseMoved(e -> {
			crosshair.setTranslateX(e.getSceneX() - width / 2);
			crosshair.setTranslateY(e.getSceneY() - height / 2);
		});
		scene.setOnMouseEntered(e -> {
			crosshair.setVisible(true);
			scene.setCursor(Cursor.NONE);
		});
		scene.setOnMouseExited(e -> {
			crosshair.setVisible(false);
			scene.setCursor(Cursor.DEFAULT);
		});
		
		scene.setOnMouseReleased(e -> {
			if (e.getButton() == MouseButton.PRIMARY && controlShot) {
				MediaPlayer mediaPlayer = playMusic("assets/effects/Gunshot.mp3");
				medias.add(mediaPlayer);
				
				for (ImageView duck : ducks.keySet()) {
					shot(e, duck, ducks.get(duck));
				}
				
				ammo--;
				ammoText.setText("Ammo Left: " + ammo);
			}
			
			if (duckNumber == 0 && controlShot) {
				if (level == 6) {
					MediaPlayer mediaPlayer = playMusic("assets/effects/GameCompleted.mp3");
					medias.add(mediaPlayer);
					
					situaionText.setText("You have completed the game!");
					controlText1.setText("Press ENTER to play again");
					controlText2.setText("Press ESC to exit");
				} else {
					MediaPlayer mediaPlayer = playMusic("assets/effects/LevelCompleted.mp3");
					medias.add(mediaPlayer);
					situaionText.setText("YOU WIN!");
					controlText1.setText("Press ENTER to play next level");
				}
				controlShot = false;
				nextLvl();
				
			} else if (ammo == 0 && controlShot) {
				MediaPlayer mediaPlayer = playMusic("assets/effects/GameOver.mp3");
				medias.add(mediaPlayer);
				
				situaionText.setText("GAME OVER!");
				controlText1.setText("Press ENTER to play again");
				controlText2.setText("Press ESC to exit");
				
				scene.setOnKeyPressed(event -> {
					if (event.getCode() == KeyCode.ESCAPE) {
						stopMusic(medias);
						new TitleScreen(stage);
					}
					if (event.getCode() == KeyCode.ENTER) {
						stopMusic(medias);
						new Lvl1(stage, background, crosshair, foreground, width, height);
					}
				});
				controlShot = false;
			}
		});
	}
	
	/**
	 * Handles the shooting event when a mouse button is released.
	 *
	 * @param e     the mouse event
	 * @param duck  the image view of the duck
	 * @param color the list of duck images
	 */
	private void shot(MouseEvent e, ImageView duck, ArrayList<Image> color) {
		double x = duck.getBoundsInLocal().getWidth() * scale / 2;
		double y = duck.getBoundsInLocal().getHeight() * scale / 2;
		
		if (timelines.get(duck) != null && !timelines.get(duck).getStatus().equals(Animation.Status.STOPPED) &&
				(e.getX() - width / 2 < duck.getTranslateX() + x && e.getX() - width / 2 > duck.getTranslateX() - x) &&
				(e.getY() - height / 2 < duck.getTranslateY() + y && e.getY() - height / 2 > duck.getTranslateY() - y)) {
			MediaPlayer mediaPlayer = playMusic("assets/effects/DuckFalls.mp3");
			medias.add(mediaPlayer);
			
			timelines.get(duck).stop();
			
			if (duck.getScaleY() == -scale) duck.setScaleY(scale);
			duck.setImage(color.get(6));
			
			Timeline timeline1 = new Timeline(
					new KeyFrame(Duration.seconds(0.15), event -> duck.setImage(color.get(7)))
			);
			timeline1.play();
			
			Timeline timeline2 = new Timeline();
			timeline2.setCycleCount(Timeline.INDEFINITE);
			timeline2.getKeyFrames().add(
					new KeyFrame(Duration.seconds(scale * 0.03), event -> {
						double currentY = duck.getTranslateY();
						duck.setTranslateY(currentY + scale * height * 0.0075);
						if (currentY > height / 2) duck.setImage(null);
					})
			);
			timeline2.play();
			duckNumber--;
		}
	}
	
	/**
	 * Creates a duck that flies straight horizontally across the screen.
	 *
	 * @param duckList the list of duck images
	 * @param x        the initial x-coordinate of the duck
	 * @param y        the initial y-coordinate of the duck
	 * @return the image view of the duck
	 */
	ImageView straightFlight(ArrayList<Image> duckList, int x, int y) {
		final double[] X = {scale * width * 0.01};
		final int[] index = {3};
		final double[] elapsed = {0.0};
		
		ImageView duck = createDuck(duckList, x, y, index[0]);
		index[0]++;
		
		Timeline timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(
				new KeyFrame(Duration.seconds(scale * 0.04), e -> {
					double currentX = duck.getTranslateX();
					
					elapsed[0] += scale * 0.04;
					if (elapsed[0] >= 0.3) {
						duck.setImage(duckList.get(index[0]));
						index[0]++;
						if (index[0] == 6) index[0] = 3;
						elapsed[0] = 0.0;
					}
					
					xAxis(X, duck, currentX, duckList.get(0).getWidth());
				})
		);
		timeline.play();
		timelines.put(duck, timeline);
		return duck;
	}
	
	/**
	 * Creates a duck that flies in a cross pattern across the screen.
	 *
	 * @param duckList the list of duck images
	 * @param x        the initial x-coordinate of the duck
	 * @param y        the initial y-coordinate of the duck
	 * @return the image view of the duck
	 */
	ImageView crossFlight(ArrayList<Image> duckList, int x, int y) {
		final double[] X = {scale * width * 0.01};
		final double[] Y = {-scale * height * 0.01};
		final int[] index = {0};
		final double[] elapsed = {0.0};
		
		ImageView duck = createDuck(duckList, x, y, index[0]);
		index[0]++;
		
		Timeline timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(
				new KeyFrame(Duration.seconds(scale * 0.04), e -> {
					double currentX = duck.getTranslateX();
					double currentY = duck.getTranslateY();
					
					elapsed[0] += scale * 0.04;
					if (elapsed[0] >= 0.3) {
						duck.setImage(duckList.get(index[0]));
						index[0]++;
						if (index[0] == 3) index[0] = 0;
						elapsed[0] = 0.0;
					}
					
					xAxis(X, duck, currentX, duckList.get(0).getWidth());
					
					yAxis(Y, duck, currentY, duckList.get(0).getHeight());
				})
		);
		timeline.play();
		timelines.put(duck, timeline);
		return duck;
	}
	
	/**
	 * Updates the x-axis position of the duck based on the current position and screen boundaries.
	 *
	 * @param X        the array containing the x-axis velocity of the duck
	 * @param duck     the ImageView of the duck
	 * @param currentX the current x-coordinate of the duck
	 * @param duckWidth the width of pictures of ducks
	 */
	private void xAxis(double[] X, ImageView duck, double currentX, double duckWidth) {
		if (currentX >= (width / 2) - duckWidth * scale / 2 || currentX <= -(width / 2) + duckWidth * scale / 2) {
			X[0] = -1 * X[0];
			duck.setScaleX(-duck.getScaleX());
		}
		duck.setTranslateX(currentX + X[0]);
	}
	
	/**
	 * Updates the y-axis position of the duck based on the current position and screen boundaries.
	 *
	 * @param Y        the array containing the x-axis velocity of the duck
	 * @param duck     the ImageView of the duck
	 * @param currentY the current x-coordinate of the duck
	 * @param duckHeight the height of pictures of ducks
	 */
	private void yAxis(double[] Y, ImageView duck, double currentY, double duckHeight) {
		if (currentY >= (height / 2) - duckHeight *scale / 2 || currentY <= -(height / 2) + duckHeight *scale/ 2) {
			Y[0] = -1 * Y[0];
			duck.setScaleY(-duck.getScaleY());
		}
		duck.setTranslateY(currentY + Y[0]);
	}
	
	/**
	 * Creates a duck that flies across the screen.
	 *
	 * @param duckList the list of duck images
	 * @param x        the initial x-coordinate of the duck
	 * @param y        the initial y-coordinate of the duck
	 * @return the image view of the duck
	 */
	private ImageView createDuck(ArrayList<Image> duckList, int x, int y, int index) {
		ImageView duck = new ImageView(duckList.get(index));
		duck.setScaleX(scale);
		duck.setScaleY(scale);
		duck.setTranslateX(x);
		duck.setTranslateY(y);
		return duck;
	}
	
	/**
	 * Stops the currently playing music and sound effects.
	 * This method stops the MediaPlayer instances for the game music, duck fall sound effect, and gunshot sound effect.
	 * If any of the MediaPlayer instances is not null, it is stopped using the stop() method.
	 */
	void stopMusic(ArrayList<MediaPlayer> medias) {
		for (MediaPlayer mediaPlayer : medias) {
			if (mediaPlayer != null) {
				mediaPlayer.stop();
			}
		}
	}
	
	/**
	 * Proceeds to the next level in the Duck Hunt game.
	 * This method is called when the player completes a level and needs to progress to the next level.
	 * The implementation of this method should handle the necessary actions for transitioning to the next level,
	 * such as updating the level number, duck count, and other game elements.
	 * Subclasses of the GameManagement class should provide their own implementation of this method.
	 */
	abstract void nextLvl();
}
