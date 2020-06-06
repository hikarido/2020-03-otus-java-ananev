package hw06.atm.single;

/**
 * Denomination of banknotes
 */
public enum Denomination {
    ONE(1),
    FIVE(5),
    TEN(10),
    FIFTY(50),
    HUNDRED(100),
    FIVE_HUNDRED(500),
    THOUSAND(1000);

    private final int value;

    Denomination(int howMuchItWorths){
        value = howMuchItWorths;
    }

    public int value(){
        return value;
    }
}