package morabaraba;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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

		messageL = new Label("Player 1s Turn");

		GridPane buttonsGP = new GridPane();

		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				gameBoardB[i][j] = new Button("" + i + j);
				gameBoardB[i][j].setOnAction(this);
				gameBoardB[i][j].setUserData("" + i + j);

				if (j == 1)
				{
					buttonsGP.add(gameBoardB[i][j], 3, i);
				} else if (j == 3)
				{
					buttonsGP.add(gameBoardB[i][j], 6 - i, 3);
				} else if (j == 5)
				{
					buttonsGP.add(gameBoardB[i][j], 3, 6 - i);
				} else if (j == 7)
				{
					buttonsGP.add(gameBoardB[i][j], i, 3);
				} else if (j == 0)
				{
					buttonsGP.add(gameBoardB[i][j], i, i);
				} else if (j == 2)
				{
					buttonsGP.add(gameBoardB[i][j], 6 - i, i);
				} else if (j == 4)
				{
					buttonsGP.add(gameBoardB[i][j], 6 - i, 6 - i);
				} else if (j == 6)
				{
					buttonsGP.add(gameBoardB[i][j], i, 6 - i);
				}
			}
		}

		root.setCenter(buttonsGP);

		resetB = new Button("Reset");
		resetB.setOnAction(this);

		root.setTop(resetB);

		root.setBottom(messageL);

		primaryStage.show();
	}

	@Override
	public void handle(ActionEvent ev)
	{
		if(ev.getSource() == resetB)
		{
			//TODO call reset method and switch color
		}
		for(int i = 0; i< 3; i++) {
			for(int j = 0; j< 8; j++) {
				if(ev.getSource() == gameBoardB[i][j])
				{
					try {
					//TODO call button method
					//TODO call get space owner update color based off of value returned
						System.out.println("This Button has been pressed " + i + j);
					}catch(Exception ex)
					{
						
					}
				}
			}
		}
		messageL.setText(""); //TODO call get message
	}

}
