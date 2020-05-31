package hw06.atm.single.exceptions;

public class SpaceInCellsIsNotEnough extends Exception{
    public SpaceInCellsIsNotEnough(){
        super("Storage have no enough cells space for storing required amount of money");
    }
}
