public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int a, int b) {
        x = a;
        y = b;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public void setX(int newX){
        x = newX;
    }

    public void setY(int newY){
        y = newY;
    }
    public void Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int hashCode() {
        return x * 100 + y;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if ((obj == null) || (obj.getClass() != this.getClass()))
            return false;
        // object must be Test at this point
        Coordinate coord = (Coordinate) obj;
        return x == coord.x && y == coord.y;
    }

    public Coordinate clone() {
        return new Coordinate(x, y);
    }


}