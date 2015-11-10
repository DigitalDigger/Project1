public class Coordinate {
    public int x;
    public int y;

    public Coordinate(int a, int b) {
        x = a;
        y = b;
    }

    public int hashCode() {
        return x * 100 + y;
    }

    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if((obj == null) || (obj.getClass() != this.getClass()))
            return false;
        // object must be Test at this point
        Coordinate coord = (Coordinate)obj;
        return x == coord.x && y == coord.y;
    }

}