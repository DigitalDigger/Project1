import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;

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

    public TetrisGUI(Board inBoard, FuturePentominoBoard fBoard) {
        board = inBoard;
        board.futurePentominoBoard = fBoard;
        setSize(board.getWidth() * 100, board.getHeight());
        ROWS = board.getHeight();
        COLS = board.getWidth();
        addKeyListener(this);
    }

    SquareRx[][] squaresNextPentomino;
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


            }
        }
        repaint();

    }

     public void paintFuturePentomino() {
        if (squaresNextPentomino == null)
            initNextPentomino();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                 if (board.futurePentominoBoard.getBoard().get(new Coordinate(i, j)).getType() == 0)
                    squaresNextPentomino[j][i].bgColor = this.darkOrange;
                 else if (board.futurePentominoBoard.getBoard().get(new Coordinate(i, j)).getType() == 1)
                    squaresNextPentomino[j][i].bgColor = this.darkBlue;
                else if (board.futurePentominoBoard.getBoard().get(new Coordinate(i, j)).getType() == 2)
                    squaresNextPentomino[j][i].bgColor = this.darkBlack;
                else if (board.futurePentominoBoard.getBoard().get(new Coordinate(i, j)).getType() == 3)
                    squaresNextPentomino[j][i].bgColor = this.lightBlack;
                else if (board.futurePentominoBoard.getBoard().get(new Coordinate(i, j)).getType() == 4)
                    squaresNextPentomino[j][i].bgColor = this.manlyBlue;
                else if (board.futurePentominoBoard.getBoard().get(new Coordinate(i, j)).getType() == 5)
                    squaresNextPentomino[j][i].bgColor = this.fragileMasculinity;
                else if (board.futurePentominoBoard.getBoard().get(new Coordinate(i, j)).getType() == 6)
                    squaresNextPentomino[j][i].bgColor = this.Pinkie;
                else if (board.futurePentominoBoard.getBoard().get(new Coordinate(i, j)).getType() == 7)
                    squaresNextPentomino[j][i].bgColor = this.Unicorn;
                else if (board.futurePentominoBoard.getBoard().get(new Coordinate(i, j)).getType() == 8)
                    squaresNextPentomino[j][i].bgColor = Color.magenta;
                else if (board.futurePentominoBoard.getBoard().get(new Coordinate(i, j)).getType() == 9)
                    squaresNextPentomino[j][i].bgColor = this.Pixie;
                else if (board.futurePentominoBoard.getBoard().get(new Coordinate(i, j)).getType() == 10)
                    squaresNextPentomino[j][i].bgColor = this.Rainbow;
                else if (board.futurePentominoBoard.getBoard().get(new Coordinate(i, j)).getType() == 11)
                    squaresNextPentomino[j][i].bgColor = this.newColor;
                else if (board.futurePentominoBoard.getBoard().get(new Coordinate(i, j)).getType() == 12)
                    squaresNextPentomino[j][i].bgColor = this.Hidden;

            }
        }
         repaint();

    }


    public void paintComponent(Graphics g) {
        Rectangle2D scoreRectangle = new Rectangle2D.Double(585,0,1000,1000);
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        if(!board.botEnabled) {
            g2.setColor(Color.DARK_GRAY);
            g2.fill(scoreRectangle);
            g2.setColor(Color.white);
            Font font = new Font("Serif", Font.PLAIN, 50);
            g2.setFont(font);
            g2.drawString("Your score is: " + board.highscore.getScore(), 620, 450);
            g2.drawLine(10, 20, 10, 20);
            g2.drawString("HighscoreList ", 620, 520);
            Font font2 = new Font("Serif", Font.PLAIN, 32);
            g2.setFont(font2);

            g2.drawString("1. " + board.highscore.getHighscoreByRank(1), 650, 580);
            g2.drawString("2. " + board.highscore.getHighscoreByRank(2), 650, 630);
            g2.drawString("3. " + board.highscore.getHighscoreByRank(3), 650, 680);
            g2.drawString("4. " + board.highscore.getHighscoreByRank(4), 650, 730);
            g2.drawString("5. " + board.highscore.getHighscoreByRank(5), 650, 780);
        }
       if (squares == null)
           initSquares();



        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                if(squares!=null && g2!=null)
                squares[j][i].draw(g2);
            }
        }
        if(!board.botEnabled) {
        if(squaresNextPentomino == null)
            initNextPentomino();
       for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if(squaresNextPentomino!=null && g2!=null) {
                    squaresNextPentomino[j][i].draw(g2);
                }
                }

            }
        }

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
        squaresNextPentomino = new SquareRx[5][5];
        int w = getWidth()-700;
        int h = getHeight()-700;
        double xInc = (double) (w) / 5 ;
        double yInc = (double) (h) / 5 ;

        for (int i = 0; i < 5; i++) {
            double y = i * yInc;
            for (int j = 0; j < 5; j++) {
                double x = j * xInc;
                Rectangle2D.Double r =
                        new Rectangle2D.Double((x+650), (y+50), xInc, yInc);
                squaresNextPentomino[i][j] = new SquareRx(r);
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
            board.rotateActiveTetris();
        }
        if (key == KeyEvent.VK_DOWN) {
            board.setCurrentPosition(board.moveDown(board.getCurrentPosition(), board.getActiveTetris(), false));
        }

        if (key == KeyEvent.VK_SPACE) {
            board.dropDown();
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