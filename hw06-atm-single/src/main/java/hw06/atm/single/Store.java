package hw06.atm.single;

import hw06.atm.single.exceptions.SpaceInCellsIsNotEnough;
import hw06.atm.single.exceptions.ThereIsNoAppropriateDenominationCell;

import java.util.List;

/**
 * Store must have constructor of {@link List} of {@link Cell};
 */
public interface Store {
    void add(Denomination bankNote) throws ThereIsNoAppropriateDenominationCell, SpaceInCellsIsNotEnough;
    void addAll(List<Denomination> money) throws ThereIsNoAppropriateDenominationCell, SpaceInCellsIsNotEnough;
    List<Denomination> get(MoneyExtractStrategy extractWay);

    void insertCell(Cell cell);
    Cell extractCell(int  cellId);

    StoreInfo getStoreInfo();
}
