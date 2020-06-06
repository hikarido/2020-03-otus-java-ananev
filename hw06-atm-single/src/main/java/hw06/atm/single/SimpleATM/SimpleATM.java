package hw06.atm.single.SimpleATM;

import hw06.atm.single.*;
import hw06.atm.single.exceptions.CantExtractRequiredAmount;
import hw06.atm.single.exceptions.SpaceInCellsIsNotEnough;
import hw06.atm.single.exceptions.ThereIsNoAppropriateDenominationCell;

import java.util.List;

/**
 * SimpleATM
 * Sample of usage interface hierarchy
 * It uses: {@link SimpleStoreInfo}, {@link SimpleStore}, {@link SimpleCellInfo}, {@link SimpleCell}
 * All his part are simple
 */
public class SimpleATM implements ClientATM, ServiceATM{

    public SimpleATM(){
        store = new SimpleStore();
    }

    public SimpleATM(Store store){
        this.store = store;
    }

    @Override
    public void replenish(List<Denomination> money) throws ThereIsNoAppropriateDenominationCell, SpaceInCellsIsNotEnough {
        store.addAll(money);
    }

    @Override
    public List<Denomination> withdraw(int sum) throws CantExtractRequiredAmount {
        return store.get(sum, null);
    }

    @Override
    public int getBalance() {
        int balance = 0;
        for(int id: store.getStoreInfo().getCellIdentifiers()){
            CellInfo info = store.getStoreInfo().getCellInfo(id);
            balance += info.getDenomination().value() * info.getSize();
        }

        return balance;
    }

    @Override
    public StoreInfo getStorageInfo() {
        return store.getStoreInfo();
    }

    @Override
    public Store changeStore(Store newStore) {
        Store old = store;
        store = newStore;
        return old;
    }

    private Store store;
}