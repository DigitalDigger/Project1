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
    private String name="";

    public boolean boardToCurrentPosition=false;
    public boolean botEnabled = true;
    public boolean botDropIt = false;
    public int[][] botArray = new int[20][3];
    FallingTimer FallingEvent = new FallingTimer(this, 2000);
    DropEvent DroppingEvent = new DropEvent(this,10);
    BotEvents BotDroppingEvent = new BotEvents(this,10);

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
    public int[][] boardToArray() {
        int[][] boardArray = new int[getHeight()][getWidth()];
        for (int b = 0; b < getWidth(); b++) {
        for (int a = 0; a < getHeight(); a++) {

                boardArray[a][b]=board.get(new Coordinate(b, a)).getMatrixValue();
            }

        }
        return boardArray;
    }
    public int[][] boardToArrayreverse() {
        int[][] boardArray = new int[getWidth()][getHeight()];

        for (int a = 0; a < getHeight(); a++) {
            for (int b = 0; b < getWidth(); b++) {
                boardArray[b][a]=board.get(new Coordinate(b, a)).getMatrixValue();
            }

        }
        return boardArray;
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
    public Coordinate moveUp(Coordinate curCoord, Tetris curPento, boolean spacePressed) {

        if (checkCollision(curCoord, curPento)) {
            removePentomino(curCoord, curPento);
            curCoord.setY(curCoord.getY() - 1);
            if (checkCollision(curCoord, curPento)){
                curCoord.setY(curCoord.getY() + 1);
                fillPentomino(curCoord, curPento, 1);
            }
            else{
                fillPentomino(curCoord, curPento, 1);
            }
        }

        return new Coordinate(curCoord.getX(), curCoord.getY());

    }
    public void botDropDown(){
        Coordinate previousPosition = new Coordinate(currentPosition.getX(), currentPosition.getY() - 1);

        while (previousPosition.getY() != currentPosition.getY()) {
            previousPosition = currentPosition.clone();
            currentPosition = moveDown(getCurrentPosition(), getActiveTetris(),true);
        }
        System.out.println("dropped");
        printBoard();
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
        if(botEnabled)
           tetrisBot();
        // board.printBoard();
    }
    public void checkGameOver() {
        if (gameOver) {
            FallingEvent.cancel();
            DroppingEvent.cancel();
            BotDroppingEvent.cancel();
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
        public void actionPerformed(ActionEvent e) {

            gameOverFrame.dispose();
            if(botEnabled)
                name="bot";
            else
            name = nameGetter.getText();
            int score = highscore.getScore();
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
public void tetrisBot(){
    if(!gameOver) {
        printBoard();
        int counter = 0;
        for (int i = 0; i < getWidth(); i++) {
            for (int k = 0; k < 4; k++) {
                botDropIt = true;

                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int[][] arrayBoard = boardToArray();

                botArray[counter][0] = calculatePositionScore(arrayBoard);
                botArray[counter][1] = i;
                botArray[counter][2] = k;

                boardToCurrentPosition = true;

                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                rotateActiveTetris();
                counter++;
            }
            moveActiveTetrisToRight();

        }

        for (int z = 0; z < getWidth(); z++) {
            moveActiveTetrisToLeft();
        }
        int[] executeArray = new int[3];
        int maxScore = -999999999;
        for (int h = 0; h < botArray.length; h++) {

            if (maxScore < botArray[h][0]) {
                maxScore = botArray[h][0];
                executeArray[0] = maxScore;
                executeArray[1] = botArray[h][1];
                executeArray[2] = botArray[h][2];
            }

        }
        System.out.println("let's start the bot");

        for (int v = 0; v < executeArray[1]; v++) {
            moveActiveTetrisToRight();
        }

        for (int c = 0; c < executeArray[2]; c++) {
            rotateActiveTetris();
        }

        dropDown();
    }
}

/*****************************************/
/*** BOT SCORE CALCULATION COMES HERE ***/
/***************************************/
public int getHeightScore(int[][] bArray) {

    int height = 0;
    for (int j = 0; j < bArray[0].length; j++)       {
        for(int i = 0; i < bArray.length; i++)       {
            if(bArray[i][j] != 0)    {
                height += (bArray.length - i);
            }
        }
    }
    return height;
}

    public int getHolesScore(int[][] bArray) {

        int holes = 0;
        for(int i = bArray.length - 1; i > 0 ; i--)      {
            for (int j = 0; j < bArray[0].length; j++)   {
                if(bArray[i][j] == 0 && bArray[i-1][j] != 0)      {

                    holes++;
                }
            }
        }


        return holes;
    }

    public int getFullRowsScore(int[][] bArray){
        int a;
        int bRows = 0;
        for(int i = 0;i<bArray.length;i++){
            a=0;
            for(int j = 0;j<bArray[0].length;j++){
                if(bArray[i][j] != 0){
                    a++;
                }
            }
            if(a==bArray[0].length){
                bRows++;
            }
        }
        return bRows;
    }

    public int getMonotoneScore(int[][] bArray) {
        int a=0;
        int sum=0;
        int counter = 0;
        int[]array = new int[bArray[0].length+1];
        for (int i = 0; i < bArray[0].length; i++) {
            for (int j = 0; j < bArray.length; j++) {
                if (bArray[j][i] != 0) {
                    counter=bArray.length-j;
                    a++;
                    array[a]=counter;
                    counter=0;
                    break;

                }

            }
        }


        for(int k=0; k<array.length-1;k++) {
            sum += Math.abs(array[k] - array[k + 1]);
        }

        return sum;
    }

    public int calculatePositionScore(int[][] bArray) {
        int height= getHeightScore(bArray);
        int holes = getHolesScore(bArray);
        int fullrows = getFullRowsScore(bArray);
        int monotone = getMonotoneScore(bArray);

        int w1 = -26;   //height
        int w2 = -86;   //holes
        int w3 = 60;    //rows
        int w4 = -20;    //montone



        int sum = height*w1 + holes*w2  + fullrows*w3 + monotone*w4;

        return sum;
    }

    public void generateTetris() {
        Coordinate firstPosition = new Coordinate((getWidth() / 2), 0);

        if(botEnabled){
            firstPosition = new Coordinate(0, 0);
        }

        currentPosition = firstPosition;
        Coordinate futurePentominoPosition = new Coordinate((getWidth() / 2), 0);
        currentFuturePosition = futurePentominoPosition;
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
        if(botEnabled);


        futurePentominoBoard.fillPentomino(futurePentominoPosition, futureTetris, 1);

        if (!checkCollision(currentPosition, activeTetris)) {
            fillPentomino(currentPosition, activeTetris, 1);
            return;
        }

        while (firstPosition.getX() != 0) {
            firstPosition.pushLeft();
            currentPosition = firstPosition;
            if (!checkCollision(currentPosition, activeTetris)) {
                fillPentomino(currentPosition, activeTetris, 1);
                return;
            }
        }

        while (firstPosition.getX() != getWidth()) {
            firstPosition.pushRight();
            currentPosition = firstPosition;
            if (!checkCollision(currentPosition, activeTetris)) {
                fillPentomino(currentPosition, activeTetris, 1);
                return;
            }
        }



        if(checkCollision(currentPosition, activeTetris)) {
            System.out.println("Game Over");
            gameOver = true;
        }

    }
    public void rotateActiveTetris(){
        Tetris rotatedTetris = getActiveTetris().Rotate();
        removePentomino(getCurrentPosition(), getActiveTetris());
        if (!checkCollision(getCurrentPosition(), rotatedTetris)) {
            // board.printBoard();
            fillPentomino(getCurrentPosition(), rotatedTetris, 1);
            setActiveTetris(rotatedTetris);
        } else
            fillPentomino(getCurrentPosition(), getActiveTetris(), 1);

        System.out.println("rotated");
        printBoard();

    }
    public void moveActiveTetrisToRight() {
        setCurrentPosition(moveRight(getCurrentPosition(), getActiveTetris()));

        System.out.println("moved to the right");
        printBoard();
    }
    public void moveActiveTetrisToLeft() {
        setCurrentPosition(moveLeft(getCurrentPosition(), getActiveTetris()));

        System.out.println("moved to the right");
        printBoard();
    }
    public Coordinate moveActiveTetrisDown(Coordinate curCoord, Tetris curPento, boolean spacePressed) {

        if (checkCollision(curCoord, curPento)) {
            removePentomino(curCoord, curPento);
            curCoord.setY(curCoord.getY() + 1);
            if (checkCollision(curCoord, curPento)){
                curCoord.setY(curCoord.getY() - 1);
                fillPentomino(curCoord, curPento, 1);
            }
            else{
                fillPentomino(curCoord, curPento, 1);
            }

        }
        return new Coordinate(curCoord.getX(), curCoord.getY());

    }


    public static void main(String[] args) throws Exception {

        /* Set the size of the board here */
        int heightBoard = 12;
        int widthBoard = 5;

        /* create object board */

        Board board = new Board(widthBoard, heightBoard);
        FuturePentominoBoard fBoard = new FuturePentominoBoard(5,5);

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
        TetrisGUI grid = new TetrisGUI(board,fBoard);

        grid.setFocusable(true);

        JFrame j = new JFrame();
        //j.setResizable(false);
        j.add(grid);
        j.setSize(board.getWidth() * 120 + 400, board.getHeight() * 80);
        j.setVisible(true);
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        board.highscore.init();
        board.generateTetris();
        if(board.botEnabled)
            board.tetrisBot();


        while(true)
        {
            // Improve processor load
            long starttime = System.currentTimeMillis();
            while(System.currentTimeMillis()-starttime < 50) {
            }

            grid.paintSquares();
            //grid.paintFuturePentomino();

        }

    }


}
