package hw06.atm.single.exceptions;

/**
 * Main ATM exception
 */
public class ATMException extends RuntimeException{
    ATMException(){
        super();
    }

    ATMException(String msg){
        super(msg);
    }
}
