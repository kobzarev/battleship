
public class Coordinate {
    private final int x;
    private final int y;
    private CoordinateState state = CoordinateState.INTACT;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void hit() {
        this.state = CoordinateState.HIT;
    }

    public boolean compare(Coordinate c) {
        return c.x == this.x && c.y == this.y;
    }

    public boolean isHit() {
        return this.state == CoordinateState.HIT;
    }
}