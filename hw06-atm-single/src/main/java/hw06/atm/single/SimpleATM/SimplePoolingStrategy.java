package hw06.atm.single.SimpleATM;

import hw06.atm.single.Denomination;
import hw06.atm.single.MoneyExtractStrategy;
import hw06.atm.single.Store;

import java.util.List;

public class SimplePoolingStrategy implements MoneyExtractStrategy {
    @Override
    public List<Denomination> extract(Store store, int sum) {
        return null;
    }
}
