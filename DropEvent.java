import java.util.Timer;
import java.util.TimerTask;

public class DropEvent extends TimerTask {
    Timer timer;
    public int seconds;
    public Board board;

    public DropEvent(Board board, int seconds) {
        this.seconds = seconds;
        this.board = board;
        timer = new Timer();
        timer.schedule(this, seconds);
    }

    public void run() {
        DropEvent timer3;

        if (board.dropIt){
            board.dropDown();
        board.dropIt = false;
    }
        
        
        timer.cancel();

        timer3 = new DropEvent(board, 10);
    }
}

	
	
	
	
	