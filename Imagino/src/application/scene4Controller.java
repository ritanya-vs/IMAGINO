package application;

import javax.*;
import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class scene4Controller implements Initializable{
	@FXML
	private ImageView imageView;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Image image1 = new Image(new File("D:/media_javaproj/thankyou_page.png").toURI().toString());
		imageView.setImage(image1);
	}
	
	public void email() throws Exception{
		TextInputDialog emailDialog = new TextInputDialog();
	    emailDialog.setTitle("Email Input");
	    emailDialog.setHeaderText("Enter your email address:");
	    emailDialog.setContentText("Email:");
	    Optional<String> emailResult = emailDialog.showAndWait();
	    
	    emailResult.ifPresent(email -> {
	    	try
	    	{
	    		Properties properties = new Properties();
	    		properties.put("mail.smtp.auth",true);
	    		properties.put("mail.smtp.host","smtp.gmail.com");
	    		properties.put("mail.smtp.port", 587);
	    		properties.put("mail.smtp.starttls.enable",true);
	    		properties.put("mail.transport.protocl", "smtp");
	    		
	    		Session session = Session.getInstance(properties,new Authenticator() {
	    			@Override
	    			protected PasswordAuthentication getPasswordAuthentication() {
	    				return new PasswordAuthentication("rsnp175@gmail.com","gjzl aiop qmwa eyad");
	    			}
	    		});
	    		
	    		Message message = new MimeMessage(session);
	    		message.setSubject("IMAGINO");
	    		
	    		Address addressTo = new InternetAddress(email);
	    		message.setRecipient(Message.RecipientType.TO, addressTo);
	    		
	    		MimeMultipart multipart = new MimeMultipart();
	    		
	    		attachFileIfExists(multipart, "D:/captured_image.jpg");
                attachFileIfExists(multipart, "D:/img_gray.jpg");
                attachFileIfExists(multipart, "D:/img_negative.jpg");
                attachFileIfExists(multipart, "D:/img_rgb.jpg");
                attachFileIfExists(multipart, "D:/img_sepia.jpg");
	    		//MimeBodyPart attachment = new MimeBodyPart();
	    		//attachment.attachFile(new File("D:/captured_image.jpg"));
	    		
	    		MimeBodyPart messageBodyPart = new MimeBodyPart();
	    		messageBodyPart.setContent("<h1>Images </h1>","text/html");
	    		multipart.addBodyPart(messageBodyPart);
	    		//multipart.addBodyPart(attachment);
	    		
	    		message.setContent(multipart);
	    		
	    		Transport.send(message);
	    		
	    		showSuccessAlert();
                
                // Terminate the program
                System.exit(0);
	    	}catch (Exception e) {
	            e.printStackTrace();}
	    	
	    	
	    });
	}
	
	private void showSuccessAlert() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Email sent successfully!");
        alert.showAndWait();
    }
	
	private void attachFileIfExists(MimeMultipart multipart, String filePath) throws Exception {
        File file = new File(filePath);
        if (file.exists()) {
            MimeBodyPart attachment = new MimeBodyPart();
            attachment.attachFile(file);
            multipart.addBodyPart(attachment);
        }
    }
	
	public void close() {
		System.exit(0);
	}
}
