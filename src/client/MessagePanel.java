package client;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class MessagePanel extends Parent {
	
	private Label messageAttente;

	public MessagePanel(String message, Color couleur) {
		this.messageAttente = new Label(message);
		this.messageAttente.setTextFill(couleur);
		this.messageAttente.setTextAlignment(TextAlignment.CENTER);
		this.messageAttente.setFont(Font.font(30));

		this.messageAttente.setLayoutX(250);
		this.messageAttente.setLayoutY(462.5);
		
		this.getChildren().add(this.messageAttente);
	}
}
