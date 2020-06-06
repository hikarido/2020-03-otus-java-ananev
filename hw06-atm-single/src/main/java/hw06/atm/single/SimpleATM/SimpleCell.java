package hw06.atm.single.SimpleATM;

import hw06.atm.single.Cell;
import hw06.atm.single.CellInfo;
import hw06.atm.single.Denomination;
import hw06.atm.single.exceptions.CellIsFullException;
import hw06.atm.single.exceptions.MoneyAreNotEnoughToRetrieve;

public class SimpleCell implements Cell {

    public SimpleCell(Denomination denomination, int amountAlreadyIn, int cellCapacity){
        if(amountAlreadyIn > cellCapacity){
            throw new IllegalArgumentException("Cell can't contains more then it's physical size");
        }

        if(amountAlreadyIn < 0){
            throw new IllegalArgumentException("Can't accept negative amount of money");
        }

        if(cellCapacity <= 0){
            throw new IllegalArgumentException("Cell capacity must be greater then 0");
        }

        capacity = cellCapacity;
        size = amountAlreadyIn;
        this.denomination = denomination;
    }

    @Override
    public int get(int amount) {
        if(size < amount){
            throw new MoneyAreNotEnoughToRetrieve();
        }

        if(amount < 0){
            throw new  IllegalArgumentException("Can't retrieve negative amount");
        }

        size -= amount;
        return amount;
    }

    @Override
    public void add(int amount) {
        if(amount + size > capacity){
            throw new CellIsFullException();
        }

        size += amount;
    }

    @Override
    public CellInfo getCellInfo() {
        return new SimpleCellInfo(size, capacity, denomination);
    }

    private final int capacity;
    private int size;
    private Denomination denomination;
}