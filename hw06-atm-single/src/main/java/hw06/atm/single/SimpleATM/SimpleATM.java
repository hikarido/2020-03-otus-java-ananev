package hw06.atm.single.SimpleATM;

import hw06.atm.single.*;

import java.util.List;

public class SimpleATM implements ClientATM, ServiceATM{

    @Override
    public void replenish(List<Denomination> money) {

    }

    @Override
    public List<Denomination> withdraw(int sum) {
        return null;
    }

    @Override
    public String getBalance() {
        return null;
    }

    @Override
    public StoreInfo getStorageInfo() {
        return null;
    }

    @Override
    public Store changeStore(Store newStore) {
        return null;
    }
}
