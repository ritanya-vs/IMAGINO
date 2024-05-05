package application;
	
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.MediaView;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
			//FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
			//Parent root = loader.load(); 
			//root.getChildren().add(mediaView);
			Scene scene = new Scene(root,800,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
			Mat mat = Mat.eye(3, 3, CvType.CV_8UC1);
			System.out.println("mat = "+mat.dump());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
