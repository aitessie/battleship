public class Field {

    private final static int SIZE_OF_FIELD = 11;
    private final String[][] field;
    private final String[][] fogField;
    private Ship[] ships = new Ship[0];
    private int filledCells;

    public Field() {
        this.field = createEmptyField();
        this.fogField = createEmptyField();
    }

    private String[][] createEmptyField() {
        String[][] field = new String[SIZE_OF_FIELD][SIZE_OF_FIELD];
        for (int i = 0; i < SIZE_OF_FIELD; i++) {
            for (int j = 0; j < SIZE_OF_FIELD; j++) {
                if (i == 0 && j == 0) { //первое пустое
                    field[i][j] = "  ";
                } else if (i == 0 && j == SIZE_OF_FIELD - 1) { //послндяя цифра
                    field[i][j] = String.valueOf(j);
                } else if (i > 0 && j == SIZE_OF_FIELD - 1) { //крайние ячейки
                    field[i][j] = "~";
                } else if (i == 0) { //верхний ряд цифр
                    field[i][j] = j + " ";
                } else if (j == 0) { //столбик букв
                    field[i][j] = (char) ('A' + (i - 1)) + " ";
                } else { //остальное
                    field[i][j] = "~ ";
                }
            }
        }
        return field;
    }

    public void printField() {
        for (int i = 0; i < SIZE_OF_FIELD; i++) {
            for (int j = 0; j < SIZE_OF_FIELD; j++) {
                System.out.print(field[i][j]);
            }
            System.out.println();
        }
    }

    public void printFogField() {
        for (int i = 0; i < SIZE_OF_FIELD; i++) {
            for (int j = 0; j < SIZE_OF_FIELD; j++) {
                System.out.print(fogField[i][j]);
            }
            System.out.println();
        }
    }

    public boolean isShipCoordinatesValid(int row1, int column1, int row2, int column2, ShipType ship) {
        int difOfRow = Math.max(row1, row2) - Math.min(row1, row2);
        int difOfColumn = Math.max(column1, column2) - Math.min(column1, column2);

        //проверка верности размера корабля
        if (difOfRow == 0) {
            if (difOfColumn != ship.getSize() - 1) {
                System.out.println("\nError! Wrong length of the " + ship.getName() + "! Try again:\n");
                return false;
            }
        } else if (difOfColumn == 0) {
            if (difOfRow != ship.getSize() - 1) {
                System.out.println("\nError! Wrong length of the " + ship.getName() + "! Try again:\n");
                return false;
            }
        }

        //проверка что не диагонально
        if (row1 != row2 && column1 != column2) {
            System.out.println("\nError! Wrong ship location! Try again:\n");
            return false;
        }

        //проверка что не выходят за рамки поля
        if (!isCoordinateValid(row1, column1) && !isCoordinateValid(row2, column2)) {
            return false;
        }

        //проверка что другие корабли не мешают
        int firstCoordinateRow = Math.min(row1, row2) - 1;
        int firstCoordinateColumn = Math.min(column1, column2) - 1;
        int secondCoordinateRow = Math.max(row1, row2) + 1;
        int secondCoordinateColumn = Math.max(column1, column2) + 1;

        for (int i = firstCoordinateRow; i <= secondCoordinateRow; i++) {
            for (int j = firstCoordinateColumn; j <= secondCoordinateColumn; j++) {
                if (i != Field.SIZE_OF_FIELD && j != Field.SIZE_OF_FIELD && field[i][j].equals("O ")) {
                    System.out.println("\nError! You placed it too close to another one. Try again:\n");
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isCoordinateValid(int shootRow, int shootColumn) {
        //проверка что не выходят за рамки поля
        if (!(shootRow >= 1 && shootRow <= 10 && shootColumn >= 1 && shootColumn <= 10)) {
            System.out.println("\nError! You entered the wrong coordinates! Try again:\n");
            return false;
        }
        return true;
    }

    public void hitOrMiss(int shootRow, int shootColumn) {
        if (field[shootRow][shootColumn].equals("X ")) {
            printFogField();
            System.out.println("\nYou hit a ship!\n");
        } else if (field[shootRow][shootColumn].equals("O ")) {
            fogField[shootRow][shootColumn] = "X ";
            field[shootRow][shootColumn] = "X ";

            filledCells--;
            printFogField();
            checkKilling(shootRow, shootColumn);
        } else {
            fogField[shootRow][shootColumn] = "M ";
            field[shootRow][shootColumn] = "M ";
            printFogField();
            System.out.println("\nYou missed!\n");
        }
    }

    public void checkKilling(int shootRow, int shootColumn) {
        for (Ship ship : ships) {
            if (ship.isShipHit(shootRow, shootColumn)) {
                if (filledCells == 0) {
                    System.out.println("\nYou sank the last ship. You won. Congratulations!\n");
                    System.exit(200);
                }
                if (ship.isAlive()) {
                    System.out.println("\nYou hit a ship! Try again:\n");
                } else {
                    System.out.println("\nYou sank a ship! Specify a new target:\n");
                }
            }
        }
    }

    public void placeShip(ShipType shipType, int row1, int column1, int row2, int column2) {

        int setRow1 = Math.min(row1, row2);
        int setColumn1 = Math.min(column1, column2);
        int setRow2 = Math.max(row1, row2);
        int setColumn2 = Math.max(column1, column2);

        Ship ship = new Ship(shipType, setRow1, setRow2, setColumn1, setColumn2);

        for (int i = setRow1; i <= setRow2; i++) {
            for (int j = setColumn1; j <= setColumn2; j++) {
                field[i][j] = "O ";
            }
        }

        Ship[] newShips = new Ship[ships.length + 1];
        System.arraycopy(ships, 0, newShips, 0, ships.length);
        newShips[ships.length] = ship;
        ships = newShips;

        filledCells += shipType.getSize();

        System.out.println();
    }
}
