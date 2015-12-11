import java.util.Timer;
import java.util.TimerTask;
import java.util.*;

public class FallingTimer extends TimerTask {
    Timer timer;
    public int seconds;
    private Board board;

    public FallingTimer(Board board, int seconds) {
        this.seconds = seconds;
        this.board = board;
        timer = new Timer();
        timer.schedule(this, seconds);
    }

    public void run() {
        FallingTimer timer2;
        if(!board.dropIt && !board.gameOver && !board.botEnabled)
        board.moveDown(board.getCurrentPosition(), board.getActiveTetris(), false);


        timer.cancel();

        if (board.highscore.getScore() < 10)
            timer2 = new FallingTimer(board, 3000);
        else if ((10 <= board.highscore.getScore()) && (board.highscore.getScore() < 20))
            timer2 = new FallingTimer(board, 2500);
        else if ((20 <= board.highscore.getScore()) && (board.highscore.getScore() < 30))
            timer2 = new FallingTimer(board, 2000);
        else if ((30 <= board.highscore.getScore()) && (board.highscore.getScore() < 40))
            timer2 = new FallingTimer(board, 1500);
        else if ((40 <= board.highscore.getScore()) && (board.highscore.getScore() < 50))
            timer2 = new FallingTimer(board, 1000);
        else
            timer2 = new FallingTimer(board, 500);


    }
}

	
	
	
	
	