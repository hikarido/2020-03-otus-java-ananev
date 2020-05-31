package hw06.atm.single;

public interface CellInfo {
    Denomination getDenomination();
    int getSize();
    int getCapacity();
    boolean isFull();
}
