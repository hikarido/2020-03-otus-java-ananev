package hw06.atm.single.SimpleATM;

import hw06.atm.single.CellInfo;
import hw06.atm.single.Denomination;

public class SimpleCellInfo implements CellInfo {

    public SimpleCellInfo(int size, int capacity, Denomination denomination){
        this.size = size;
        this.capacity = capacity;
        this.denomination = denomination;
    }

    @Override
    public Denomination getDenomination() {
        return denomination;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public boolean isFull() {
        return size == capacity;
    }

    @Override
    public String toString(){
        return "Denomination: " + denomination + " size: " + size + " capacity: " + capacity;
    }

    private final int size;
    private final int capacity;
    private final Denomination denomination;
}