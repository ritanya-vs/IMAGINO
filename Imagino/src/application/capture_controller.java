package application;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class capture_controller implements Initializable {

		// the FXML button
		@FXML
		private Button button;
		// the FXML image view
		@FXML
		private ImageView currentFrame;
		
		// a timer for acquiring the video stream
		private ScheduledExecutorService timer;
		// the OpenCV object that realizes the video capture
		private VideoCapture capture = new VideoCapture();
		// a flag to change the button behavior
		private boolean cameraActive = false;
		// the id of the camera to be used
		private static int cameraId = 0;
		

		@FXML
		private MediaView mediaView;
		private File file;
		private Media media;
		private MediaPlayer mediaPlayer;
		
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
			String path = new File("src/media/capturing_video.mp4").getAbsolutePath();
			media = new Media(new File(path).toURI().toString());
			mediaPlayer = new MediaPlayer(media);
			mediaView.setMediaPlayer(mediaPlayer);
			mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
			mediaView.setPreserveRatio(false);
			//mediaView = new MediaView(mediaPlayer);
			mediaPlayer.setAutoPlay(true);
	    }
		@FXML
		protected void startCamera(ActionEvent event)
		{
			if (!this.cameraActive)
			{
				// start the video capture
				this.capture.open(cameraId);
				
				// is the video stream available?
				if (this.capture.isOpened())
				{
					this.cameraActive = true;
					
					// grab a frame every 33 ms (30 frames/sec)
					Runnable frameGrabber = new Runnable() {
						
						@Override
						public void run()
						{
							// effectively grab and process a single frame
							Mat frame = grabFrame();
							// convert and show the frame
							Image imageToShow = Utils.mat2Image(frame);
							updateImageView(currentFrame, imageToShow);
						}
					};
					
					this.timer = Executors.newSingleThreadScheduledExecutor();
					this.timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);
					
					// update the button content
					this.button.setText("Stop Camera");
				}
				else
				{
					// log the error
					System.err.println("Impossible to open the camera connection...");
				}
			}
			else
			{
				// the camera is not active at this point
				this.cameraActive = false;
				// update again the button content
				this.button.setText("Start Camera");
				
				// stop the timer
				this.stopAcquisition();
			}
		}
		
		/**
		 * Get a frame from the opened video stream (if any)
		 */
		private Mat grabFrame()
		{
			// init everything
			Mat frame = new Mat();
			
			// check if the capture is open
			if (this.capture.isOpened())
			{
				try
				{
					// read the current frame
					this.capture.read(frame);
					
					// if the frame is not empty, process it
					if (!frame.empty())
					{
						String saveDirectory = "D:/";
						String fileName = saveDirectory + "captured_image.jpg";
						Imgcodecs.imwrite(fileName, frame);
					}
					
				}
				catch (Exception e)
				{
					// log the error
					System.err.println("Exception during the image elaboration: " + e);
				}
			}
			
			return frame;
		}
		
		/**
		 * Stop the acquisition from the camera and release all the resources
		 */
		private void stopAcquisition()
		{
			if (this.timer!=null && !this.timer.isShutdown())
			{
				try
				{
					// stop the timer
					this.timer.shutdown();
					this.timer.awaitTermination(33, TimeUnit.MILLISECONDS);
				}
				catch (InterruptedException e)
				{
					// log any exception
					System.err.println("Exception in stopping the frame capture, trying to release the camera now... " + e);
				}
			}
			
			if (this.capture.isOpened())
			{
				// release the camera
				this.capture.release();
			}
		}
		
		/**
		 * Update the {@link ImageView} in the JavaFX main thread
		 */
		private void updateImageView(ImageView view, Image image)
		{
			Utils.onFXThread(view.imageProperty(), image);
		}
		
		/**
		 * On application close, stop the acquisition from the camera
		 */
		protected void setClosed()
		{
			this.stopAcquisition();
		}
		
		public void start(Stage primaryStage)
		{
			try
			{
				// load the FXML resource
				FXMLLoader loader = new FXMLLoader(getClass().getResource("capture.fxml"));
				// store the root element so that the controllers can use it
				BorderPane rootElement = (BorderPane) loader.load();
				// create and style a scene
				Scene scene = new Scene(rootElement, 800, 600);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				// create the stage with the given title and the previously created
				// scene
				primaryStage.setTitle("JavaFX meets OpenCV");
				primaryStage.setScene(scene);
				// show the GUI
				primaryStage.show();
				
				// set the proper behavior on closing the application
				capture_controller controller = loader.getController();
				primaryStage.setOnCloseRequest((new EventHandler<WindowEvent>() {
					public void handle(WindowEvent we)
					{
						controller.setClosed();
					}
				}));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		private Stage stage;
		private Scene scene;
		private Parent root;
		
		@FXML
		public void switchToScene3(ActionEvent event) throws IOException {
			root = FXMLLoader.load(getClass().getResource("scene3.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
		
	}

