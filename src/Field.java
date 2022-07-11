import java.util.ArrayList;

public class Field {
    private final int size;
    private final String[][] field;

    private final ArrayList<Ship> ships = new ArrayList<>();

    public Field(int size) {
        this.size = size;
        this.field = new String[size][size];
        create();
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public void print() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                System.out.print(this.field[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void printShots() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (this.field[i][j].equals("X") || this.field[i][j].equals("M") || i == 0 || j == 0) {
                    System.out.print(this.field[i][j] + " ");
                } else {
                    System.out.print("~ ");
                }
            }
            System.out.println();
        }
    }

    public void addShip(Ship ship) {
        checkCoordinate(ship);
        checkPlace(ship.getPlaceCoordinates());

        for (Coordinate item : ship.getCoordinates()) {
            this.field[item.getX()][item.getY()] = "O";
        }
        this.ships.add(ship);
    }

    public ShotResult shot(Shot shot) {
        String field = this.field[shot.getCoordinate().getX()][shot.getCoordinate().getY()];
        ShotResult result;
        switch (field) {
            case "~":
            case "M":
                this.field[shot.getCoordinate().getX()][shot.getCoordinate().getY()] = "M";
                result = ShotResult.MISS;
                break;
            case "O":
                this.field[shot.getCoordinate().getX()][shot.getCoordinate().getY()] = "X";
                Ship ship = findShip(shot.getCoordinate());
                ship.hit(shot.getCoordinate());
                result = ship.isDestroyed() ? ShotResult.DESTROY : ShotResult.HIT;
                break;
            case "X":
                result = ShotResult.HIT;
                break;
            default:
                throw new IllegalArgumentException("Error field! Try again:");
        }
        return result;
    }

    private void create() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (i == 0 && j == 0) {
                    this.field[i][j] = " ";
                } else if (i == 0) {
                    this.field[i][j] = String.valueOf(j);
                } else if (j == 0) {
                    this.field[i][j] = String.valueOf((char)(64 + i));
                } else {
                    this.field[i][j] = "~";
                }
            }
        }
    }

    private void checkCoordinate(Ship ship) {
        if (ship.getA().getX() != ship.getB().getX() && ship.getA().getY() != ship.getB().getY()) {
            throw new IllegalArgumentException("Error! Wrong ship location! Try again:");
        }
    }

    private void checkPlace(Coordinate[] place) {
        for (Coordinate c : place) {
            if (c != null) {
                if (!this.field[c.getX()][c.getY()].equals("~")) {
                    throw new IllegalArgumentException("Error! You placed it too close to another one. Try again:");
                }
            }
        }
    }

    private Ship findShip(Coordinate coordinate) {
        Ship result = null;
        for (Ship ship : this.ships) {
            for (Coordinate c : ship.getCoordinates()) {
                if (coordinate.compare(c)) {
                    result = ship;
                    break;
                }
            }
        }
        if (result == null) {
            throw new RuntimeException("Ship not found");
        }
        return result;
    }
}
