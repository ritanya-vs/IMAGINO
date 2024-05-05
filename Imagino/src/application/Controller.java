package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class Controller implements Initializable{
	
	@FXML
	private MediaView mediaView;
	private File file;
	private Media media;
	private MediaPlayer mediaPlayer;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	

	@Override
	public void initialize(URL location , ResourceBundle resources) {
		// TODO Auto-generated method stub
		//String path = "C:/Users/admin/Desktop/Open_screen.mp4";
		String path = new File("src/media/Intro_video.mp4").getAbsolutePath();
		media = new Media(new File(path).toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		mediaView.setMediaPlayer(mediaPlayer);
		//mediaView = new MediaView(mediaPlayer);
		//mediaView.setFitWidth(stage.getWidth());
	    //mediaView.setFitHeight(stage.getHeight());
	    mediaView.setPreserveRatio(false);
		mediaPlayer.setAutoPlay(true);
	}	
	
	@FXML
	public void switchtoScene2(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
