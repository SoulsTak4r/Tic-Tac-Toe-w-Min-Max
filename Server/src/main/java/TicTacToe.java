import java.io.Serializable;
import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.image.Image;

import java.util.HashMap;


public class TicTacToe extends Application {

    Server serverConnection;

    HashMap<String ,Scene> sceneHashMap = new HashMap<String, Scene>();


    // ----------- Intro Scene Data -------------
    BorderPane pane = new BorderPane();
    TextField getPort = new TextField("5555");
    Button startSv = new Button("Start Server");
    Button exit = new Button("Exit");
    VBox introVbox = new VBox();
    int portNum;
    Image img;
    Image img2;


    // ------------ end Intro Data ---------------


    // -------------- Actual Server Data -------------

    BorderPane pane2 = new BorderPane();
    ListView<String> serverLog = new ListView<>();
    Button exitSv = new Button("Exit");




    public static void main(String[] args) {
        // TODO Auto-generated method stub
        launch(args);
    }

    //feel free to remove the starter code from this method
    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
        primaryStage.setTitle("Let's Play Tic Tac Toe!!!");


        // ------------ Intro Data Implementation -----------------


        img = new Image("ticc.JPG");
        img2 = new Image("tic.JPG");

        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);

        pane.setBackground(new Background(new BackgroundImage( img,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,bSize)));
        pane2.setBackground(new Background(new BackgroundImage( img2,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,bSize)));


        getPort.setMaxWidth(100);
        getPort.setMaxHeight(100);
        startSv.setMaxWidth(80);
        startSv.setMaxHeight(40);
        exit.setMaxHeight(40);
        exit.setMaxWidth(80);
        exit.setStyle("-fx-background-color: orange");
        startSv.setStyle("-fx-background-color: lightgreen");

        portNum = Integer.parseInt(getPort.getText());

        exit.setOnAction(e -> System.exit(0));

        startSv.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(sceneHashMap.get("Server"));

                serverConnection = new Server(data -> {Platform.runLater(() -> {serverLog.getItems().add(data);});}, portNum);
                while (serverConnection.checkConnected == 0)
                {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        //---------------- End Intro Data --------------------


        // --------------- New Scene Data --------------------

        serverLog.setMaxHeight(300);
        serverLog.setMaxWidth(300);
        serverLog.setStyle("-fx-control-inner-background: lightgrey");

        exitSv.setMaxHeight(30);
        exitSv.setMinWidth(50);
        exitSv.setTranslateX(10);
        exitSv.setStyle("-fx-background-color: orange");

        exitSv.setOnAction(e -> System.exit(0));





        // --------------- End New Scene ----------------------

        sceneHashMap.put("Intro", intro());
        sceneHashMap.put("Server", serverScene());


        primaryStage.setScene(sceneHashMap.get("Intro"));
        primaryStage.show();
    }



    public Scene serverScene()
    {




        pane2.setCenter(serverLog);
        pane2.setLeft(exitSv);

        return new Scene(pane2, 600, 600);
    }

    public Scene intro()
    {
        introVbox.getChildren().add(getPort);
        introVbox.getChildren().add(startSv);
        introVbox.getChildren().add(exit);
        introVbox.setAlignment(Pos.CENTER);
        introVbox.setSpacing(30);

        pane.setCenter(introVbox);

        return new Scene(pane, 600, 600);
    }
}
