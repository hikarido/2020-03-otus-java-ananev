package hw06.atm.single;

import java.util.List;

/**
 * Store must have constructor of {@link List} of {@link Cell};
 */
public interface Store {
    void add(Denomination bankNote);
    void addAll(List<Denomination> money);
    List<Denomination> get(MoneyExtractStrategy extractWay);

    void insertCell(Cell cell);
    Cell extractCell(int  cellId);

    StoreInfo getStoreInfo();
}
