package morabaraba;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

	private Controler game;

	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		game = new Controler();

		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 300, 375);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Morabaraba");

		gameBoardB = new Button[3][8];

		messageL = new Label(game.getMessage());

		GridPane buttonsGP = new GridPane();

		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				gameBoardB[i][j] = new Button("" + i + j);
				gameBoardB[i][j].setOnAction(this);
				gameBoardB[i][j].setUserData("" + i + j);
				gameBoardB[i][j].setStyle("-fx-background-color: white; -fx-font-size: 2em");

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
		if (ev.getSource() == resetB)
		{
			game.reset();
		}
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				if (ev.getSource() == gameBoardB[i][j])
				{
					try
					{
						game.buttonPress(i, j);
					} catch (IllegalArgumentException ex)
					{
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("Error");
						alert.setContentText(ex.getMessage());
						alert.showAndWait();
					}
				}
			}
		}
		messageL.setText(game.getMessage()); // TODO call get message
		updateGameBoard();
	}

	private void updateGameBoard()
	{
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				if (game.getSpaceOwner(i, j) == 0)
				{
					gameBoardB[i][j].setStyle("-fx-background-color: white; -fx-font-size: 2em");
				} else if (game.getSpaceOwner(i, j) == 1)
				{
					gameBoardB[i][j].setStyle("-fx-background-color: red; -fx-font-size: 2em");
				} else if (game.getSpaceOwner(i, j) == 2)
				{
					gameBoardB[i][j].setStyle("-fx-background-color: blue; -fx-font-size: 2em");
				}
			}
		}
	}

}
