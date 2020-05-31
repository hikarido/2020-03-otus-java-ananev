package hw06.atm.single;

import hw06.atm.single.exceptions.CantExtractRequiredAmount;
import hw06.atm.single.exceptions.SpaceInCellsIsNotEnough;
import hw06.atm.single.exceptions.ThereIsNoAppropriateDenominationCell;

import java.util.List;

/**
 * Main interface to ATM for Customers.
 * ATM contains {@link Store} which manages money in {@link Cell}
 * ATM interface must have constructor of {@link Store}
 *
 * If you ATM user you can see residual(getBalance), add(replenish), and get(withdraw) money.
 */
public interface ClientATM{
    void replenish(List<Denomination> money) throws ThereIsNoAppropriateDenominationCell, SpaceInCellsIsNotEnough;
    List<Denomination> withdraw(int sum) throws CantExtractRequiredAmount;
    int getBalance();

}

