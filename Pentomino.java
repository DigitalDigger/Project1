import javax.swing.*;
import java.util.*;
import java.awt.event.*;

public class Pentomino {

	public List<Coordinate> coords = new ArrayList<Coordinate>();
	public boolean isRotatable;
	public boolean isFlippable;
	public int type;


	public Pentomino(int a, int b, int c, int d, int e, int f, int g, int h, int i, int j, int k, boolean l, boolean m){
		coords.add(new Coordinate(b, c));
		coords.add(new Coordinate(d, e));
		coords.add(new Coordinate(f, g));
		coords.add(new Coordinate(h, i));
		coords.add(new Coordinate(j, k));
		isRotatable = l;
		isFlippable = m;
		type = a;
	}


	public Pentomino(List<Coordinate> newCoords, boolean l, boolean m, int inType)
	{
		coords = newCoords;
		isRotatable = l;
		isFlippable = m;
		type = inType;
	}


	public void PrintPento(){
		for (Coordinate curCoord : coords){

			System.out.println(curCoord.x +  " "+  curCoord.y);
		}
		System.out.println("");
	}

	public static int MinAr(int[] array){
		if (array.length == 1){
			return array[0];
		}

		else {
			int min = Math.min(array[0], array[1]);

			if (min == array[0]){
				int temp = array[0];
				array[0] = array[1];
				array[1] = temp;
			}

			int newArray[] = new int[array.length -1];
			System.arraycopy(array, 1, newArray, 0, newArray.length);

			return MinAr(newArray);
		}

	}


	public Pentomino Rotate(){
		// Method to rotate the pentominoes
		List<Coordinate> Rotated = new ArrayList<Coordinate>();

		//Transposition of the array (rotation +90° and flip all in once
		for (Coordinate curCoord : coords){
			//Coordinate temp = new Coordinate(coords.get(i));
			Coordinate Transp = new Coordinate(curCoord.y, curCoord.x);
			Coordinate Reverse = new Coordinate((5 - (Transp.x) - 1), Transp.y);
			Rotated.add(Reverse);
		}

		int[] position = new int[5];
		for (int a = 0; a < 5; a++){
			//Coordinate cell = new Coordinate(Rotated.get(a));
			position[a] = Rotated.get(a).x;
		}

		List<Coordinate> ShapedUp = new ArrayList<Coordinate>();

		int c = MinAr(position);
		for (Coordinate Up : Rotated){
			//Coordinate Up = new Coordinate (Rotated.get(e));
			Coordinate ShapeUp = new Coordinate((Up.x)-c, Up.y);
			ShapedUp.add(ShapeUp);
		}

		Pentomino finite = new Pentomino(ShapedUp, this.isRotatable, this.isFlippable, this.type);

		return finite;

	}



	public void PrintArrayList() {
		for (int i = 0; i < coords.size(); i++) {
			System.out.print(coords.get(i).x + "," + coords.get(i).y);
			System.out.println(" ");
		}
	}

	public int hashCode() {
		return type;
	}


	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		// object must be Test at this point
		Pentomino pento = (Pentomino)obj;
		return type == pento.type;
	}

	public Pentomino Flip(){

		List<Coordinate> flipped = new ArrayList<Coordinate>();

		for (Coordinate curCoord : coords){
			Coordinate Flip = new Coordinate (5 - 1 - curCoord.x, curCoord.y);
			flipped.add(Flip);
		}

		List<Coordinate> Up = new ArrayList<Coordinate>();

		int[] position = new int[5];

		for (int a = 0; a < 5; a++){
			//Coordinate cell = new Coordinate();
			position[a] = flipped.get(a).x;
		}

		List<Coordinate> ShapedUp = new ArrayList<Coordinate>();

		int c = MinAr(position);

		for (Coordinate Upped : flipped){
			Coordinate ShapeUp = new Coordinate(Upped.x - c, Upped.y);
			Up.add(ShapeUp);
		}

		Pentomino finite = new Pentomino(Up, this.isRotatable, this.isFlippable, this.type);

		return finite;
	}

	public static boolean contains(int[] array, int x) {
		boolean found = false;
		int start = 0;
		int end = array.length;

		while (!found && start <= end) {
			int middle = (start + end) / 2;

			if (array[middle] < x)
				start = middle + 1;
			else if (x < array[middle])
				end = middle - 1;
			else
				found = true;
		}

		return found;
	}

	public static boolean contains(int[] array, int x, int start, int end) {

		if (start > end)
			return false;

		int middle = (start + end) / 2;

		if (array[middle] < x)
			return contains(array, x, middle + 1, end);
		else if (x < array[middle])
			return contains(array, x, start, middle - 1);
		else
			return true;

	}

	public static int sizeofChar() {
		char i = 1, j = 0;
		while (i != 0) {
			i =  (char) (i<<1); j++;
		}
		return j;
	}

	public static boolean checkIsMatrix(double[][] matrix) {
		int secondDimensionLen = matrix[0].length;

		for (int curRow = 0; curRow < matrix.length; curRow++) {
			if (matrix[curRow].length != secondDimensionLen)
				return false;
		}

		return true;
	}

	public static double[][] addMatrices(double[][] first, double[][] second) {

		if (!checkIsMatrix(first) || !checkIsMatrix(second)) {
			System.out.println("One of the parameters is not a matrix");
			return new double[0][0];
		}

		int firstMatrix1stDimension = first.length;
		int firstMatrix2ndDimenstion = first[0].length;
		int secondMatrix1stDimension = second.length;
		int secondMatrix2ndDimension = second[0].length;

		if (firstMatrix1stDimension != secondMatrix1stDimension
				|| firstMatrix2ndDimenstion != secondMatrix2ndDimension) {
			System.out.println("Only matrices with identical dimensions could be added");
			return  new double[0][0];
		}

		double[][] result = new double[firstMatrix1stDimension][firstMatrix2ndDimenstion];

		for (int curRow = 0; curRow < firstMatrix1stDimension; curRow++) {
			for (int curColumn = 0; curColumn < firstMatrix2ndDimenstion; curColumn++) {
				result[curRow][curColumn] = first[curRow][curColumn] + second[curRow][curColumn];
			}
		}

		return result;

	}

	public static double[][] multiplyMatrices(double[][] first, double[][] second) {

		if (!checkIsMatrix(first) || !checkIsMatrix(second)) {
			System.out.println("One of the parameters is not a matrix");
			return new double[0][0];
		}

		int firstWidth = first[0].length;
		int secondHeight = second.length;

		if (firstWidth != secondHeight) {
			System.out.println("The width of the first matrix should be equal to the height of the second");
			return null;
		}

		double[][] result = new double[first.length][second[0].length];

		for (int curRow = 0; curRow < first.length; curRow++) {
			for (int curColumn = 0; curColumn < second[0].length; curColumn++) {
				for (int curCell = 0; curCell < firstWidth; curCell++) {
					result[curRow][curColumn] += first[curRow][curCell] * second[curCell][curColumn];
				}
			}
		}

		return result;

	}



}







