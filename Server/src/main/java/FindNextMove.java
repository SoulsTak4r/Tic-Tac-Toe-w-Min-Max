public class FindNextMove {


    String userBoard;
    int computerMove;
    AI_MinMax mini;

    FindNextMove()
    {
        this.userBoard = null;
    }

    public void startMinMax()
    {
        mini= new AI_MinMax(userBoard);
        computerMove = 1;

        mini.clientInput = userBoard;

        mini.start();

        while (mini.isRunning != 1)
        {
            try {
                Thread.sleep(10);

            }
            catch (Exception e)
            {
                System.out.println("Error in AI_MINMAX");
            }
        }

        if(mini.bestMoves.size() != 0)
        {
            computerMove = mini.bestMoves.get(0).getMovedTo();
        }
        else
        {
            computerMove = -2;
        }
        System.out.println("In FindNextMove");
    }
}
