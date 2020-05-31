package hw06.atm.single.exceptions;

/**
 * If your Store has no cell of demanded {@link hw06.atm.single.Denomination}
 */
public class ThereIsNoAppropriateDenominationCell extends Exception{
    public ThereIsNoAppropriateDenominationCell(String msg){
        super(msg);
    }
}
