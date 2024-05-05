package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

public class scene3Controller implements Initializable {
	private Stage stage;
	private Scene scene;
	private Parent root;
	@FXML
	private ImageView imageview1;
	@FXML
	private ImageView imageview2;
	@FXML
	private ImageView imageview3;
	@FXML
	private ImageView imageview4;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Image image1 = new Image(new File("D:/media_javaproj/grey.jpg").toURI().toString());
		imageview1.setImage(image1);
		
		Image image2 = new Image(new File("D:/media_javaproj/negative.jpg").toURI().toString());
		imageview2.setImage(image2);
		
		Image image3 = new Image(new File("D:/media_javaproj/rgb.jpg").toURI().toString());
		imageview3.setImage(image3);
		
		Image image4 = new Image(new File("D:/media_javaproj/sepia.jpg").toURI().toString());
		imageview4.setImage(image4);
	}
	@FXML
	public void switchToScene3(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("scene3.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	public void switchToScene4(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("scene4.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	public void grayscale_pic1(ActionEvent event) throws IOException{
		BufferedImage img = null;
		File f = null;
		try {
            f = new File("D:/captured_image.jpg");
            img = ImageIO.read(f);
        } catch (IOException e) {
            System.out.println(e);
        }
		
		int width = img.getWidth();
        int height = img.getHeight();
        int[] pixels = img.getRGB(0, 0, width, height, null, 0, width);
        for (int i = 0; i < pixels.length; i++) {
        	 
            // Here i denotes the index of array of pixels
            // for modifying the pixel value.
            int p = pixels[i];
 
            int a = (p >> 24) & 0xff;
            int r = (p >> 16) & 0xff;
            int g = (p >> 8) & 0xff;
            int b = p & 0xff;
 
            // calculate average
            int avg = (r + g + b) / 3;
 
            // replace RGB value with avg
            p = (a << 24) | (avg << 16) | (avg << 8) | avg;
 
            pixels[i] = p;
        }
        img.setRGB(0, 0, width, height, pixels, 0, width);
        try {
            f = new File(
                    "D:/img_gray.jpg");
            ImageIO.write(img, "png", f);
        } catch (IOException e) {
            System.out.println(e);
        }
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Image Processed");
        alert.setHeaderText("Image processing was successful.");
        alert.setContentText("Do you want to process with any other effect?");
        ButtonType Button1 = new ButtonType("Yes");
        ButtonType Button2 = new ButtonType("No");
        //ButtonType cancelButtonType = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(Button1, Button2);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()){
        	if(result.get() == Button1){
            	switchToScene3(event);
            }
        	else if(result.get() == Button2) {
        		switchToScene4(event);
        	}
        }
	}
	
	@FXML
	public void negative(ActionEvent event) throws IOException
	{
		 BufferedImage img = null; 
	        File f = null; 
	  
	        // read image 
	        try { 
	            f = new File( 
	                "D:/captured_image.jpg"); 
	            img = ImageIO.read(f); 
	        } 
	        catch (IOException e) { 
	            System.out.println(e); 
	        } 
	  
	        // Get image width and height 
	        int width = img.getWidth(); 
	        int height = img.getHeight(); 
	  
	        // Convert to negative 
	        for (int y = 0; y < height; y++) { 
	            for (int x = 0; x < width; x++) { 
	                int p = img.getRGB(x, y); 
	                int a = (p >> 24) & 0xff; 
	                int r = (p >> 16) & 0xff; 
	                int g = (p >> 8) & 0xff; 
	                int b = p & 0xff; 
	  
	                // subtract RGB from 255 
	                r = 255 - r; 
	                g = 255 - g; 
	                b = 255 - b; 
	  
	                // set new RGB value 
	                p = (a << 24) | (r << 16) | (g << 8) | b; 
	                img.setRGB(x, y, p); 
	            } 
	        } 
	  
	        // write image 
	        try { 
	            f = new File( 
	                "D:/img_negative.jpg"); 
	            ImageIO.write(img, "jpg", f); 
	        } 
	        catch (IOException e) { 
	            System.out.println(e); 
	        } 
	        
	        Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("Image Processed");
	        alert.setHeaderText("Image processing was successful.");
	        alert.setContentText("Do you want to process with any other effect?");
	        ButtonType Button1 = new ButtonType("Yes");
	        ButtonType Button2 = new ButtonType("No");
	        //ButtonType cancelButtonType = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
	        alert.getButtonTypes().setAll(Button1, Button2);
	        Optional<ButtonType> result = alert.showAndWait();
	        if (result.isPresent()){
	        	if(result.get() == Button1){
	            	switchToScene3(event);
	            }
	        	else if(result.get() == Button2) {
	        		switchToScene4(event);
	        	}
	        }
	}
	
	
	@FXML
	public void rgb(ActionEvent event) throws IOException
	{
		BufferedImage img = null; 
        File f = null; 
  
        // read image 
        try { 
            f = new File( 
                "D:/captured_image.jpg"); 
            img = ImageIO.read(f); 
        } 
        catch (IOException e) { 
            System.out.println(e); 
        } 
  
        // get width and height 
        int width = img.getWidth(); 
        int height = img.getHeight(); 
  
        // convert to red image 
        for (int y = 0; y < height; y++) { 
            for (int x = 0; x < width; x++) { 
                int p = img.getRGB(x, y); 
  
                int a = (p >> 24) & 0xff; 
                int r = (p >> 16) & 0xff; 
  
                // set new RGB keeping the r 
                // value same as in original image 
                // and setting g and b as 0. 
                p = (a << 24) | (r << 16) | (0 << 8) | 0; 
  
                img.setRGB(x, y, p); 
            } 
        } 
  
        // write image 
        try { 
            f = new File( 
                "D:/img_rgb.jpg"); 
            ImageIO.write(img, "png", f); 
        } 
        catch (IOException e) { 
            System.out.println(e); 
        } 
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Image Processed");
        alert.setHeaderText("Image processing was successful.");
        alert.setContentText("Do you want to process with any other effect?");
        ButtonType Button1 = new ButtonType("Yes");
        ButtonType Button2 = new ButtonType("No");
        //ButtonType cancelButtonType = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(Button1, Button2);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()){
        	if(result.get() == Button1){
            	switchToScene3(event);
            }
        	else if(result.get() == Button2) {
        		switchToScene4(event);
        	}
        }
	}
	
	
	@FXML
	public void sepia(ActionEvent event) throws IOException
	{
		BufferedImage img = null; 
        File f = null; 
  
        // read image 
        try { 
            f = new File( 
                "D:/captured_image.jpg"); 
            img = ImageIO.read(f); 
        } 
        catch (IOException e) { 
            System.out.println(e); 
        } 
  
        // get width and height of the image 
        int width = img.getWidth(); 
        int height = img.getHeight(); 
  
        // convert to sepia 
        for (int y = 0; y < height; y++) { 
            for (int x = 0; x < width; x++) { 
                int p = img.getRGB(x, y); 
  
                int a = (p >> 24) & 0xff; 
                int R = (p >> 16) & 0xff; 
                int G = (p >> 8) & 0xff; 
                int B = p & 0xff; 
  
                // calculate newRed, newGreen, newBlue 
                int newRed = (int)(0.393 * R + 0.769 * G 
                                   + 0.189 * B); 
                int newGreen = (int)(0.349 * R + 0.686 * G 
                                     + 0.168 * B); 
                int newBlue = (int)(0.272 * R + 0.534 * G 
                                    + 0.131 * B); 
  
                // check condition 
                if (newRed > 255) 
                    R = 255; 
                else
                    R = newRed; 
  
                if (newGreen > 255) 
                    G = 255; 
                else
                    G = newGreen; 
  
                if (newBlue > 255) 
                    B = 255; 
                else
                    B = newBlue; 
  
                // set new RGB value 
                p = (a << 24) | (R << 16) | (G << 8) | B; 
  
                img.setRGB(x, y, p); 
            } 
        } 
  
        // write image 
        try { 
            f = new File( 
                "D:/img_sepia.jpg"); 
            ImageIO.write(img, "png", f); 
        } 
        catch (IOException e) { 
            System.out.println(e); 
        } 
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Image Processed");
        alert.setHeaderText("Image processing was successful.");
        alert.setContentText("Do you want to process with any other effect?");
        ButtonType Button1 = new ButtonType("Yes");
        ButtonType Button2 = new ButtonType("No");
        //ButtonType cancelButtonType = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(Button1, Button2);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()){
        	if(result.get() == Button1){
            	switchToScene3(event);
            }
        	else if(result.get() == Button2) {
        		switchToScene4(event);
        	}
        }
	}
	

}
