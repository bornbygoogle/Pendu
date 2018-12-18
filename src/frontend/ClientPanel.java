package frontend;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.text.TextFlow;

public class ClientPanel extends Parent {
	protected ScrollPane scrollReceivedText;
	protected TextFlow receivedText;
	protected List letters;
	protected List alphabet;
	
	public ClientPanel() {
		alphabet = new ArrayList<Button>();
		
		for (int i=0; i<26; i++) {
			Button btn = new Button();
			if (i/9 == 0) {
				btn.setLayoutX(25 + i*50);
				btn.setLayoutY(550);
			} else if (i/9 == 1) {
				btn.setLayoutX(25 + (i-9)*50);
				btn.setLayoutY(600);
			} else {
				btn.setLayoutX(50 + (i-18)*50);
				btn.setLayoutY(650);
			}
			btn.setPrefWidth(50);
			btn.setPrefHeight(50);
			char lettre = (char)(i+65);
			String btnLettre = "" + lettre;
			btn.setText(btnLettre);
			
			this.getChildren().add(btn);
			
			letters = new ArrayList<>();

			scrollReceivedText = new ScrollPane();
			scrollReceivedText.setLayoutX(50);
			scrollReceivedText.setLayoutY(50);
			scrollReceivedText.setPrefWidth(400);
			scrollReceivedText.setPrefHeight(350);

			receivedText = new TextFlow();
			scrollReceivedText.setContent(receivedText);
			scrollReceivedText.vvalueProperty().bind(receivedText.heightProperty());
			
		}
		
		
		this.getChildren().add(scrollReceivedText);
		

	}
}
