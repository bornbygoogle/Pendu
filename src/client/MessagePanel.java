package client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class MessagePanel extends Parent {
	
	private Label messageAttente;
	private ImageView screen;

	public MessagePanel(String message, Color couleur, int version) {
		
		Image image;
		try {
			if (version == 1) {
				image = new Image(new FileInputStream("./Images/win.png"));
				
				this.screen = new ImageView(image);
				this.screen.setLayoutX(280);
				this.screen.setLayoutY(100);
				this.screen.setFitHeight(300);
				this.screen.setFitWidth(150);
			} else if (version == 2) {
				image = new Image(new FileInputStream("./Images/etape7.png"));
				
				this.screen = new ImageView(image);
				this.screen.setLayoutX(50);
				this.screen.setLayoutY(100);
				this.screen.setFitHeight(550);
				this.screen.setFitWidth(400);
			} else {
				image = new Image(new FileInputStream("./Images/etape1.png"));

				this.screen = new ImageView(image);
				this.screen.setLayoutX(50);
				this.screen.setLayoutY(200);
				this.screen.setFitHeight(550);
				this.screen.setFitWidth(400);
			}
			
			this.getChildren().add(screen);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.messageAttente = new Label(message);
		this.messageAttente.setTextFill(couleur);
		this.messageAttente.setTextAlignment(TextAlignment.CENTER);
		this.messageAttente.setFont(Font.font(30));

		this.messageAttente.setLayoutX(250);
		this.messageAttente.setLayoutY(462.5);
		
		this.getChildren().add(this.messageAttente);
	}
}
