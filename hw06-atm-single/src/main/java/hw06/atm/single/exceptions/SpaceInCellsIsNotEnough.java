package hw06.atm.single.exceptions;

/**
 * If required amount of banknotes cant be placed in cell
 */
public class SpaceInCellsIsNotEnough extends ATMException{
    public SpaceInCellsIsNotEnough(){
        super("Storage have no enough cells space for storing required amount of money");
    }
}
