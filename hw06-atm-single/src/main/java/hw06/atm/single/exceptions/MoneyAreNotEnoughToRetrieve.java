package hw06.atm.single.exceptions;

/**
 * if one cell has no enough banknotes
 */
public class MoneyAreNotEnoughToRetrieve extends ATMException{
    public MoneyAreNotEnoughToRetrieve(){
        super("Cell have no enough money to retrieve required sum");
    }
}