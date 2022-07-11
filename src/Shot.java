
public class Shot {
    private final Coordinate coordinate;

    public Shot(String input, int size) {
        this.coordinate = new Coordinate((int)input.charAt(0) - 64, Integer.parseInt(input.substring(1)));
        checkBelongToField(size);
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    private void checkBelongToField(int size) {
        if (this.coordinate.getX() < 1 || this.coordinate.getX() > (size - 1) || this.coordinate.getY() < 1 || this.coordinate.getY() > (size - 1)) {
            throw new IllegalArgumentException("Error! You entered the wrong coordinates! Try again:");
        }
    }
}
