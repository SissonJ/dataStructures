package morabaraba;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Gui extends Application implements EventHandler<ActionEvent>
{
	
	private Button[][] gameBoardB;
	
	private Button resetB;
	
	private Label messageL;
	
	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 625, 500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Morabaraba");
		
		gameBoardB = new Button[3][8];
		
		messageL = new Label();
		
		resetB = new Button("Reset");
		resetB.setOnAction(this);
		
		root.setTop(resetB);
		
		root.setBottom(messageL);
		
		primaryStage.show();
		}

	@Override
	public void handle(ActionEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

}
