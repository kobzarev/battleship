public class TestShips {
    public static String get(int playerIndex, int index) {
        String[][] ships = new String[2][5];
        ships[0][0] = "F3 F7";
        ships[0][1] = "A1 D1";
        ships[0][2] = "J10 J8";
        ships[0][3] = "B9 D9";
        ships[0][4] = "I2 J2";

        ships[1][0] = "H2 H6";
        ships[1][1] = "F3 F6";
        ships[1][2] = "H8 F8";
        ships[1][3] = "D4 D6";
        ships[1][4] = "C8 D8";

        return ships[playerIndex][index];
    }
}
