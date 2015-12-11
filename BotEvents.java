import java.util.Timer;
import java.util.TimerTask;

public class BotEvents extends TimerTask {
    Timer timer;
    public int seconds;
    private Board board;

    public BotEvents(Board board, int seconds) {
        this.seconds = seconds;
        this.board = board;
        timer = new Timer();
        timer.schedule(this, seconds);
    }

    public void run() {
        BotEvents timer4;

        if (board.botDropIt && board.botEnabled){
            for(int i=0; i<board.getHeight(); i++){
                board.setCurrentPosition(board.moveActiveTetrisDown(board.getCurrentPosition(), board.getActiveTetris(), false));
                board.printBoard();
            }

            board.botDropIt = false;

          }
        if (board.boardToCurrentPosition && board.botEnabled){
            for(int i=0; i<board.getHeight(); i++){
                board.setCurrentPosition(board.moveUp(board.getCurrentPosition(), board.getActiveTetris(), false));
                board.printBoard();
            }

            board.boardToCurrentPosition = false;
        }


        timer.cancel();

        timer4 = new BotEvents(board, 20);
    }
}

	
	
	
	
	