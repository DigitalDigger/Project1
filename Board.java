import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;




public class Board extends Highscore {
    private HashMap<Coordinate, CellValues> board = new HashMap<Coordinate, CellValues>();
    private int width = 0;
    private int height = 0;
    private Coordinate currentPosition;
    private Coordinate currentFuturePosition;
    private static Tetris activeTetris;
    private static Tetris futureTetris;
    private HashMap<Tetris, ArrayList<Tetris>> pentominos;
    private int pentoCheck = 0;
    private JFrame gameOverFrame;
    private JTextField nameGetter;
    public boolean gameOver=false;
    public Highscore highscore = new Highscore();
    public FuturePentominoBoard futurePentominoBoard = new FuturePentominoBoard(5,5);
    FallingTimer FallingEvent = new FallingTimer(this, 2000);
    DropEvent DroppingEvent = new DropEvent(this,10);

    public Tetris getActiveTetris() {
        return activeTetris;
    }
    public boolean dropIt = false;

    public Tetris setActiveTetris(Tetris newTetris) {
        activeTetris = newTetris;
        return activeTetris;
    }


    public HashMap<Coordinate, CellValues> getBoard() {
        return board;
    }

    public Coordinate getCurrentPosition() {
        return currentPosition;
    }

    public Coordinate setCurrentPosition(Coordinate newCoordinate) {
        currentPosition = newCoordinate;
        return currentPosition;

    }

    Board(int curWidth, int curHeight) {
        width = curWidth;
        height = curHeight;

        for (int y = 0; y < curHeight; y++) {
            for (int x = 0; x < curWidth; x++) {
                board.put(new Coordinate(x, y), new CellValues(0, 0));
            }
        }
    }

    public void setZeros() {
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                board.put(new Coordinate(x, y), new CellValues(0, 0));
            }
        }
    }


    public boolean checkCollision(Coordinate cell, Tetris curTetris) {

        /* Looping through all the current pentomino coordinates and checking for collissions & outside the board exceptions  */
        for (Coordinate curCoord : curTetris.getCoords()) {

            /* check if our pentomino is going to be placed outside of the board */
            if (cell.getY() + curCoord.getY() >= getHeight() || cell.getY() < 0 || cell.getX() + curCoord.getX() >= getWidth() || cell.getX() + curCoord.getX() < 0)
                return true;

            /* Let's check if there are collissions :) */
            try {
                // if (board.get(new Coordinate(cell.x, cell.y)).geMatrixValue() + board.get(new Coordinate(curCoord.getX() + cell.x, curCoord.getY() + cell.y)).geMatrixValue() > 1)
                if (board.get(new Coordinate(curCoord.getX() + cell.getX(), curCoord.getY() + cell.getY())).getMatrixValue() == 1)
                    return true;

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return false;
    }

    public void checkAndRemoveFullLine() {
        int fullLinesCounter = 0;
        for (int a = 0; a < getHeight(); a++) {
            int counter = 0;
            for (int b = 0; b < getWidth(); b++) {
                if (board.get(new Coordinate(b, a)).getMatrixValue() == 0) {
                    break;
                } else {
                    counter++;
                }
                if (counter == getWidth()) {
                    fullLinesCounter++;
                    //System.out.println("FULL LINE on row " + a);
                    removeFullLine(a);
                    shiftEmptyLines(a);
                    break;
                }
            }
        }
        highscore.calculateScore(fullLinesCounter);
       // System.out.println(fullLinesCounter + " Full line detected");
        //System.out.println("Your score is now: " + highscore.getCurrentScore());
    }

    public void removeFullLine(int lineNumber) {
        for (int b = 0; b < getWidth(); b++) {
            board.put(new Coordinate(b, lineNumber), new CellValues(0, 0));
            int c = b + 1;
            //System.out.println("REMOVE FULL LINE STEP: " + c);
            //printBoard();
        }
    }

    public void shiftEmptyLines(int lineNumber) {
        for (int a = lineNumber; a > 0; a--) {
            for (int b = 0; b < getWidth(); b++) {
                int type = 0;
                int value = 0;
                if (board.get(new Coordinate(b, a - 1)) != null) {
                    type = board.get(new Coordinate(b, a - 1)).getType();
                    value = board.get(new Coordinate(b, a - 1)).getMatrixValue();
                }


                board.put(new Coordinate(b, a - 1), new CellValues(0, 0));
                board.put(new Coordinate(b, a), new CellValues(value, type));

                int c = b + 1;
                // System.out.println("SHIFT LINES STEP: " + c);
                //printBoard();

            }

        }
    }


    public void printBoard() {

        //Method to print the pentominoes
        for (int a = 0; a < getHeight(); a++) {
            for (int b = 0; b < getWidth(); b++) {
                System.out.print(board.get(new Coordinate(b, a)).getMatrixValue() + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }
    public void printFuturePentomino() {
        String futurePentominoArray = futureTetris.toString();
        System.out.println(futurePentominoArray);
        //Method to print the pentominoes
        for (int a = 0; a < getHeight(); a++) {
            for (int b = 0; b < getWidth(); b++) {
                //System.out.print(futurePentominoBoard.get(new Coordinate(b, a)).getMatrixValue() + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void  fillPentomino(Coordinate cell, Tetris curTetris, int toAdd) {

        for (Coordinate curCoord : curTetris.getCoords()) {

            board.put(new Coordinate(curCoord.getX() + cell.getX(), curCoord.getY() + cell.getY()), new CellValues(toAdd, curTetris.getType()));

        }
    }

    public void removePentomino(Coordinate cell, Tetris curTetris) {

        for (Coordinate curCoord : curTetris.getCoords()) {

            board.put(new Coordinate(curCoord.getX() + cell.getX(), curCoord.getY() + cell.getY()), new CellValues(0, 0));

        }
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Tetris generatePentomino(HashMap<Tetris, ArrayList<Tetris>> pentominos) {

        Tetris chosen = null;
        int cntr = 0;
        int random = (int) (Math.random() * 12);
        for (Map.Entry<Tetris, ArrayList<Tetris>> entry : pentominos.entrySet()) {
            if (random == cntr) {
                entry.getKey();
                int random2 = (int) (Math.random() * entry.getValue().size());
                chosen = entry.getValue().get(random2);
            }
            cntr++;
        }
        return chosen;
    }

    public Coordinate moveLeft(Coordinate curCoord, Tetris curPento) {
        removePentomino(curCoord, curPento);
        curCoord.setX(curCoord.getX() - 1);
        if (checkCollision(curCoord, curPento))
            curCoord.setX(curCoord.getX() + 1);
        fillPentomino(curCoord, curPento, 1);

        return new Coordinate(curCoord.getX(), curCoord.getY());
    }

    public Coordinate moveRight(Coordinate curCoord, Tetris curPento) {
        removePentomino(curCoord, curPento);
        curCoord.setX(curCoord.getX() + 1);
        if (checkCollision(curCoord, curPento))
            curCoord.setX(curCoord.getX() - 1);
        fillPentomino(curCoord, curPento, 1);

        return new Coordinate(curCoord.getX(), curCoord.getY());
    }

    public Coordinate moveDown(Coordinate curCoord, Tetris curPento, boolean spacePressed) {
        if(!gameOver);
        checkGameOver();
        if (checkCollision(curCoord, curPento)) {
            removePentomino(curCoord, curPento);
            curCoord.setY(curCoord.getY() + 1);
            //printBoard();
            if (checkCollision(curCoord, curPento)){
                curCoord.setY(curCoord.getY() - 1);
                fillPentomino(curCoord, curPento, 1);
                if(!spacePressed)
                dropIt = true;
            }
            else{
                fillPentomino(curCoord, curPento, 1);
            }

        }
            return new Coordinate(curCoord.getX(), curCoord.getY());

    }
    public void dropDown(){
        Coordinate currentPosition = getCurrentPosition().clone();
        Coordinate previousPosition = new Coordinate(currentPosition.getX(), currentPosition.getY() - 1);

        while (previousPosition.getY() != currentPosition.getY()) {
            previousPosition = currentPosition.clone();
            currentPosition = moveDown(getCurrentPosition(), getActiveTetris(),true);
        }
        checkAndRemoveFullLine();
        generateTetris();
        checkGameOver();
        // board.printBoard();
    }
    public void checkGameOver() {
        if (gameOver) {
            System.out.println("game over");
            FallingEvent.cancel();
            DroppingEvent.cancel();

            gameOverFrame = new JFrame("Game Over!");
            gameOverFrame.setLocationRelativeTo(null);
            gameOverFrame.setResizable(false);
            JPanel gameOverPanel = new JPanel(new FlowLayout());
            nameGetter = new JTextField(12);
            JButton gameOverButton = new JButton("Submit");
            GameOverListener listener = new GameOverListener();
            gameOverButton.addActionListener(listener);
            gameOverPanel.add(nameGetter);
            gameOverPanel.add(gameOverButton);
            gameOverFrame.add(gameOverPanel);
            gameOverFrame.pack();
            gameOverFrame.setVisible(true);

        }
    }

    class GameOverListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            String name = nameGetter.getText();
            int score = highscore.getScore();
            gameOverFrame.dispose();
            Highscore newScore = new Highscore(score, name);
            newScore.read();
            newScore.add(newScore);
            newScore.sortList();
            newScore.write();

            if(highscore.getScore()>= highscore.getHighscoreNumberByRank(6)){
                if(highscore.getScore()>=highscore.getHighscoreNumberByRank(1)){
                    JOptionPane.showMessageDialog(null, "New Highscore! 1st Place! Your score is: " + highscore.getScore(), "1st Place!", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    for(int i = 2; i<=5; i++){
                        if(highscore.getScore()>=highscore.getHighscoreNumberByRank(i)) {
                            JOptionPane.showMessageDialog(null, "New Highscore! " + i + "th Place! Your score is: " + highscore.getScore(), "New Highscore", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                    }
                }

            }
            else{
                JOptionPane.showMessageDialog(null, "No new Highscore :( Your score is: " + highscore.getScore(), "Game Over", JOptionPane.WARNING_MESSAGE);
            }


            System.exit(0);
        }
        }

    public void generateTetris() {
        Coordinate firstPosition = new Coordinate((getWidth() / 2), 0);
        currentPosition = firstPosition;
        Coordinate futurePentominoPosition = new Coordinate((getWidth() / 2), 0);
        currentFuturePosition = futurePentominoPosition;
        System.out.println("INITIAL POSITION   X: " + futurePentominoPosition.getX() + "  " + "Y: " + futurePentominoPosition.getY());

            /*
        while (futurePentominoPosition.getX() != 0) {
            futurePentominoPosition.pushLeft();
            System.out.println("TO THE LEFT   X: " + futurePentominoPosition.getX() + "  " + "Y: " + futurePentominoPosition.getY());
            currentFuturePosition = futurePentominoPosition;

        }

        while (futurePentominoPosition.getX() != getWidth()) {
            futurePentominoPosition.pushRight();
            System.out.println("TO THE RIGHT   X: " + futurePentominoPosition.getX() + "  " + "Y: " + futurePentominoPosition.getY());
            currentFuturePosition = futurePentominoPosition;

        }
        */
        System.out.println("FINAL   X: " + futurePentominoPosition.getX() + "  " + "Y: " + futurePentominoPosition.getY());
        if (pentoCheck == 0) {
            Tetris aTetris = generatePentomino(pentominos);
            Tetris fTetris = generatePentomino(pentominos);
            futureTetris = fTetris;
            activeTetris = aTetris;
            pentoCheck++;
        }
        else {
            activeTetris=futureTetris;
            Tetris fTetris = generatePentomino(pentominos);
            futureTetris = fTetris;
            futurePentominoBoard.setZeros();
            //futurePentominoBoard.removePentomino(futurePentominoPosition,activeTetris);
        }
        if(futureTetris.getType()==1){
            futurePentominoPosition.pushLeft();
            futurePentominoPosition.pushLeft();
        }
        if(futureTetris.getType()==3){
            futurePentominoPosition.pushLeft();
        }
        if(futureTetris.getType()==2){
            futurePentominoPosition.pushLeft();
        }
        if(futureTetris.getType()==10){
            futurePentominoPosition.pushLeft();
        }
        futurePentominoBoard.fillPentomino(futurePentominoPosition, futureTetris, 1);




        //while (true) {
        //Tetris tetris = generatePentomino(pentominos);


       // futurePentominoBoard.removePentomino(firstPosition,activeTetris);
        futurePentominoBoard.printBoard();
        //futureTetris = tetris;
        //printFuturePentomino();
        if (!checkCollision(firstPosition, activeTetris)) {
            fillPentomino(firstPosition, activeTetris, 1);
            return;
        }


        while (firstPosition.getX() != 0) {
            firstPosition.pushLeft();
            currentPosition = firstPosition;
            if (!checkCollision(firstPosition, activeTetris)) {
                fillPentomino(firstPosition, activeTetris, 1);
                return;
            }
        }

        while (firstPosition.getX() != getWidth()) {
            firstPosition.pushRight();
            currentPosition = firstPosition;
            if (!checkCollision(firstPosition, activeTetris)) {
                fillPentomino(firstPosition, activeTetris, 1);
                return;
            }
        }
        if(checkCollision(firstPosition, activeTetris)) {
            System.out.println("Game Over");
            gameOver = true;

        }
        // }
    }

    public static void main(String[] args) throws Exception {

        /* Set the size of the board here */
        int heightBoard = 12;
        int widthBoard = 5;

        /* create object board */

        Board board = new Board(widthBoard, heightBoard);

        /***********************************/
        /* PUT ALL THE PENTOMINOS IN A MAP */
        /***********************************/

        board.pentominos = new HashMap<Tetris, ArrayList<Tetris>>();
        ArrayList<Tetris> basicShapes = new ArrayList<Tetris>();

        Tetris f = new Tetris(12, 0, 1, 0, 2, 1, 0, 1, 1, 2, 1, true, true);
        basicShapes.add(f);
        Tetris l = new Tetris(2, 0, 0, 1, 0, 2, 0, 3, 0, 3, 1, true, true);
        basicShapes.add(l);
        Tetris n = new Tetris(3, 0, 1, 1, 0, 1, 1, 2, 0, 3, 0, true, true);
        basicShapes.add(n);
        Tetris p = new Tetris(4, 0, 0, 0, 1, 1, 0, 1, 1, 2, 0, true, true);
        basicShapes.add(p);
        Tetris t = new Tetris(5, 0, 0, 0, 1, 0, 2, 1, 1, 2, 1, true, false);
        basicShapes.add(t);
        Tetris u = new Tetris(6, 0, 0, 0, 2, 1, 0, 1, 1, 1, 2, true, false);
        basicShapes.add(u);
        Tetris v = new Tetris(7, 0, 0, 1, 0, 2, 0, 2, 1, 2, 2, true, false);
        basicShapes.add(v);
        Tetris w = new Tetris(8, 0, 0, 1, 0, 1, 1, 2, 1, 2, 2, true, false);
        basicShapes.add(w);
        Tetris y = new Tetris(10, 0, 1, 1, 0, 1, 1, 2, 1, 3, 1, true, true);
        basicShapes.add(y);

        for (Tetris basic : basicShapes) {
            ArrayList<Tetris> Rotatable = new ArrayList<Tetris>();
            Rotatable.add(basic);
            if (basic.getRotatable()) {
                Tetris basicRot1 = basic.Rotate();
                Rotatable.add(basicRot1);
                Tetris basicRot2 = basicRot1.Rotate();
                Rotatable.add(basicRot2);
                Tetris basicRot3 = basicRot2.Rotate();
                Rotatable.add(basicRot3);
            }

            if (basic.getFlippable()) {
                Tetris flipped = basic.Flip();
                Rotatable.add(flipped);
                Tetris basicFlip1 = flipped.Rotate();
                Rotatable.add(basicFlip1);
                Tetris basicFlip2 = basicFlip1.Rotate();
                Rotatable.add(basicFlip2);
                Tetris basicFlip3 = basicFlip2.Rotate();
                Rotatable.add(basicFlip3);
            }

            board.pentominos.put(basic, Rotatable);
        }

        Tetris x = new Tetris(9, 0, 1, 1, 0, 1, 1, 1, 2, 2, 1, false, false);
        ArrayList<Tetris> xRots = new ArrayList<Tetris>();
        xRots.add(x);
        board.pentominos.put(x, xRots);

        Tetris i = new Tetris(1, 0, 0, 1, 0, 2, 0, 3, 0, 4, 0, false, false);
        Tetris iRot1 = i.Rotate();
        ArrayList<Tetris> iRots = new ArrayList<Tetris>();
        iRots.add(i);
        iRots.add(iRot1);
        board.pentominos.put(i, iRots);

        Tetris z = new Tetris(11, 0, 0, 0, 1, 1, 1, 2, 1, 2, 2, false, false);
        Tetris zRot1 = z.Rotate();
        ArrayList<Tetris> zRots = new ArrayList<Tetris>();
        zRots.add(z);
        zRots.add(zRot1);
        Tetris zRot2 = z.Flip();
        zRots.add(zRot2);
        Tetris zRot3 = zRot2.Rotate();
        zRots.add(zRot3);
        board.pentominos.put(z, zRots);


        /* Pentomino Visualisation */
        TetrisGUI grid = new TetrisGUI(board);

        grid.setFocusable(true);

        JFrame j = new JFrame();
        //j.setResizable(false);
        j.add(grid);

        j.setSize(board.getWidth() * 120 + 400, board.getHeight() * 80);
        j.setVisible(true);
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        board.highscore.init();
        board.generateTetris();
        Coordinate zero = new Coordinate(0, 0);

        while(true)
        {
            // Improve processor load
            long starttime = System.currentTimeMillis();
            while(System.currentTimeMillis()-starttime < 50)
            {
            }
            grid.paintSquares();
            grid.paintFuturePentomino();
        }
    }


}
