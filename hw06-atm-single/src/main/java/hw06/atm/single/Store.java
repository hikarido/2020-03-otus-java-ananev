package hw06.atm.single;

import hw06.atm.single.exceptions.CantExtractRequiredAmount;
import hw06.atm.single.exceptions.SpaceInCellsIsNotEnough;
import hw06.atm.single.exceptions.ThereIsNoAppropriateDenominationCell;

import java.util.List;

/**
 * The place for money cells
 * Store contains {@link Cell}
 * You can as make money operation on Store as replace or retrieve cells
 * Store must have constructor of {@link List} of {@link Cell};
 */
public interface Store {
    void add(Denomination bankNote) throws ThereIsNoAppropriateDenominationCell, SpaceInCellsIsNotEnough;
    void addAll(List<Denomination> money) throws ThereIsNoAppropriateDenominationCell, SpaceInCellsIsNotEnough;
    List<Denomination> get(int sum, MoneyExtractStrategy extractWay) throws CantExtractRequiredAmount;

    void insertCell(Cell cell);
    Cell extractCell(int  cellId);

    StoreInfo getStoreInfo();
}