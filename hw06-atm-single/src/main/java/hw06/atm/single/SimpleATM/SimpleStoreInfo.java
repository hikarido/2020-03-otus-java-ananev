package hw06.atm.single.SimpleATM;

import hw06.atm.single.Cell;
import hw06.atm.single.CellInfo;
import hw06.atm.single.StoreInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SimpleStoreInfo implements StoreInfo {

    public SimpleStoreInfo(Map<Integer, Cell> cells){
        this.cells = cells;
    }

    @Override
    public int getCellAmount() {
        return cells.size();
    }

    @Override
    public List<Integer> getCellIdentifiers() {
        return new ArrayList<>(cells.keySet());
    }

    @Override
    public CellInfo getCellInfo(int cellId) {
        return cells.get(cellId).getCellInfo();
    }

    @Override
    public String toString(){
        StringBuilder b = new StringBuilder();
        for(Map.Entry<Integer, Cell> cellData: cells.entrySet()){
            String cellDescription = cellData.getValue().getCellInfo().toString();
            Integer cellId = cellData.getKey();
            b.append("Cell: " + cellId + " " + cellDescription + "\n");
        }

        return b.toString();
    }

    private Map<Integer, Cell> cells;
}



















