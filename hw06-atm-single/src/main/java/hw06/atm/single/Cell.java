package hw06.atm.single;

/**
 * Must have constructor with:
 *     * banknote {@link Denomination} of cell
 *     * init amount in it
 *     * maximum capacity
 */
interface Cell {
    int get(int amount);
    void add(int amount);
    CellInfo getCellInfo();
}
