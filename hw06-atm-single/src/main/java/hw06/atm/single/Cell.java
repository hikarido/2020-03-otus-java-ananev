package hw06.atm.single;

/**
 *
 * The money place. Supplies access to money
 * Must have constructor with:
 *     * banknote {@link Denomination} of cell
 *     * init amount in it
 *     * maximum capacity
 */
public interface Cell {
    int get(int amount);
    void add(int amount);
    CellInfo getCellInfo();
}
