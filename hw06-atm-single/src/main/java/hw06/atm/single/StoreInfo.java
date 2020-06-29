package hw06.atm.single;

import java.util.List;

/**
 * Summary information about Store
 */
public interface StoreInfo {
    int getCellAmount();
    List<Integer> getCellIdentifiers();
    CellInfo getCellInfo(int cellId);
}