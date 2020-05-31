package hw06.atm.single;

import java.util.List;

public interface StoreInfo {
    int getCellAmount();
    List<Integer> getCellIdentifiers();
    CellInfo getCellInfo(int cellId);
}
