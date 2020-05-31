package hw06.atm.single;

import java.util.List;

@FunctionalInterface
public interface MoneyExtractStrategy {
    List<Denomination> extract(Store store, int sum);
}
