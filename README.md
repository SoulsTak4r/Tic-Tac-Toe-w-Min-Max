# Tic-Tac-Toe-w-Min-Max

A server that plays Tic Tac Toe with each client that connects to that server.
The server will utilize the Min-Max algorithm to determine its moves as well as when the game has been won or tied.
At the end of each game, each client will be able to play again or quit.  



                                                    Design Description

The server class begins by creating a server on the port number entered by a user, this creates
a thread to wait for a client. There will be only 1 thread waiting. A new thread will be created
when a client connects to our server. Each client will be on it’s own server-client thread.
When the client code is run, it will open a window to connect to a server via port and IP. The
client will try to connect and start a new game once successful. The player will be prompted to
make a move as soon as a new game begins. Out and in streams are used here to transfer
information between the client and server.
The server and client also transfer information through the GameInfo class. This is a serialized
class and every game has an instance of GameInfo object. The data members in this class
keep track of moves, current board, and the winners of the game.
We have implemented min-max code in server project folder which will run from server side.
The min-max code here gathers the data from the board which we modified in a way that
instead of receiving the game board information from the terminal by scanning, the game board
is a string object that is passed from the server as a parameter of the AI_MinMax constructor.
