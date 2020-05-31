package hw06.atm.single.exceptions;

import hw06.atm.single.Cell;

/**
 * You can't add money to cell which is full
 */
public class CellIsFullException extends RuntimeException {

    public CellIsFullException(){
        super("Cell is full");
    }

    public CellIsFullException(String msg){
        super(msg);
    }
}
