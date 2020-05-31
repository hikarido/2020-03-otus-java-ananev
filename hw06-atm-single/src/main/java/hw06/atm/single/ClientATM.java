package hw06.atm.single;

import java.util.List;

/**
 * Main interface to ATM for Customers.
 * ATM contains {@link Store} which manages money in {@link Cell}
 * ATM interface must have constructor of {@link Store}
 *
 * If you ATM user you can see residual(getBalance), add(replenish), and get(withdraw) money.
 */
public interface ClientATM{
    void replenish(List<Denomination> money);
    List<Denomination> withdraw(int sum);
    String getBalance();

}

