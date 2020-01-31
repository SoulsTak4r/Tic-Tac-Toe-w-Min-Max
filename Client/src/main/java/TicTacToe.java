import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


//Class for the Client front end GUI.
public class TicTacToe extends Application{
	//Defining Client class global variables.

	Client clientConnection;

	HashMap<String, Scene> sceneHashMap = new HashMap<String, Scene>();

	// ------------ Intro Scene Data --------------------
	BorderPane pane = new BorderPane();
	VBox vb = new VBox();


	Button startGame = new Button("Start Game");
	TextField getIp = new TextField("127.0.0.1");
	TextField getPort = new TextField("5555");


	Image img;

	//-------------- End Intro Data ----------------------

	// ------------- Difficulty Data ----------------------

	BorderPane pane1 = new BorderPane();
	VBox vb1 = new VBox();

	Text lbl = new Text("Select Difficulty");
	Button easy = new Button("Easy");
	Button medium = new Button("Medium");
	Button expert = new Button("Expert");

	//---------------End Difficulty Data -------------------


	// --------------- Game Data Scene -------------------

	BorderPane pane2 = new BorderPane();
	VBox vb2 = new VBox();
	HBox hb1 = new HBox();
	HBox hb2 = new HBox();
	HBox hb3 = new HBox();
	HBox hb4 = new HBox();


	Button row1Col1, row1Col2, row1Col3, row2Col1, row2Col2, row2Col3, row3Col1, row3Col2, row3Col3;



	Button playAgain  = new Button("Play Again");
	Button quit = new Button("Quit");

	ListView<String> topThree = new ListView<>();

	ArrayList<Button> buttons = new ArrayList<Button>();

	List<Button> names = Arrays.asList(row1Col1, row1Col2, row1Col3, row2Col1, row2Col2, row2Col3, row3Col1, row3Col2, row3Col3);

	int Wins = 0;
	Text numofWins = new Text("Your Wins: " + Wins);
	int port;
	VBox vbb = new VBox();
	Label lbl1 = new Label();


	//Method main.
	public static void main(String[] args) {
		launch(args);
	}//End of main method..


	//Method start to begin the GUI and stage.
	@Override
	public void start(Stage primaryStage) throws Exception {
		//Define the scene hash map.

		primaryStage.setTitle("Tic Tac Toe");

		topThree.getItems().add("Welcome To Tic Tac Toe");
		topThree.getItems().add("Click on any of the black boxed to start the game");
		topThree.getItems().add("Good Luck and have fun :)");
		topThree.getItems().add(" ");

		buttons.addAll(names);

		img = new Image("g.PNG");
		Image img2 = new Image("black1.PNG");

		BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
		pane.setBackground(new Background(new BackgroundImage( img,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,bSize)));
		pane1.setBackground(new Background(new BackgroundImage( img2,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,bSize)));

		sceneHashMap.put("Intro", intro());
		sceneHashMap.put("Diffi", difficulty());
		sceneHashMap.put("Game", game());
		// -------------- Intro Data Implementation ---------------

		getIp.setMaxHeight(100);
		getIp.setMaxWidth(100);
		getPort.setMaxHeight(100);
		getPort.setMaxWidth(100);
		startGame.setMaxWidth(80);
		startGame.setMaxHeight(40);


		startGame.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String ip = getIp.getText();
				port = Integer.parseInt(getPort.getText());

				try {
					clientConnection = new Client(ip, port, data -> {
						Platform.runLater(() -> {
							topThree.getItems().add(data);
						});
					});
					clientConnection.start();



					if (clientConnection.checkConnected == 1) {
						lbl1.setText("Failed to connect. Please check the status of the server");
					}

					primaryStage.setScene(sceneHashMap.get("Diffi"));
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

			}
		});

		// -------------- End Intro Implementation ------------------

		// ------------ Difficulty Implementation --------------------

		easy.setMaxWidth(80);
		easy.setMaxHeight(40);
		medium.setMaxWidth(80);
		medium.setMaxHeight(40);
		expert.setMaxWidth(80);
		expert.setMaxHeight(40);


		easy.setOnAction(e -> primaryStage.setScene(sceneHashMap.get("Game")));
		medium.setOnAction(e -> primaryStage.setScene(sceneHashMap.get("Game")));
		expert.setOnAction(e -> primaryStage.setScene(sceneHashMap.get("Game")));

		playAgain.setMaxHeight(40);
		playAgain.setMaxWidth(80);
		quit.setMaxHeight(40);
		quit.setMaxWidth(80);
		quit.setOnAction(e -> System.exit(0));
		quit.setStyle("-fx-background-color: orange");
		playAgain.setDisable(true);
		playAgain.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				topThree.setStyle("-fx-control-inner-background: white");

				for(int i = 0; i < buttons.size(); i++)
				{
					buttons.get(i).setText("");
					buttons.get(i).setId("b");;
					if(buttons.get(i).getId() == "b")
					{
						buttons.get(i).setDisable(false);
					}
				}
				playAgain.setDisable(true);
				clientConnection.gameInfo.currBoard = null;
				clientConnection.gameInfo.playerHasMadeMove = false;
				clientConnection.gameInfo.compNextMove = null;
				clientConnection.gameInfo.whoWon = null;
				topThree.getItems().add("You are playing AGAIN against Computer. GOOD LUCK!");
			}
		});


		//Create Buttons





		// ------------- End Difficulty Implementation --------------



		// --------------- Game Implementation ---------------


		numofWins.setFont(new Font("Arial", 20));
		numofWins.setStyle("-fx-fill: green");


		//-------------------------------------------------
		primaryStage.setScene(sceneHashMap.get("Intro"));

		primaryStage.show();




	}//End of method start().





	public Scene intro()
	{

		vb.getChildren().add(getIp);
		vb.getChildren().add(getPort);
		vb.getChildren().add(startGame);
		vb.getChildren().add(lbl1);
		vb.setAlignment(Pos.CENTER);
		vb.setSpacing(30);

		pane.setCenter(vb);

		return new Scene(pane, 600, 600);
	}


	public Scene game()
	{

		//pane2.setStyle("-fx-background-color: indigo");


		topThree.setMaxHeight(300);

		lbl1.setStyle("-fx-font-size: 15;" + "-fx-text-fill: RED;" + "-fx-font-weight: BOLD;");
		for(int i = 0; i < buttons.size(); i++)
		{
			buttons.set(i, getBtns());
		}
		hb1.getChildren().add(buttons.get(0));
		hb1.getChildren().add(buttons.get(1));
		hb1.getChildren().add(buttons.get(2));
		hb1.setSpacing(20);
		hb1.setAlignment(Pos.CENTER);
		//hb1.setPadding(new Insets(0, 0, 0, 0));

		hb2.getChildren().add(buttons.get(3));
		hb2.getChildren().add(buttons.get(4));
		hb2.getChildren().add(buttons.get(5));
		hb2.setSpacing(20);
		hb2.setAlignment(Pos.CENTER);
		//hb2.setPadding(new Insets(0, 0, 0, 0));

		hb3.getChildren().add(buttons.get(6));
		hb3.getChildren().add(buttons.get(7));
		hb3.getChildren().add(buttons.get(8));
		hb3.setSpacing(20);
		hb3.setAlignment(Pos.CENTER);
		//hb3.setPadding(new Insets(0, 0, 0, 0));

		hb4.getChildren().add(playAgain);
		hb4.getChildren().add(quit);
		hb4.setSpacing(35);
		hb4.setAlignment(Pos.CENTER);

		vb2.getChildren().add(hb4);
		vb2.getChildren().add(hb1);
		vb2.getChildren().add(hb2);
		vb2.getChildren().add(hb3);
		vb2.setAlignment(Pos.CENTER);
		vb2.setSpacing(30);

		vbb.getChildren().add(numofWins);
		vbb.setAlignment(Pos.CENTER_LEFT);
		vbb.setTranslateX(10);

		//numofWins.setStyle("-fx-font-size: 15;" + "-fx-fill: Green;" + "-fx-font-weight: BOLD;");



		pane2.setCenter(vb2);
		pane2.setBottom(topThree);
		pane2.setLeft(vbb);
		pane2.setTop(lbl1);


		return new Scene(pane2, 900, 900);


	}

	public Scene difficulty()
	{
		lbl.setStyle("-fx-font-size: 15;" + "-fx-fill: GOLD;" + "-fx-font-weight: BOLD;");
		vb1.getChildren().add(lbl);
		vb1.getChildren().add(easy);
		vb1.getChildren().add(medium);
		vb1.getChildren().add(expert);
		vb1.setAlignment(Pos.CENTER);
		vb1.setSpacing(20);

		pane1.setCenter(vb1);

		return new Scene(pane1, 900, 900);
	}





	public Button getBtns()
	{
		Button t = new Button();
		t.setMinSize(78, 87);
		t.setMaxSize(78, 87);
		t.setId("b");
		t.setText("");
		//t.setOnAction(boardclick);

		t.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String crBoard;
				Button sBoard;
				for(int i = 0; i < buttons.size(); i++)
				{
					buttons.get(i).setDisable(true);
					buttons.get(i).setOpacity(1);
				}

				sBoard = (Button) event.getSource();
				sBoard.setText("O");
				sBoard.setId("O");
				sBoard.setDisable(true);
				sBoard.setOpacity(1);
				crBoard = getBoard();

				clientConnection.gameInfo.currBoard = crBoard;
				clientConnection.gameInfo.playerHasMadeMove = true;
				clientConnection.send(clientConnection.gameInfo);

				//btn readable
				btnThread();
				//check winner
				checkwinner();
			}
		});

		t.setStyle("-fx-font-size: 34;" + "-fx-background-color: BLACK;" + "-fx-text-fill: WHITE;" + "-fx-font-weight: BOLD;");



		return t;

	}


	public void checkwinner()
	{
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (clientConnection.gameInfo.whoWon == null)
				{
					try {
						Thread.sleep(10);
					}
					catch (Exception e)
					{
						System.out.println("Error in checkwinner");
					}
				}
				if( clientConnection.gameInfo.whoWon.equals("Player"))
				{
					Wins++;
					Platform.runLater( () -> topThree.getItems().add("*** Congrats! You have beaten the computer Press Play Again to play ***"));

					Platform.runLater( () -> playAgain.setDisable(false));
					Platform.runLater( () -> disableBtns());
					Platform.runLater(() -> topThree.setStyle("-fx-control-inner-background: green"));

				}
				else if(clientConnection.gameInfo.whoWon.equals("Computer"))
				{
					Platform.runLater( () -> topThree.getItems().add("*** Opps! you have lost against Computer Press Play Again to play ***"));
					Platform.runLater( () -> playAgain.setDisable(false));
					Platform.runLater( () -> disableBtns());


					Platform.runLater(() -> topThree.setStyle("-fx-control-inner-background: red"));
				}
				else  if(clientConnection.gameInfo.whoWon.equals("Tie"))
				{
					Platform.runLater( () -> topThree.getItems().add("*** The game has been tied!!!... Press Play Again to play ***"));

					Platform.runLater( () -> playAgain.setDisable(false));
					Platform.runLater( () -> disableBtns());
					Platform.runLater(() -> topThree.setStyle("-fx-control-inner-background: lightgrey"));

				}
				clientConnection.gameInfo.whoWon = null;
			}
		}).start();
	}

	public void disableBtns()
	{
		for(int i = 0; i < buttons.size(); i++)
		{
			buttons.get(i).setDisable(true);
			buttons.get(i).setOpacity(1);
		}
	}


	public String getBoard()
	{
		String t = new String();

		t = buttons.get(0).getId();

		for (int i = 1; i < buttons.size(); i++)
		{
			t = t + " " + buttons.get(i).getId();
		}
		return t;
	}

	public void btnThread()
	{
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (clientConnection.gameInfo.compNextMove == null)
				{
					try {
						Thread.sleep(10);
					}
					catch (Exception e)
					{
						System.out.println("Error in btnThread");
						break;
					}
				}
				Platform.runLater( () -> {
					if(clientConnection.gameInfo.compNextMove != null)
					{
						// Display Server Moves
						displayMove();

						for(int i = 0; i < buttons.size(); i++)
						{
							if(buttons.get(i).getId() == "b")
							{
								buttons.get(i).setDisable(false);
							}
						}
					}
					clientConnection.gameInfo.compNextMove = null;
				});
			}
		}).start();
	}



	public void displayMove()
	{
		int m = Integer.parseInt(clientConnection.gameInfo.compNextMove);

		for(int i = 0; i < buttons.size(); i++)
		{
			if(m == i + 1)
			{
				buttons.get(i).setId("X");
				buttons.get(i).setText("X");
			}
		}
	}



}

