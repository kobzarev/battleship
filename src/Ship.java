public class Ship {
    private Coordinate a;
    private Coordinate b;
    private final int length;

    private Coordinate[] coordinates;

    private Coordinate[] placeCoordinates;

    private ShipState state = ShipState.INTACT;

    public Ship(String input, int length, int fieldSize) {
        String[] tmp = input.split(" ");

        this.a = new Coordinate((int)tmp[0].charAt(0) - 64, Integer.parseInt(tmp[0].substring(1)));
        this.b = new Coordinate((int)tmp[1].charAt(0) - 64, Integer.parseInt(tmp[1].substring(1)));

        if (a.getX() > b.getX() || a.getY() > b.getY()) {
            Coordinate tmpC = a;
            this.a = this.b;
            this.b = tmpC;
        }

        this.length = length;

        checkLength();

        calcShipCoordinates();
        calcPlaceCoordinates(fieldSize);
    }

    public Coordinate getA() {
        return a;
    }

    public Coordinate getB() {
        return b;
    }

    public Coordinate[] getCoordinates() {
        return coordinates;
    }

    public Coordinate[] getPlaceCoordinates() {
        return placeCoordinates;
    }

    public void hit(Coordinate coordinate) {
        for (Coordinate c : this.coordinates) {
            if (c.compare(coordinate)) {
                c.hit();
                break;
            }
        }
        if (this.state.equals(ShipState.INTACT)) {
            this.state = ShipState.HIT;
        }
        if (checkState()) {
            this.state = ShipState.DESTROYED;
        }
    }

    public boolean isDestroyed() {
        return this.state == ShipState.DESTROYED;
    }

    private void checkLength() {
        int length = this.b.getX() - this.a.getX() + this.b.getY() - this.a.getY() + 1;
        if (length != this.length || length < 1) {
            throw new IllegalArgumentException("Error! Wrong length of the Submarine! Try again:");
        }
    }

    private void calcShipCoordinates() {
        this.coordinates = new Coordinate[this.length];
        int j = 0;
        if (this.getA().getX() == this.getB().getX()) {
            for (int i = this.getA().getY(); i <= this.getB().getY(); i++, j++) {
                this.coordinates[j] = new Coordinate(this.getA().getX(), i);
            }
        } else {
            for (int i = this.getA().getX(); i <= this.getB().getX(); i++, j++) {
                this.coordinates[j] = new Coordinate(i, this.getA().getY());
            }
        }
    }

    private void calcPlaceCoordinates(int fieldSize) {
        this.placeCoordinates = new Coordinate[21];
        int index = 0;
        for (Coordinate c : this.getCoordinates()) {
            for (int i = c.getX() - 1; i <= c.getX() + 1; i++) {
                for (int j = c.getY() - 1; j <= c.getY() + 1; j++) {
                    if (i > 0 && j > 0 && i < fieldSize && j < fieldSize) {
                        Coordinate tmp = new Coordinate(i, j);
                        if (!this.isCoordinateExistInArray(this.placeCoordinates, tmp)) {
                            this.placeCoordinates[index] = tmp;
                            index++;
                        }
                    }
                }
            }
        }
    }

    private boolean isCoordinateExistInArray(Coordinate[] array, Coordinate c) {
        boolean result = false;
        for (Coordinate item : array) {
            if (item != null && item.compare(c)) {
                result = true;
                break;
            }
        }
        return result;
    }

    private boolean checkState() {
        boolean result = true;
        for (Coordinate c : this.coordinates) {
            if (!c.isHit()) {
                result = false;
                break;
            }
        }
        return result;
    }
}