package hw06.atm.single;

/**
 * Main interface to ATM for Employees.
 * ATM contains {@link Store} which manages money in {@link Cell}
 * ATM interface must have constructor of {@link Store}
 *
 * If you service ATM you can replace storage(changeStore), see storage state (getStorageInfo)
 */
public interface ServiceATM{
    StoreInfo getStorageInfo();
    Store changeStore(Store newStore);
}
