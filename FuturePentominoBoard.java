import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FuturePentominoBoard extends Highscore {
    private HashMap<Coordinate, CellValues> board = new HashMap<Coordinate, CellValues>();
    private int width = 5;
    private int height = 5;
    private Coordinate currentPosition;
    private static Tetris activeTetris;
    private static Tetris futureTetris;
    private HashMap<Tetris, ArrayList<Tetris>> pentominos;
    private int pentoCheck = 0;


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

    FuturePentominoBoard(int curWidth, int curHeight) {
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

    }



