import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.*;


public class Board {
    private HashMap<Coordinate, CellValues> board = new HashMap<Coordinate, CellValues>();
    private int width = 0;
    private int height = 0;
    private Coordinate currentPosition;
    private Tetris activeTetris;
    private HashMap<Tetris, ArrayList<Tetris>> pentominos;
    private int score = 0;

    public int getScore(){
        return score;
    }
    

    public Tetris getActiveTetris(){
        return activeTetris;
    }

    public Tetris setActiveTetris(Tetris newTetris){
        activeTetris = newTetris;
        return activeTetris;
    }

    public HashMap<Coordinate, CellValues> getBoard(){
        return board;
    }

    public Coordinate getCurrentPosition(){
        return currentPosition;
    }

    public Coordinate setCurrentPosition(Coordinate newCoordinate){
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

    public int calculateScore(int currentScore, int lines) {
        int score;
        score = currentScore + (int) Math.pow(3, lines);
        return score;
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
                    System.out.println("FULL LINE on row " + a);
                    removeFullLine(a);
                    shiftEmptyLines(a);
                    break;
                }

            }
        }

        score = calculateScore(score, fullLinesCounter);
        System.out.println(fullLinesCounter + "Full lines detected");
        System.out.println("Your score is now: " + score);
    }

    public boolean checkGameOver() {
        boolean gameOver = false;


        for (int b = 0; b < getWidth(); b++) {
            if (board.get(new Coordinate(b, 0)).getMatrixValue() == 1) {
                gameOver = true;
                System.out.println("GAME OVER YOUR");
                System.out.println("YOUR SCORE IS: " + score);
                break;
            }
        }
        return gameOver;
    }

    public void removeFullLine(int lineNumber) {
        for (int b = 0; b < getWidth(); b++) {
            board.put(new Coordinate(b, lineNumber), new CellValues(0, 0));
            int c = b + 1;
            System.out.println("REMOVE FULL LINE STEP: " + c);
            printBoard();
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
                System.out.println("SHIFT LINES STEP: " + c);
                printBoard();

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

    public void fillPentomino(Coordinate cell, Tetris curTetris, int toAdd) {

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
        curCoord.setX(curCoord.getX()-1);
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

    public Coordinate moveDown(Coordinate curCoord, Tetris curPento) {
        removePentomino(curCoord, curPento);
        curCoord.setY(curCoord.getY()+1);
        if (checkCollision(curCoord, curPento))
            curCoord.setY(curCoord.getY() - 1);
        fillPentomino(curCoord, curPento, 1);
        // printBoard();

        return new Coordinate(curCoord.getX(), curCoord.getY());
    }

    public void generateTetris() {
        Coordinate firstPosition = new Coordinate((getWidth() / 2), 0);
        currentPosition = firstPosition;

        while (true) {
            Tetris tetris = generatePentomino(pentominos);
            activeTetris = tetris;

            if (!checkCollision(firstPosition, tetris)) {
                fillPentomino(firstPosition, tetris, 1);
                return;
            }
        }
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
        j.add(grid);

        j.setSize(board.getWidth() * 100, board.getHeight() * 100);
        j.setVisible(true);
        //j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        board.generateTetris();


        while (true) {
            grid.paintSquares();
            /***********************************/
            /*  ADD FALLING PENTOMINO HERE!!!  */
            /***********************************/
            
             FallingTimer timer = new FallingTimer(board, 2000);
        }
    }


}
