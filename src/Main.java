import java.util.Scanner;

public class Main {

    private static String[][] field;
    private final static int SIZE_OF_FIELD = 11;

    public static void main(String[] args) {
        field = getEmptyField();
        printField();

        Ships[] values = Ships.values();

        for (Ships ship : values) {
            System.out.printf("\nEnter the coordinates of the %s (%d cells):\n%n", ship.getName(), ship.getSize());

            boolean isCoordinateValid;
            int row1;
            int column1;
            int row2;
            int column2;
            do {
                Scanner scanner = new Scanner(System.in);

                if (!scanner.hasNext()) {
                    System.out.println("\n> ");
                }
                String coordinate1 = scanner.next();
                String coordinate2 = scanner.next();

                row1 = coordinate1.toCharArray()[0] - 64;
                column1 = Integer.parseInt(coordinate1.substring(1));
                row2 = coordinate2.toCharArray()[0] - 64;
                column2 = Integer.parseInt(coordinate2.substring(1));

                isCoordinateValid = validateCoordinates(row1, column1, row2, column2, ship);

            } while (!isCoordinateValid);
            setShip(row1, column1, row2, column2);
            printField();
        }
    }

    private static String[][] getEmptyField() {
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

    private static boolean validateCoordinates(int row1, int column1, int row2, int column2, Ships ship) {
        int difOfRow = Math.max(row1, row2) - Math.min(row1, row2);
        int difOfColumn = Math.max(column1, column2) - Math.min(column1, column2);

        //проверка верности размера корабля
        if (difOfRow == 0) {
            if (difOfColumn != ship.getSize() - 1) {
                System.out.println("\nError! Wrong length of the " + ship.getName() + "! Try again:");
                return false;
            }
        } else if (difOfColumn == 0) {
            if (difOfRow != ship.getSize() - 1) {
                System.out.println("\nError! Wrong length of the " + ship.getName() + "! Try again:");
                return false;
            }
        }

        //проверка что не диагонально
        if (row1 != row2 && column1 != column2) {
            System.out.println("\nError! Wrong ship location! Try again:");
            return false;
        }

        //проверка что не выходят за рамки поля
        if (!(row1 >= 1 && row1 <= 10 && column1 >= 1 && column1 <= 10 && row2 >= 1 && row2 <= 10 && column2 >= 1 && column2 <= 10)) {
            return false;
        }

        //проверка что другие корабли не мешают
        int firstCoordinateRow = Math.min(row1, row2) - 1;
        int firstCoordinateColumn = Math.min(column1, column2) - 1;
        int secondCoordinateRow = Math.max(row1, row2) + 1;
        int secondCoordinateColumn = Math.max(column1, column2) + 1;

        for (int i = firstCoordinateRow; i <= secondCoordinateRow; i++) {
            for (int j = firstCoordinateColumn; j <= secondCoordinateColumn; j++) {
                if (i != SIZE_OF_FIELD && j != SIZE_OF_FIELD && field[i][j].equals("O ")) {
                    System.out.println("\nError! You placed it too close to another one. Try again:");
                    return false;
                }
            }
        }
        return true;
    }

    private static void setShip(int row1, int column1, int row2, int column2) {

        int setRow1 = Math.min(row1, row2);
        int setColumn1 = Math.min(column1, column2);
        int setRow2 = Math.max(row1, row2);
        int setColumn2 = Math.max(column1, column2);

        for (int i = setRow1; i <= setRow2; i++) {
            for (int j = setColumn1; j <= setColumn2; j++) {
                field[i][j] = "O ";
            }
        }
    }

    private static void printField() {
        for (int i = 0; i < SIZE_OF_FIELD; i++) {
            for (int j = 0; j < SIZE_OF_FIELD; j++) {
                System.out.print(field[i][j]);
            }
            System.out.println();
        }
    }
}