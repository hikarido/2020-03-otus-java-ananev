package hw06.atm.single.exceptions;

/**
 * If there is no required SUM of money in all cells
 */
public class CantExtractRequiredAmount extends ATMException{
    public CantExtractRequiredAmount(String msg){
        super(msg);
    }
}