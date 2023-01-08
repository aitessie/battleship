public class Ship {

    private final ShipType shipType;
    private final int row1;
    private final int row2;
    private final int column1;
    private final int column2;
    private int injuries;
    private boolean isAlive;

    public Ship(ShipType shipType, int row1, int row2, int column1, int column2) {
        this.shipType = shipType;
        this.row1 = row1;
        this.row2 = row2;
        this.column1 = column1;
        this.column2 = column2;

        injuries = 0;
        isAlive = true;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public boolean isShipHit(int shotRow, int shotColumn) {
        if (shotRow >= row1 && shotRow <= row2 && shotColumn >= column1 && shotColumn <= column2) {
            injuries++;
            if (shipType.getSize() == injuries) {
                isAlive = false;
            }
            return true;
        }
        return false;
    }
}
