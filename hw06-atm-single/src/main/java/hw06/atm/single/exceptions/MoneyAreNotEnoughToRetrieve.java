package hw06.atm.single.exceptions;

public class MoneyAreNotEnoughToRetrieve extends RuntimeException{
    public MoneyAreNotEnoughToRetrieve(){
        super("Cell have no enough money to retrieve required sum");
    }
}
