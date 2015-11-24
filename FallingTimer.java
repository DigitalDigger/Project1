import java.util.Timer;
import java.util.TimerTask;
import java.util.*;

public class FallingTimer extends TimerTask {
	Timer timer;
    public int seconds;
    
    
	public Board board;
	
	public FallingTimer(Board boardx, int x){
		seconds = x;
		board = boardx;
		timer = new Timer();
		timer.schedule(this , x);
	}
	
	 public void run() {
    	board.moveDown(board.currentPosition, board.activeTetris);
    	timer.cancel();
    FallingTimer timer2;
    int x = board.getScore();

    	if ( x < 10 )
    		 timer2= new FallingTimer(board, 3000) ;
    	else if ( (10 <= x ) && (x< 20 ) )
    		 timer2= new FallingTimer(board, 2500) ;
    	else if ( (20 <= x) && (x < 30) )
    		 timer2= new FallingTimer(board, 2000) ;
    	 else if ( (30 <= x) && (x < 40) )
    		 timer2= new FallingTimer(board, 1500) ;
    	 else if ( (40 <= x) && (x < 50) )
    		 timer2= new FallingTimer(board, 1000) ;
    	 else 
    		 timer2= new FallingTimer(board, 500) ;
    	
       }
}
	
	
	
	
	
	