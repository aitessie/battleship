import java.util.Scanner;


public class Game {

    private final Field field;
    private final String playerName;

    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) {
        Game player1 = new Game(new Field(), "Player 1");
        Game player2 = new Game(new Field(), "Player 2");

        System.out.println(player1.getPlayerName() + ", place your ships on the game field\n");
        player1.doGamePreparation();
        toTheNextPlayer();

        System.out.println(player2.getPlayerName() + ", place your ships to the game field\n");
        player2.doGamePreparation();
        toTheNextPlayer();

        System.out.println("The game starts!\n");
        while (true) {
            player1.takeAShotPlayer1(player2);
            toTheNextPlayer();

            player2.takeAShotPlayer2(player1);
            toTheNextPlayer();
        }
    }

    public Game(Field field, String playerName) {
        this.field = field;
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    private void doGamePreparation() {
        field.printField();
        Scanner scanner = new Scanner(System.in);

        ShipType[] values = ShipType.values();

        for (ShipType shipType : values) {
            System.out.printf("\nEnter the coordinates of the %s (%d cells):\n%n", shipType.getName(), shipType.getSize());

            boolean isShipCoordinatesValid;
            int row1;
            int column1;
            int row2;
            int column2;

            do {
                String coordinate1 = scanner.next();
                String coordinate2 = scanner.next();

                row1 = coordinate1.toCharArray()[0] - 64;
                column1 = Integer.parseInt(coordinate1.substring(1));
                row2 = coordinate2.toCharArray()[0] - 64;
                column2 = Integer.parseInt(coordinate2.substring(1));

                isShipCoordinatesValid = field.isShipCoordinatesValid(row1, column1, row2, column2, shipType);

            } while (!isShipCoordinatesValid);

            field.placeShip(shipType, row1, column1, row2, column2);

            field.printField();
        }
    }

    private static void toTheNextPlayer() {
        System.out.println("Press Enter and pass the move to another player\n");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        System.out.flush();
        System.out.println();
    }

    private void takeAShotPlayer1(Game player2) {
        Scanner scanner = new Scanner(System.in);
        player2.field.printFogField();
        System.out.println("---------------------");
        field.printField();
        System.out.println("\n" + getPlayerName() + ", it's your turn:\n");
        System.out.println("Take a shot!\n");
        boolean isShotCoordinateValid;
        int shotRow;
        int shotColumn;

        do {
            String shootCoordinate = scanner.next();

            shotRow = shootCoordinate.toCharArray()[0] - 64;
            shotColumn = Integer.parseInt(shootCoordinate.substring(1));

            isShotCoordinateValid = player2.field.isCoordinateValid(shotRow, shotColumn);

        } while (!isShotCoordinateValid);
        System.out.println();
        player2.field.hitOrMiss(shotRow, shotColumn);
    }

    private void takeAShotPlayer2(Game player1) {
        Scanner scanner = new Scanner(System.in);
        player1.field.printFogField();
        System.out.println("---------------------");
        field.printField();
        System.out.println("\n" + getPlayerName() + ", it's your turn:\n");
        System.out.println("Take a shot!\n");
        boolean isShotCoordinateValid;
        int shotRow;
        int shotColumn;

        do {
            String shootCoordinate = scanner.next();

            shotRow = shootCoordinate.toCharArray()[0] - 64;
            shotColumn = Integer.parseInt(shootCoordinate.substring(1));

            isShotCoordinateValid = player1.field.isCoordinateValid(shotRow, shotColumn);

        } while (!isShotCoordinateValid);
        System.out.println();
        player1.field.hitOrMiss(shotRow, shotColumn);
    }
}
