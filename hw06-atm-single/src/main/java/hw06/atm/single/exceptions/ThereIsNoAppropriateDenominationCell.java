package hw06.atm.single.exceptions;

public class ThereIsNoAppropriateDenominationCell extends Exception{
    public ThereIsNoAppropriateDenominationCell(String msg){
        super(msg);
    }
}
