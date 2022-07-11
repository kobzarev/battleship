import java.util.Scanner;

public class Battleship {
    private final int fieldSize;

    private final Field[] field = new Field[2];

    private int currentPlayer = 0;

    private final Scanner scanner = new Scanner(System.in);

    private final String env;

    private final int[] shipsSize = new int[] {5, 4, 3, 3, 2};
    private final String[] outputStrings = new String[] {
            "Enter the coordinates of the Aircraft Carrier (5 cells):",
            "Enter the coordinates of the Battleship (4 cells):",
            "Enter the coordinates of the Submarine (3 cells):",
            "Enter the coordinates of the Cruiser (3 cells):",
            "Enter the coordinates of the Destroyer (2 cells):"
    };

    public Battleship(int fieldSize, String env) {
        this.fieldSize = fieldSize;
        this.env = env;
    }

    public void run () {
        for (int i = 0; i < this.field.length; i++) {
            create(i);
            System.out.println("Press Enter and pass the move to another player");
            this.scanner.nextLine();
        }
        battle();
    }

    private void create(int currentPlayer) {
        this.field[currentPlayer] = new Field(this.fieldSize);
        System.out.println("Player " + (currentPlayer + 1) + ", place your ships on the game field");
        this.field[currentPlayer].print();

        for (int i = 0; i < this.shipsSize.length; i++) {
            while (true) {
                System.out.println(this.outputStrings[i]);
                String input = getInputData(currentPlayer, i);
                try {
                    this.field[currentPlayer].addShip(new Ship(input, this.shipsSize[i], this.fieldSize));
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
            this.field[currentPlayer].print();
        }
    }

    private String getInputData(int currentPlayer, int number) {
        String input;
        if (this.env.equals("prod")) {
            input = this.scanner.nextLine().trim();
        } else {
            input = TestShips.get(currentPlayer, number);
        }
        return input;
    }

    private void battle() {
        while (!isAllShipsOpponentDestroyed()) {
            int anotherPlayer = this.currentPlayer == 0 ? 1 : 0;
            printBattleFields();
            try {
                String input = this.scanner.nextLine().trim();
                ShotResult result = this.field[anotherPlayer].shot(new Shot(input, this.fieldSize));
                System.out.println(getMessageAfterShot(result));
                System.out.println("Press Enter and pass the move to another player");
                this.scanner.nextLine();
                changePlayer();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String getMessageAfterShot(ShotResult result) {
        String message = "";
        switch (result) {
            case MISS:
                message = "You missed! Try again:";
                break;
            case HIT:
                message = "You hit a ship! Try again:";
                break;
            case DESTROY:
                if (isAllShipsOpponentDestroyed()) {
                    message = "You sank the last ship. You won. Congratulations!";
                } else {
                    message = "You sank a ship! Specify a new target:";
                }

                break;
        }
        return message;
    }

    private boolean isAllShipsOpponentDestroyed() {
        int anotherPlayer = this.currentPlayer == 0 ? 1 : 0;
        boolean result = true;
        for (Ship ship : this.field[anotherPlayer].getShips()) {
            if (!ship.isDestroyed()) {
                result = false;
                break;
            }
        }
        return result;
    }

    private void printBattleFields() {
        int anotherPlayer = this.currentPlayer == 0 ? 1 : 0;
        this.field[anotherPlayer].printShots();
        System.out.println("---------------------");
        this.field[currentPlayer].print();
        System.out.println("Player (" + (this.currentPlayer + 1) + "), it's your turn:");
    }

    private void changePlayer() {
        this.currentPlayer = this.currentPlayer == 0 ? 1 : 0;
    }
}
