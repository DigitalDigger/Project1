import java.util.*;

public class Tetris {

	public List<Coordinate> coords = new ArrayList<Coordinate>();
	public boolean isRotatable;
	public boolean isFlippable;
	public int type;


	public Tetris(int a, int b, int c, int d, int e, int f, int g, int h, int i, int j, int k, boolean l, boolean m){
		coords.add(new Coordinate(b, c));
		coords.add(new Coordinate(d, e));
		coords.add(new Coordinate(f, g));
		coords.add(new Coordinate(h, i));
		coords.add(new Coordinate(j, k));
		isRotatable = l;
		isFlippable = m;
		type = a;
	}


	public Tetris(List<Coordinate> newCoords, boolean l, boolean m, int inType)
	{
		coords = newCoords;
		isRotatable = l;
		isFlippable = m;
		type = inType;
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


	public Tetris Rotate(){
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

		Tetris finite = new Tetris(ShapedUp, this.isRotatable, this.isFlippable, this.type);

		return finite;

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
		Tetris pento = (Tetris)obj;
		return type == pento.type;
	}

	public Tetris Flip(){

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

		Tetris finite = new Tetris(Up, this.isRotatable, this.isFlippable, this.type);

		return finite;
	}
}







