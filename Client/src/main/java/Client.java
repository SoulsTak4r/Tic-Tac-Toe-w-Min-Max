import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.function.Consumer;
import javafx.scene.control.ListView;
import java.util.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class Client extends Thread {


    TicTacToe i = new TicTacToe();
    Socket socket;

    String ip;
    int port;
    int checkConnected = 0;
    boolean playerHasMadeMove = false;

    ObjectInputStream in;
    ObjectOutputStream out;

    GameInfo gameInfo;

    Consumer<String> callback;


    Client(String ipA, int portN, Consumer<String> call)
    {
        this.ip = ipA;
        this.port = portN;
        this.callback = call;
    }

    public void run()
    {
        try{

            socket = new Socket();

            socket.connect(new InetSocketAddress(ip, port));
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream((socket.getInputStream()));
            gameInfo = rcvMsgs();


            checkConnected = 2;
            System.out.println("Connection Established");

            while (true)
            {
                try {
                    gameInfo = (GameInfo) in.readObject();
                }
                catch (Exception E)
                {
                    System.out.println("Error in Client");
                    break;
                }
                if(gameInfo.compNextMove != null)
                {
                    callback.accept("You have recieved Computer's Move: " + gameInfo.compNextMove);
                }
            }

        }
        catch (IOException e)
        {
            checkConnected = 1 ;
            System.out.println("Error in Connection in Client");
            e.printStackTrace();

        }

    }


    public GameInfo rcvMsgs()
    {
        GameInfo getGameInfo;

        try {
            getGameInfo = (GameInfo) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return getGameInfo;
    }

    public void send(GameInfo Object)
    {
        try {
            out.writeObject(Object);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}

