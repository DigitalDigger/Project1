import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import javax.swing.JOptionPane;

public class TetrisGUI extends JPanel
        implements KeyListener, Cloneable {
    private int ROWS;
    private int COLS;
    private Board board;
    public Color darkOrange = new Color(241, 238, 244);
    public Color newColor = new Color(128, 183, 180);
    public Color darkBlue = new Color(188, 133, 250);
    public Color darkBlack = new Color(168, 209, 215);
    public Color lightBlack = new Color(164, 233, 244);
    public Color manlyBlue = new Color(134, 130, 210);
    public Color fragileMasculinity = new Color(114, 50, 114);
    public Color Pinkie = new Color(29, 143, 150);
    public Color Pixie = new Color(255, 179, 179);
    public Color Rainbow = new Color(182, 7, 107);
    public Color Unicorn = new Color(192, 101, 158);
    public Color Hidden = new Color(12, 101, 158);

    public TetrisGUI(Board inBoard) {
        board = inBoard;
        ROWS = board.getHeight();
        COLS = board.getWidth();
        addKeyListener(this);
    }

    SquareRx[][] squares;

    public void paintSquares() {
        if (squares == null)
            initSquares();
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                if (board.board.get(new Coordinate(i, j)).getType() == 0)
                    squares[j][i].bgColor = this.darkOrange;
                else if (board.board.get(new Coordinate(i, j)).getType() == 1)
                    squares[j][i].bgColor = this.darkBlue;
                else if (board.board.get(new Coordinate(i, j)).getType() == 2)
                    squares[j][i].bgColor = this.darkBlack;
                else if (board.board.get(new Coordinate(i, j)).getType() == 3)
                    squares[j][i].bgColor = this.lightBlack;
                else if (board.board.get(new Coordinate(i, j)).getType() == 4)
                    squares[j][i].bgColor = this.manlyBlue;
                else if (board.board.get(new Coordinate(i, j)).getType() == 5)
                    squares[j][i].bgColor = this.fragileMasculinity;
                else if (board.board.get(new Coordinate(i, j)).getType() == 6)
                    squares[j][i].bgColor = this.Pinkie;
                else if (board.board.get(new Coordinate(i, j)).getType() == 7)
                    squares[j][i].bgColor = this.Unicorn;
                else if (board.board.get(new Coordinate(i, j)).getType() == 8)
                    squares[j][i].bgColor = Color.magenta;
                else if (board.board.get(new Coordinate(i, j)).getType() == 9)
                    squares[j][i].bgColor = this.Pixie;
                else if (board.board.get(new Coordinate(i, j)).getType() == 10)
                    squares[j][i].bgColor = this.Rainbow;
                else if (board.board.get(new Coordinate(i, j)).getType() == 11)
                    squares[j][i].bgColor = this.newColor;
                else if (board.board.get(new Coordinate(i, j)).getType() == 12)
                    squares[j][i].bgColor = this.Hidden;

                repaint();
            }
        }
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (squares == null)
            initSquares();
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                squares[j][i].draw(g2);
            }
        }
    }

    private void initSquares() {
        squares = new SquareRx[ROWS][COLS];
        int w = getWidth();
        int h = getHeight();
        double xInc = (double) (w) / COLS;
        double yInc = (double) (h) / ROWS;

        for (int i = 0; i < ROWS; i++) {
            double y = i * yInc;
            for (int j = 0; j < COLS; j++) {
                double x = j * xInc;
                Rectangle2D.Double r =
                        new Rectangle2D.Double(x, y, xInc, yInc);
                squares[i][j] = new SquareRx(i, j, r);
            }
        }
    }

    /**
     * Handle the key typed event from the text field.
     */
    public void keyTyped(KeyEvent e) {

        repaint();
    }

    /**
     * Handle the key pressed event from the text field.
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            board.currentPosition = board.moveLeft(board.currentPosition, board.activeTetris);
        }

        if (key == KeyEvent.VK_RIGHT) {
            board.currentPosition = board.moveRight(board.currentPosition, board.activeTetris);
        }

        if (key == KeyEvent.VK_UP) {
            Tetris rotatedTetris = board.activeTetris.Rotate();
            board.removePentomino(board.currentPosition, board.activeTetris);
            if (!board.checkCollision(board.currentPosition, rotatedTetris)) {
                // board.printBoard();
                board.fillPentomino(board.currentPosition, rotatedTetris, 1);
                board.activeTetris = rotatedTetris;
            } else
                board.fillPentomino(board.currentPosition, board.activeTetris, 1);

        }

        if (key == KeyEvent.VK_DOWN) {
            board.currentPosition = board.moveDown(board.currentPosition, board.activeTetris);
        }

        if (key == KeyEvent.VK_SPACE) {
            Coordinate currentPosition = board.currentPosition.clone();
            Coordinate previousPosition = new Coordinate(currentPosition.getX(), currentPosition.getY() - 1);

            while (previousPosition.getY() != currentPosition.getY()) {
                previousPosition = currentPosition.clone();
                currentPosition = board.moveDown(board.currentPosition, board.activeTetris);
            }
            if (board.checkGameOver()) {
                JOptionPane.showMessageDialog(null, "Your score is: " + board.score, "Game Over", JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            }
            board.checkAndRemoveFullLine();

            board.generateTetris();

            board.printBoard();
        }

        repaint();
    }

    /**
     * Handle the key released event from the text field.
     */
    public void keyReleased(KeyEvent e) {
        repaint();
    }

}

class SquareRx {

    Rectangle2D.Double rect;
    Color color = new Color(0, 0, 0);
    Color bgColor = Color.lightGray;

    public SquareRx(int r, int c, Rectangle2D.Double rect) {
        this.rect = rect;
    }

    public void draw(Graphics2D g2) {
        g2.setPaint(bgColor);
        g2.fill(rect);
        g2.setPaint(color);
        g2.draw(rect);
    }
}