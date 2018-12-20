package client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.Receiver;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class ClientPanel extends Parent {
	protected ImageView screen;
	protected List<TextFlow> letters;
	protected List<Button> alphabet;
	
	public ClientPanel() throws FileNotFoundException {
		alphabet = new ArrayList<Button>();
		
		for (int i=0; i<26; i++) {
			Button btn = new Button();
			if (i/9 == 0) {
				btn.setLayoutX(25 + i*50);
				btn.setLayoutY(650);
			} else if (i/9 == 1) {
				btn.setLayoutX(25 + (i-9)*50);
				btn.setLayoutY(700);
			} else {
				btn.setLayoutX(50 + (i-18)*50);
				btn.setLayoutY(750);
			}
			btn.setPrefWidth(50);
			btn.setPrefHeight(50);
			char lettre = (char)(i+65);
			String btnLettre = "" + lettre;
			btn.setText(btnLettre);
			
			alphabet.add(btn);
			this.getChildren().add(btn);
		}
		
		letters = new ArrayList<TextFlow>();
		
		for (int i=0; i<8; i++) {
			ScrollPane scrollReceivedText = new ScrollPane();
			scrollReceivedText.setLayoutX(50+i*50);
			scrollReceivedText.setLayoutY(550);
			scrollReceivedText.setPrefWidth(50);
			scrollReceivedText.setPrefHeight(50);
			scrollReceivedText.setMaxHeight(50);
			scrollReceivedText.setMaxWidth(50);
			
			TextFlow receivedText = new TextFlow();
			
			scrollReceivedText.setContent(receivedText);
			scrollReceivedText.vvalueProperty().bind(receivedText.heightProperty());
			
			letters.add(receivedText);
			this.getChildren().add(scrollReceivedText);
		}
		
		Image image = new Image(new FileInputStream("./Images/etape1.png"));
		screen = new ImageView(image);
		screen.setLayoutX(50);
		screen.setLayoutY(0);
		screen.setFitHeight(550);
		screen.setFitWidth(400);
		
		this.getChildren().add(screen);
		
		for (int i=0; i<alphabet.size(); i++) {
			alphabet.get(i).setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					letters.get(1).getChildren().clear();
					String button = event.getSource().toString();
					String label = button.substring(button.indexOf("'"));
					label = label.substring(1, 2);
					Text letter = new Text();
					letter.setText(label);
					letter.setTextAlignment(TextAlignment.CENTER);
					letter.setFont(Font.font("Helvetica", FontPosture.REGULAR, 40));
					letters.get(1).getChildren().add(letter);
					System.out.println(letter);
				}
				
			});
		}
		
	}
}
