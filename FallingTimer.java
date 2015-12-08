import java.util.Timer;
import java.util.TimerTask;
import java.util.*;

public class FallingTimer extends TimerTask {
    Timer timer;
    public int seconds;
    public Board board;

    public FallingTimer(Board board, int seconds) {
        this.seconds = seconds;
        this.board = board;
        timer = new Timer();
        timer.schedule(this, seconds);
    }

    public void run() {
        FallingTimer timer2;
        if(!board.dropIt)
        board.moveDown(board.getCurrentPosition(), board.getActiveTetris(), false);


        timer.cancel();

        if (board.highscore.getCurrentScore() < 10)
            timer2 = new FallingTimer(board, 3000);
        else if ((10 <= board.highscore.getCurrentScore()) && (board.highscore.getCurrentScore() < 20))
            timer2 = new FallingTimer(board, 2500);
        else if ((20 <= board.highscore.getCurrentScore()) && (board.highscore.getCurrentScore() < 30))
            timer2 = new FallingTimer(board, 2000);
        else if ((30 <= board.highscore.getCurrentScore()) && (board.highscore.getCurrentScore() < 40))
            timer2 = new FallingTimer(board, 1500);
        else if ((40 <= board.highscore.getCurrentScore()) && (board.highscore.getCurrentScore() < 50))
            timer2 = new FallingTimer(board, 1000);
        else
            timer2 = new FallingTimer(board, 500);


    }
}

	
	
	
	
	