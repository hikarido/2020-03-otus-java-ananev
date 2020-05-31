package hw06.atm.single;

/**
 * Summary information about money cell
 */
public interface CellInfo {
    Denomination getDenomination();
    int getSize();
    int getCapacity();
    boolean isFull();
}
