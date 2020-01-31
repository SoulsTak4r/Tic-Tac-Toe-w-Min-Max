import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;
import javafx.application.Platform;
import javafx.scene.control.ListView;
import java.lang.Thread;
import java.io.IOException;


public class Server {

    FindNextMove findNextMove;

    int port;
    int checkConnected = 0;
    GameInfo getGameInfo;
    int counter;

    Consumer<String> callback;

    ArrayList<ClientThreads> numofClients = new ArrayList<ClientThreads>();

    newServer sv;


    Server(Consumer<String> accpetcall, int num)
    {
        sv = new newServer();
        this.callback = accpetcall;
        this.port = num;
        sv.start();

        findNextMove = new FindNextMove();
    }

    public class newServer extends Thread {

        public void run()
        {
            try(ServerSocket socket = new ServerSocket(port)) {
                callback.accept("Server is waiting for a Client");
                System.out.println("I am in inner server");
                checkConnected = 1;

                while (true)
                {
                    ClientThreads newClient = new ClientThreads(socket.accept(), counter);

                    numofClients.add(newClient);
                    callback.accept("Client " + counter + " is on the Server");

                    newClient.start();

                    counter++;


                }
            } catch (Exception e) {
                checkConnected = -1;
                e.printStackTrace();
            }
        }
    }

    public class ClientThreads extends Thread {

        Socket s;
        ObjectOutputStream out;

        int counter2;

        ObjectInputStream in;

        ClientThreads(Socket myS, int c)
        {
            this.s = myS;
            this.counter2 = c;
        }

        public void run()
        {
            try {
                out = new ObjectOutputStream(s.getOutputStream());
                in = new ObjectInputStream(s.getInputStream());

                getGameInfo = new GameInfo();
            }
            catch (Exception e)
            {
                System.out.println("Error Opening Stream");
                e.printStackTrace();
            }

            try {
                out.writeObject(getGameInfo);

            } catch (Exception e) {

                e.printStackTrace();
            }

            callback.accept("Client " + counter2 + " Sent");

            while (true)
            {
                try {
                    getGameInfo = (GameInfo) in.readObject();
                }
                catch (Exception e) {

                    break;
                }

                callback.accept("Client " + counter2 + " recieved");
                findNextMove.computerMove = -1;

                findNextMove.userBoard = getGameInfo.currBoard;
                getGameInfo.whoWon = winner(getGameInfo.currBoard, -1);

                if(getGameInfo.whoWon.equals("None"));
                {
                    synchronized (findNextMove)
                    {
                        findNextMove.startMinMax();
                    }

                    while (findNextMove.computerMove == -1)
                    {
                        try {
                            Thread.sleep(1);
                        }
                        catch (Exception e)
                        {
                            System.out.println("If == none in server");
                        }
                    }

                    if(findNextMove.computerMove > 0)
                    {
                        callback.accept("Computer Moves " + findNextMove.computerMove + " for " + " Client " + counter2);
                        getGameInfo.whoWon = winner(getGameInfo.currBoard, findNextMove.computerMove);

                    }
                    getGameInfo.compNextMove = Integer.toString(findNextMove.computerMove);
                }

                if(!getGameInfo.whoWon.equals("None"))
                {
                    callback.accept("Winner is " + getGameInfo.whoWon + " against Client " + counter2);

                }

                try {
                    out.writeObject(getGameInfo);
                }
                catch (Exception e)
                {
                    break;
                }
                callback.accept("Client " + counter2 + " Sent");
            }


        }
    }




    public String winner(String brd, int compMove)
    {
        int e= 0;


        String board = brd.replaceAll("\\s+", "");

        char[] brdArr = board.toCharArray();

        int[][] moves = new int[3][3];

        if(compMove != -1)
        {
            brdArr[compMove - 1] = 'X';
        }

        for(int i = 0; i < 3; i++)
        {
            for( int j = 0; j <3; j++)
            {
                if( brdArr[(i*3) + j] == 'b')
                {
                    moves[i][j] = 0;
                }
                else if(brdArr[(i*3) + j] == 'O')
                {
                    moves[i][j] = 1;
                }
                else if(brdArr[(i*3) + j] == 'X')
                {
                    moves[i][j] = -1;
                }
            }
        }

        for(int i = 0; i < 3; i++)
        {
            e = 0;
            for(int j = 0; j < 3; j++)
            {
                e+=moves[i][j];
            }
            if(e == -3 || e == 3)
            {
                return whosWinner(e);
            }
        }

        for(int i = 0; i < 3; i++)
        {
            e = 0;
            for(int j = 0; j < 3; j++)
            {
                e+=moves[j][i];
            }
            if(e == -3 || e == 3)
            {
                return whosWinner(e);
            }
        }

        e = 0;

        e = moves[0][0] + moves[1][1] + moves[2][2];
        if(e == -3 || e ==3)
        {
            return whosWinner(e);
        }

        e = 0;

        e = moves[0][2] + moves[1][1] + moves[2][0];
        if(e == -3 || e ==3)
        {
            return whosWinner(e);
        }

        for(int i = 0; i < brdArr.length; i++)
        {
            if(brdArr[i] == 'b')
            {
                return "None";
            }
        }

        return "Tie";


    }

    public String whosWinner(int c)
    {
        if (c == -3)
        {
            return "Computer";
        }
        else if(c == 3)
        {
            return "Player";
        }
        else
        {
            return "None";
        }
    }
}
