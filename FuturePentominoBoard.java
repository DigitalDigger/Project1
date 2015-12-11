import java.util.HashMap;

public class FuturePentominoBoard {
    private HashMap<Coordinate, CellValues> board = new HashMap<Coordinate, CellValues>();
    private int width = 5;
    private int height = 5;

    public HashMap<Coordinate, CellValues> getBoard() {
        return board;
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


    public void fillPentomino(Coordinate cell, Tetris curTetris, int toAdd) {

        for (Coordinate curCoord : curTetris.getCoords()) {

            board.put(new Coordinate(curCoord.getX() + cell.getX(), curCoord.getY() + cell.getY()), new CellValues(toAdd, curTetris.getType()));

        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


}



