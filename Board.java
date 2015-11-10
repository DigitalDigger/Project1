import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Board  implements KeyListener {
    public HashMap<Coordinate,CellValues>  board = new HashMap<Coordinate, CellValues>();
    private int width = 0;
    private int height = 0;
    private long counter=0;
    private int successcounter=0;
    private int oneIsolatedCellCounter = 0;
    private int twoIsolatedCellCounter = 0;
    private boolean isSingleIsolatedCells = false;
    private boolean isDoubleIsolatedCells = false;
    private boolean isLeastCoordinateHeuristic = false;
    public Coordinate currentPosition;
    public Pentomino activePentomino;

    /** Handle the key typed event from the text field. */
    public void keyTyped(KeyEvent e) {

    }

    /** Handle the key pressed event from the text field. */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            currentPosition = moveLeft(currentPosition, activePentomino);
        }

        if (key == KeyEvent.VK_RIGHT) {
            currentPosition = moveRight(currentPosition, activePentomino);
        }

        if (key == KeyEvent.VK_UP) {
            currentPosition = moveUp(currentPosition, activePentomino);
        }

        if (key == KeyEvent.VK_DOWN) {
            currentPosition = moveDown(currentPosition, activePentomino);
        }

    }

    /** Handle the key released event from the text field. */
    public void keyReleased(KeyEvent e) {

    }


    Board(int curWidth, int curHeight, boolean inSingleIsoltedCells, boolean inDoubleIsolatedCells, boolean inLeastCoordinateHeuristic)
    {
        width = curWidth;
        height = curHeight;

        for (int y = 0; y < curHeight; y++) {
            for (int x = 0; x < curWidth; x++) {
                board.put(new Coordinate(x, y), new CellValues(0, 0));
            }
        }

        isSingleIsolatedCells = inSingleIsoltedCells;
        isDoubleIsolatedCells = inDoubleIsolatedCells;
        isLeastCoordinateHeuristic = inLeastCoordinateHeuristic;


    }


    public int getXMinStartCoordinate(Pentomino curPentomino){
        int min = getWidth();

        /* Looping through all the current pentomino coordinates and determine the lowest X value where Y=0  */
        for (Coordinate curCoord : curPentomino.coords) {

            if (curCoord.y == 0) {
                if (curCoord.x < min)
                    min = curCoord.x;
            }
        }
        return min;
    }

    public int getYMinStartCoordinate(Pentomino curPentomino){
        int min = getHeight();

        /* Looping through all the current pentomino coordinates and determine the lowest X value where Y=0  */
        for (Coordinate curCoord : curPentomino.coords) {

            if (curCoord.x == 0) {
                if (curCoord.y < min)
                    min = curCoord.y;
            }
        }
        return min;
    }


    public boolean checkCollision(Coordinate cell, Pentomino curPentomino) {

        /* Looping through all the current pentomino coordinates and checking for collissions & outside the board exceptions  */
        for (Coordinate curCoord : curPentomino.coords) {

            /* check if our pentomino is going to be placed outside of the board */
                if (cell.y + curCoord.y >= getHeight() || cell.y < 0 || cell.x + curCoord.x >= getWidth() || cell.x + curCoord.x < 0)
                    return true;

            /* Let's check if there are collissions :) */
                try {
                    // if (board.get(new Coordinate(cell.x, cell.y)).matrixValue + board.get(new Coordinate(curCoord.x + cell.x, curCoord.y + cell.y)).matrixValue > 1)
                    if (board.get(new Coordinate(curCoord.x + cell.x, curCoord.y + cell.y)).matrixValue == 1)
                        return true;

                } catch (Exception e) {
                    e.printStackTrace();
                }

        }

        return false;
    }

    public boolean checkIsolatedCells() {

        if (!isSingleIsolatedCells && !isDoubleIsolatedCells)
            return false;


        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
               if (board.get(new Coordinate(x, y)).matrixValue == 0) {
                   if ((x == getWidth() - 1 || board.get(new Coordinate(x + 1, y)).matrixValue == 1)
                       && (y == getHeight() - 1 || board.get(new Coordinate(x, y + 1)).matrixValue == 1)
                           && (y == 0 || board.get(new Coordinate(x, y - 1)).matrixValue == 1)
                           && ( x == 0 || board.get(new Coordinate(x - 1, y)).matrixValue == 1)
                           ) {
                       oneIsolatedCellCounter++;
                       //if (oneIsolatedCellCounter % 100000 == 0)
                       // System.out.println(oneIsolatedCellCounter + " single isolated cell found");
                       return true;
                   }
               }


                if ((board.get(new Coordinate(x, y)).matrixValue == 0
                        && x != getWidth() - 1
                        && board.get(new Coordinate(x + 1, y)).matrixValue == 0
                        && (x + 1 == getWidth() - 1 || board.get(new Coordinate(x + 2, y)).matrixValue == 1)
                        && (y == 0 || (board.get(new Coordinate(x, y - 1)).matrixValue == 1 && board.get(new Coordinate(x + 1, y - 1)).matrixValue == 1) )
                        && (x == 0 || (board.get(new Coordinate(x - 1, y)).matrixValue == 1))
                        && (y == getHeight() - 1 || (board.get(new Coordinate(x, y + 1)).matrixValue == 1 && board.get(new Coordinate(x + 1, y + 1)).matrixValue == 1)))

                        ||


                        (board.get(new Coordinate(x, y)).matrixValue == 0
                        && y != getHeight() - 1
                        && board.get(new Coordinate(x, y + 1)).matrixValue == 0
                        && (x == getWidth() - 1 || (board.get(new Coordinate(x + 1, y)).matrixValue == 1 && board.get(new Coordinate(x + 1, y + 1)).matrixValue == 1))
                        && (y == 0 || (board.get(new Coordinate(x, y - 1)).matrixValue == 1 ))
                        && (x == 0 || (board.get(new Coordinate(x - 1, y)).matrixValue == 1) && board.get(new Coordinate(x - 1, y + 1)).matrixValue == 1)
                        && (y + 1 == getHeight() - 1 || board.get(new Coordinate(x, y + 2)).matrixValue == 1)
                )
                        ) {
                    twoIsolatedCellCounter++;
                   // if (twoIsolatedCellCounter % 100000 == 0)
                   //     System.out.println(twoIsolatedCellCounter + " double isolated cell found");
                    return true;

                }

            }
        }

        return false;
    }


    public void printBoard() {

        //Method to print the pentominoes
        for (int a = 0; a < getHeight(); a++) {
            for (int b = 0; b < getWidth(); b++) {
                System.out.print(board.get(new Coordinate(b, a)).matrixValue + "  ");
            }
            System.out.println();
        }
    }

    public void fillPentomino(Coordinate cell, Pentomino curPentomino, int toAdd ){

            for (Coordinate curCoord : curPentomino.coords) {

                board.put(new Coordinate(curCoord.x + cell.x, curCoord.y + cell.y), new CellValues(toAdd, curPentomino.type));

            }
    }

    public void removePentomino(Coordinate cell, Pentomino curPentomino){

            for (Coordinate curCoord : curPentomino.coords) {

                board.put(new Coordinate(curCoord.x + cell.x, curCoord.y + cell.y), new CellValues(0, 0));

            }
    }


    public boolean checkCell(Coordinate cell, HashMap<Pentomino, ArrayList<Pentomino>> pentominos, PentominoGrid grid) {

        int numberOfCells = getHeight() * getWidth();
        if (numberOfCells % 5 != 0)
            return false;


        if (pentominos.size() == 0) {
            successcounter++;
            System.out.println("success! found " + successcounter +  " solution(s)");

            return true;
        }

        Coordinate originalEmptyCell = new Coordinate(cell.x, cell.y);
        while (board.get(cell)!=null && board.get(cell).matrixValue == 1) {

            if (!isLeastCoordinateHeuristic || getWidth() < getHeight()) {
                if (cell.x < getWidth() - 1) {
                    cell.x++;
                    //System.out.println("column is " + cell.x);

                } else {
                    cell.x = 0;
                    cell.y++;
                    //System.out.println("row is " + cell.y);

                }
                if (cell.y >= getHeight()) {
                    System.out.println("no more cells left");
                    return true;
                }
            }
            else
            {
                if (cell.y < getHeight() - 1) {
                    cell.y++;
                    //System.out.println("column is " + cell.x);

                } else {
                    cell.y = 0;
                    cell.x++;
                    //System.out.println("row is " + cell.y);

                }
                if (cell.x >= getWidth() + 1) {
                    System.out.println("no more cells left");
                    return true;
                }

            }
        }

        for (Map.Entry<Pentomino, ArrayList<Pentomino>> entry : pentominos.entrySet()) {
            Pentomino pentominoToRemove = entry.getKey();
            for (Pentomino curPentomino : entry.getValue()) {
                if (!checkCollision(cell, curPentomino)) {
                    fillPentomino(cell, curPentomino, 1);
                 //   if (counter % 1000 == 0)
                   //     grid.paintSquares();

                    if (!checkIsolatedCells())
                    {
                        counter++;

                         /* try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                               e.printStackTrace();
                           }*/
                        if (counter % 100000 == 0)
                            System.out.println("amount of possibilities tried: " + counter);

                        HashMap<Pentomino, ArrayList<Pentomino>> reduced = new HashMap<Pentomino, ArrayList<Pentomino>>(pentominos);
                        reduced.remove(pentominoToRemove);
                        if (checkCell(cell, reduced, grid)) {
                            if (successcounter > 0) {
                                //System.out.println("amount of solutions found:" + successcounter);
                            }
                            //successcounter++;
                           // grid.paintSquares();

                             /*  try {
                                Thread.sleep(10000);
                            } catch (InterruptedException e) {
                               e.printStackTrace();
                           }*/
                           //return true;
                        }
                    }
                    removePentomino(cell, curPentomino);
//                    if (counter % 1000 == 0)
  //                      grid.paintSquares();

                }
            }
        }

        cell.x = originalEmptyCell.x;
        cell.y = originalEmptyCell.y;
        return false;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Pentomino generatePentomino(HashMap<Pentomino, ArrayList<Pentomino>> pentominos){

        Pentomino chosen = null;
        int cntr = 0;
        int random = (int) (Math.random()*12);
        for (Map.Entry<Pentomino, ArrayList<Pentomino>> entry : pentominos.entrySet()){
            if ( random == cntr){
                entry.getKey();
                int random2 = (int) (Math.random()* entry.getValue().size());
                chosen = entry.getValue().get(random2);
            }
            cntr++;
        }
        return chosen;
    }

    public Coordinate moveLeft(Coordinate curCoord, Pentomino curPento){
        removePentomino(curCoord,curPento );
        curCoord.x -= 1;
        if (checkCollision(curCoord, curPento))
            curCoord.x +=1;
        fillPentomino(curCoord, curPento, 1);

        return new Coordinate(curCoord.x, curCoord.y);
    }

    public Coordinate moveRight(Coordinate curCoord, Pentomino curPento){
        removePentomino(curCoord,curPento );
        curCoord.x += 1;
        if (checkCollision(curCoord, curPento))
            curCoord.x -=1;
        fillPentomino(curCoord, curPento, 1);

        return new Coordinate(curCoord.x, curCoord.y);
    }

    public Coordinate moveDown(Coordinate curCoord, Pentomino curPento){
        removePentomino(curCoord,curPento );
        curCoord.y += 1;
        if (checkCollision(curCoord, curPento))
            curCoord.y -=1;
        fillPentomino(curCoord, curPento, 1);
        printBoard();

        return new Coordinate(curCoord.x, curCoord.y);
    }

    public Coordinate moveUp(Coordinate curCoord, Pentomino curPento){
        removePentomino(curCoord,curPento );
        curCoord.y -= 1;
        if (checkCollision(curCoord, curPento))
            curCoord.y +=1;
        fillPentomino(curCoord, curPento, 1);

        return new Coordinate(curCoord.x, curCoord.y);
    }

    public void checkLines(){
        int cntrLines = 0;
        int cntrOnes = 0;
        for (int i = (height - 1); i >= 0; i--){
            for (int j = 0; j < getWidth(); j++){
                if (board.get( new Coordinate(j,i)).matrixValue  == 0)
                    break;
                else
                    cntrOnes++;
            }
            if (cntrOnes == getWidth()){
                cntrLines++;
                removeLine(i);
            }
            else
                dropLine(i, cntrLines);
            cntrOnes = 0;

        }
    }

    public void dropLine( int line, int cntr){
        for (int i = 0; i < getWidth(); i++){
            if(board.get(new Coordinate(i,line)).matrixValue  == 1){
                //int type = GETTYPE;
                board.put(new Coordinate(i, line+cntr), new CellValues(1, 1));
                board.put(new Coordinate(i, line), new CellValues(0, 0));
            }
        }
    }

    public void removeLine(int line){
        for (int i = 0; i < getWidth(); i++){
            board.put(new Coordinate(i, line), new CellValues(0,0));
        }
    }

    public static void main(String[] args) throws Exception {
        //Establishing shape of pentominoes, as well as defining whether they are reversable and/or flipable. Also making sure pentonino I will only be flipped once.
        // System.out.println("Shape j and its rotations & flips");
        // System.out.println("");

	/*	double[][] first = {{1.0,1.0,1.0},{2.0,2.0,2.0},{3.0,3.0,3.0}};
		double[][] second = {{2.0,2.0,2.0},{0.0, 0.0, 0.0},{-2.0,-2.0,-2.0}};

		double[][] result = addMatrices(first, second);

		//double val = matrix[0][2];

		String[] words = new String[10];

		Scanner input = new Scanner(System.in);

		System.out.println("Enter the number of levels your pyramid will have: ");

		int height = input.nextInt();

		for (int curRow = 0; curRow < height; curRow++) {
			for (int curColumn = 0; curColumn < height - curRow - 1; curColumn++)
				System.out.print("  ");

			for (int curPyramidCell = 0; curPyramidCell < curRow * 2 + 1; curPyramidCell++)
				System.out.print("[]");

			System.out.println();
		}

		System.out.print("Enter a sequence of number, ");
		System.out.println("end with a letter. ");

		int cntr = 0;

		while (input.hasNextDouble()) {
			cntr++;
			System.out.print("Number " + cntr);
			System.out.println(" was: " + input.nextDouble());
		}

		int[] toCheck = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		double epsilon = 1E-3;
		System.out.format("%.14f", epsilon);

		double a = 2.0;
		double b = Math.sqrt(a);

		if (Math.abs(a - b * b) <= epsilon)
			System.out.println("Equal");
		else
			System.out.println("Not equal");

		boolean found = contains(toCheck, 10, 0, toCheck.length - 1);
		int max = Integer.MAX_VALUE;
		int min = Integer.MIN_VALUE;

		char test = 'a';
		int sizeOf = sizeofChar();



*/

        HashMap<Pentomino, ArrayList<Pentomino>> pentominos = new HashMap<Pentomino, ArrayList<Pentomino>>();

        ArrayList<Pentomino> basicShapes = new ArrayList<Pentomino>();

        Pentomino f = new Pentomino(12, 0, 1, 0, 2, 1, 0, 1, 1, 2, 1, true, true);
        basicShapes.add(f);
        Pentomino l = new Pentomino(2, 0, 0, 1, 0, 2, 0, 3, 0, 3, 1, true, true);
        basicShapes.add(l);
        Pentomino n = new Pentomino(3, 0, 1, 1, 0, 1, 1, 2, 0, 3, 0, true, true);
        basicShapes.add(n);
        Pentomino p = new Pentomino(4, 0, 0, 0, 1, 1, 0, 1, 1, 2, 0, true, true);
        basicShapes.add(p);
        Pentomino t = new Pentomino(5, 0, 0, 0, 1, 0, 2, 1, 1, 2, 1, true, false);
        basicShapes.add(t);
        Pentomino u = new Pentomino(6, 0, 0, 0, 2, 1, 0, 1, 1, 1, 2, true, false);
        basicShapes.add(u);
        Pentomino v = new Pentomino(7, 0, 0, 1, 0, 2, 0, 2, 1, 2, 2, true, false);
        basicShapes.add(v);
        Pentomino w = new Pentomino(8, 0, 0, 1, 0, 1, 1, 2, 1, 2, 2, true, false);
        basicShapes.add(w);
        Pentomino y = new Pentomino(10, 0, 1, 1, 0, 1, 1, 2, 1, 3, 1, true, true);
        basicShapes.add(y);

        for (Pentomino basic : basicShapes){
            ArrayList<Pentomino> Rotatable = new ArrayList<Pentomino>();
            Rotatable.add(basic);
            if (basic.isRotatable){
                Pentomino basicRot1 = basic.Rotate();
                Rotatable.add(basicRot1);
                Pentomino basicRot2 = basicRot1.Rotate();
                Rotatable.add(basicRot2);
                Pentomino basicRot3 = basicRot2.Rotate();
                Rotatable.add(basicRot3);
            }

            if (basic.isFlippable){
                Pentomino flipped = basic.Flip();
                Rotatable.add(flipped);
                Pentomino basicFlip1 = flipped.Rotate();
                Rotatable.add(basicFlip1);
                Pentomino basicFlip2 = basicFlip1.Rotate();
                Rotatable.add(basicFlip2);
                Pentomino basicFlip3 = basicFlip2.Rotate();
                Rotatable.add(basicFlip3);
            }

            pentominos.put(basic, Rotatable);
        }

        Pentomino x = new Pentomino(9, 0 ,1, 1, 0, 1, 1, 1, 2, 2, 1, false, false);
        ArrayList<Pentomino> xRots = new ArrayList<Pentomino>();
        xRots.add(x);
        pentominos.put(x, xRots);

        Pentomino i = new Pentomino(1, 0, 0, 1, 0, 2, 0, 3, 0, 4, 0,false, false);
        Pentomino iRot1 = i.Rotate();
        ArrayList<Pentomino> iRots = new ArrayList<Pentomino>();
        iRots.add(i);
        iRots.add(iRot1);
        pentominos.put(i, iRots);

        Pentomino z = new Pentomino(11, 0, 0, 0, 1, 1, 1, 2, 1, 2, 2, false, false);
        Pentomino zRot1 = z.Rotate();
        ArrayList<Pentomino> zRots = new ArrayList<Pentomino>();
        zRots.add(z);
        zRots.add(zRot1);
        Pentomino zRot2 = z.Flip();
        zRots.add(zRot2);
        Pentomino zRot3 = zRot2.Rotate();
        zRots.add(zRot3);
        pentominos.put(z, zRots);


        //f.PrintPento();
        //f.PrintArrayList();

        Scanner in = new Scanner(System.in);
        int heightBoard=0;
        int widthBoard=0;
        int boardSize = heightBoard * widthBoard;

            System.out.println("Please enter the height of the board: ");
            heightBoard = in.nextInt();
            System.out.println("Please enter the width of the board: ");
            widthBoard = in.nextInt();
            boardSize = heightBoard * widthBoard;


        String answer1 = "";
        String answer2 = "";
        String answer3 = "";





        System.out.println("Please enter yes/no for using heuristic for isolated cells: ");
        answer1 = in.next();


        //	System.out.println("Please enter yes/no for using heuristic for double isolated cells: ");
        //	answer2 = in.next();

        System.out.println("Please enter yes/no for using heuristic for the least coordinate: ");
        answer3 = in.next();

        boolean heuristicSingleCell = answer1.equals("yes");
        boolean heurisricDoubleCell = answer2.equals("yes");
        boolean heuristicLeastCoordinate = answer3.equals("yes");

        Board board = new Board(widthBoard, heightBoard, heuristicSingleCell, heurisricDoubleCell, heuristicLeastCoordinate);



        /* Pentomino Visualisation */
        PentominoGrid grid = new PentominoGrid(board);
        JFrame j = new JFrame();
        j.add(grid);

        j.setSize(board.getWidth()*100, board.getHeight()*100);
        j.setVisible(true);
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Coordinate firstPosition = new Coordinate((widthBoard / 2), 0);
        board.currentPosition = firstPosition;
        Pentomino pentomino;
        pentomino = board.generatePentomino(pentominos);
        board.activePentomino = pentomino;
        board.moveDown(board.currentPosition, board.activePentomino);
        board.moveDown(board.currentPosition, board.activePentomino);
        board.moveDown(board.currentPosition, board.activePentomino);
        board.moveDown(board.currentPosition, board.activePentomino);
        pentomino = board.generatePentomino(pentominos);
        board.activePentomino = pentomino;
        board.moveDown(board.currentPosition, board.activePentomino);
        board.moveDown(board.currentPosition, board.activePentomino);
        board.moveDown(board.currentPosition, board.activePentomino);
        board.moveDown(board.currentPosition, board.activePentomino);
        board.moveDown(board.currentPosition, board.activePentomino);


        while (true) {

            board.activePentomino = pentomino;
            if (!board.checkCollision(firstPosition, pentomino)) {
                board.printBoard();
                board.fillPentomino(firstPosition, pentomino, 1);
                //break;
            }

            int keyPressed = System.in.read();

            if (keyPressed == 'S')
                board.moveDown(board.currentPosition, board.activePentomino);

//            while (true) {
                grid.paintSquares();
                //Thread.sleep(5000);
                //board.moveDown(firstPosition, pentomino);
                board.printBoard();
  //          }
        }
    }



}
