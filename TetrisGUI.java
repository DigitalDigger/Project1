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
    private Color darkOrange = new Color(241, 238, 244);
    private Color newColor = new Color(128, 183, 180);
    private Color darkBlue = new Color(188, 133, 250);
    private Color darkBlack = new Color(168, 209, 215);
    private Color lightBlack = new Color(164, 233, 244);
    private Color manlyBlue = new Color(134, 130, 210);
    private Color fragileMasculinity = new Color(114, 50, 114);
    private Color Pinkie = new Color(29, 143, 150);
    private Color Pixie = new Color(255, 179, 179);
    private Color Rainbow = new Color(182, 7, 107);
    private Color Unicorn = new Color(192, 101, 158);
    private Color Hidden = new Color(12, 101, 158);

    public TetrisGUI(Board inBoard) {
        board = inBoard;
        setSize(board.getWidth() * 100, board.getHeight());
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
                if (board.getBoard().get(new Coordinate(i, j)).getType() == 0)
                    squares[j][i].bgColor = this.darkOrange;
                else if (board.getBoard().get(new Coordinate(i, j)).getType() == 1)
                    squares[j][i].bgColor = this.darkBlue;
                else if (board.getBoard().get(new Coordinate(i, j)).getType() == 2)
                    squares[j][i].bgColor = this.darkBlack;
                else if (board.getBoard().get(new Coordinate(i, j)).getType() == 3)
                    squares[j][i].bgColor = this.lightBlack;
                else if (board.getBoard().get(new Coordinate(i, j)).getType() == 4)
                    squares[j][i].bgColor = this.manlyBlue;
                else if (board.getBoard().get(new Coordinate(i, j)).getType() == 5)
                    squares[j][i].bgColor = this.fragileMasculinity;
                else if (board.getBoard().get(new Coordinate(i, j)).getType() == 6)
                    squares[j][i].bgColor = this.Pinkie;
                else if (board.getBoard().get(new Coordinate(i, j)).getType() == 7)
                    squares[j][i].bgColor = this.Unicorn;
                else if (board.getBoard().get(new Coordinate(i, j)).getType() == 8)
                    squares[j][i].bgColor = Color.magenta;
                else if (board.getBoard().get(new Coordinate(i, j)).getType() == 9)
                    squares[j][i].bgColor = this.Pixie;
                else if (board.getBoard().get(new Coordinate(i, j)).getType() == 10)
                    squares[j][i].bgColor = this.Rainbow;
                else if (board.getBoard().get(new Coordinate(i, j)).getType() == 11)
                    squares[j][i].bgColor = this.newColor;
                else if (board.getBoard().get(new Coordinate(i, j)).getType() == 12)
                    squares[j][i].bgColor = this.Hidden;

                repaint();
            }
        }
    }

    public void setNextPentomino() {
        if (squares == null)
            initNextPentomino();
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                if (board.getBoard().get(new Coordinate(i, j)).getType() == 0)
                    squares[j][i].bgColor = this.darkOrange;
                else if (board.getBoard().get(new Coordinate(i, j)).getType() == 1)
                    squares[j][i].bgColor = this.darkBlue;
                else if (board.getBoard().get(new Coordinate(i, j)).getType() == 2)
                    squares[j][i].bgColor = this.darkBlack;
                else if (board.getBoard().get(new Coordinate(i, j)).getType() == 3)
                    squares[j][i].bgColor = this.lightBlack;
                else if (board.getBoard().get(new Coordinate(i, j)).getType() == 4)
                    squares[j][i].bgColor = this.manlyBlue;
                else if (board.getBoard().get(new Coordinate(i, j)).getType() == 5)
                    squares[j][i].bgColor = this.fragileMasculinity;
                else if (board.getBoard().get(new Coordinate(i, j)).getType() == 6)
                    squares[j][i].bgColor = this.Pinkie;
                else if (board.getBoard().get(new Coordinate(i, j)).getType() == 7)
                    squares[j][i].bgColor = this.Unicorn;
                else if (board.getBoard().get(new Coordinate(i, j)).getType() == 8)
                    squares[j][i].bgColor = Color.magenta;
                else if (board.getBoard().get(new Coordinate(i, j)).getType() == 9)
                    squares[j][i].bgColor = this.Pixie;
                else if (board.getBoard().get(new Coordinate(i, j)).getType() == 10)
                    squares[j][i].bgColor = this.Rainbow;
                else if (board.getBoard().get(new Coordinate(i, j)).getType() == 11)
                    squares[j][i].bgColor = this.newColor;
                else if (board.getBoard().get(new Coordinate(i, j)).getType() == 12)
                    squares[j][i].bgColor = this.Hidden;

                repaint();
            }

        }
    }

    public void paintComponent(Graphics g) {
        Rectangle2D scoreRectangle = new Rectangle2D.Double(585,0,400,78);
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (squares == null)
            initSquares();
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                if(squares!=null)
                squares[j][i].draw(g2);
            }
        }
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.DARK_GRAY);
        g2.fill(scoreRectangle);
        g2.setColor(Color.white);
        Font font = new Font("Serif", Font.PLAIN, 50);
        g2.setFont(font);
        g2.drawString("Your score is: " + board.highscore.getCurrentScore(), 620, 50);

    }

    private void initSquares() {
        squares = new SquareRx[ROWS][COLS];
        int w = getWidth() - 400;
        int h = getHeight();
        double xInc = (double) (w) / COLS;
        double yInc = (double) (h) / ROWS;

        for (int i = 0; i < ROWS; i++) {
            double y = i * yInc;
            for (int j = 0; j < COLS; j++) {
                double x = j * xInc;
                Rectangle2D.Double r =
                        new Rectangle2D.Double(x, y, xInc, yInc);
                squares[i][j] = new SquareRx(r);

            }
        }
    }

    private void initNextPentomino() {
        squares = new SquareRx[5][5];
        int w = 5;
        int h = 5;
        double xInc = (double) (w) / COLS;
        double yInc = (double) (h) / ROWS;

        for (int i = 0; i < ROWS; i++) {
            double y = i * yInc;
            for (int j = 0; j < COLS; j++) {
                double x = j * xInc;
                Rectangle2D.Double r =
                        new Rectangle2D.Double(x, y, xInc, yInc);
                squares[i][j] = new SquareRx(r);
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
            board.setCurrentPosition(board.moveLeft(board.getCurrentPosition(), board.getActiveTetris()));
        }

        if (key == KeyEvent.VK_RIGHT) {
            board.setCurrentPosition(board.moveRight(board.getCurrentPosition(), board.getActiveTetris()));
        }

        if (key == KeyEvent.VK_UP) {
            Tetris rotatedTetris = board.getActiveTetris().Rotate();
            board.removePentomino(board.getCurrentPosition(), board.getActiveTetris());
            if (!board.checkCollision(board.getCurrentPosition(), rotatedTetris)) {
                // board.printBoard();
                board.fillPentomino(board.getCurrentPosition(), rotatedTetris, 1);
                board.setActiveTetris(rotatedTetris);
            } else
                board.fillPentomino(board.getCurrentPosition(), board.getActiveTetris(), 1);

        }

        if (key == KeyEvent.VK_DOWN) {
            board.setCurrentPosition(board.moveDown(board.getCurrentPosition(), board.getActiveTetris()));
        }

        if (key == KeyEvent.VK_SPACE) {
            Coordinate currentPosition = board.getCurrentPosition().clone();
            Coordinate previousPosition = new Coordinate(currentPosition.getX(), currentPosition.getY() - 1);

            while (previousPosition.getY() != currentPosition.getY()) {
                previousPosition = currentPosition.clone();
                currentPosition = board.moveDown(board.getCurrentPosition(), board.getActiveTetris());
            }
            board.checkAndRemoveFullLine();
            if (board.checkGameOver()) {
                board.highscore.addCurrentScore();
                board.highscore.writeToHighscores();
                System.out.println("game over");

                if(board.highscore.getCurrentScore()> board.highscore.getHighscore(6)){
                    if(board.highscore.getCurrentScore()==board.highscore.getHighscore(1)){
                        JOptionPane.showMessageDialog(null, "New Highscore! 1st Place! Your score is: " + board.highscore.getCurrentScore(), "1st Place!", JOptionPane.WARNING_MESSAGE);
                        }
                    else {
                        for(int i = 2; i<=5; i++){
                            JOptionPane.showMessageDialog(null, "New Highscore! " + i + "th Place! Your score is: " + board.highscore.getCurrentScore(), "New Highscore", JOptionPane.WARNING_MESSAGE);
                                    break;
                        }
                    }

                }
                else{
                    JOptionPane.showMessageDialog(null, "No new Highscore :( Your score is: " + board.highscore.getCurrentScore(), "Game Over", JOptionPane.WARNING_MESSAGE);
                }


                System.exit(0);
            }


            board.generateTetris();

            // board.printBoard();
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

    public SquareRx(Rectangle2D.Double rect) {
        this.rect = rect;
    }

    public void draw(Graphics2D g2) {
        g2.setPaint(bgColor);
        g2.fill(rect);
        g2.setPaint(color);
        g2.draw(rect);
    }
}