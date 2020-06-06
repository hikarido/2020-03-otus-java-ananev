package hw06.atm.single.SimpleATM;

import hw06.atm.single.*;
import hw06.atm.single.exceptions.CantExtractRequiredAmount;
import hw06.atm.single.exceptions.SpaceInCellsIsNotEnough;
import hw06.atm.single.exceptions.ThereIsNoAppropriateDenominationCell;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SimpleStore implements Store {

    {
        lastCellId = 0;
    }
    public SimpleStore(List<Cell> cells){
        if(cells == null || cells.size() == 0){
           throw new IllegalArgumentException("Store must contains at least one cell");
        }

        init(cells);
    }

    public SimpleStore(){
        init(Arrays.asList(
                new SimpleCell(Denomination.ONE, 1000, 1000),
                new SimpleCell(Denomination.THOUSAND, 1000, 1000)
        ));
    }

    @Override
    public void add(Denomination bankNote) throws ThereIsNoAppropriateDenominationCell, SpaceInCellsIsNotEnough {
        if(canBeAdded(Arrays.asList(bankNote))){
            List<Cell> appropriateCells = searchAllAppropriateCells(bankNote);
            for(Cell cell: appropriateCells){
                if(!cell.getCellInfo().isFull()){
                    cell.add(1);
                    break;
                }
            }
        }
    }

    @Override
    public void addAll(List<Denomination> money) throws ThereIsNoAppropriateDenominationCell, SpaceInCellsIsNotEnough {
        if(canBeAdded(money)){
            for(Denomination banknote: money){
                List<Cell> cells = searchAllAppropriateCells(banknote);
                for(Cell cell: cells){
                    if(!cell.getCellInfo().isFull()){
                        cell.add(1);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public List<Denomination> get(int sum, MoneyExtractStrategy extractWay) throws CantExtractRequiredAmount {
        if(sum <= 0){
            throw new IllegalArgumentException("Required sum must be greater then zero");
        }

        if(extractWay == null){
            extractWay = new SimplePoolingStrategy();
        }

        List<Denomination> money = extractWay.extract(new ArrayList<>(cells.values()), sum);
        return money;
    }

    @Override
    public void insertCell(Cell cell) {
        if(cell == null){
            throw new IllegalArgumentException("Cell can't vbe null");
        }
        cells.put(lastCellId, cell);
        lastCellId++;
    }

    @Override
    public Cell extractCell(int cellId) {
        Cell cell =  cells.get(cellId);
        cells.remove(cellId);
        return cell;
    }

    @Override
    public StoreInfo getStoreInfo() {
        return new SimpleStoreInfo(Collections.unmodifiableMap(cells));
    }

    private void init(List<Cell> cells){
        this.cells = new HashMap<Integer, Cell>();
        for(Cell cell: cells){
            this.cells.put(lastCellId, cell);
            lastCellId++;
        }
    }

    private boolean canBeAdded(List<Denomination> banknotes) throws ThereIsNoAppropriateDenominationCell, SpaceInCellsIsNotEnough {
        Map<Denomination, Long> bar = banknotes
                .stream()
                .collect(
                        Collectors.groupingBy(
                                Function.identity(), Collectors.counting()
                        )
                );

        for(Map.Entry<Denomination, Long> denominationAmount: bar.entrySet()){
            Denomination denomination = denominationAmount.getKey();
            Long amount = denominationAmount.getValue();
            List<Cell> appropriateCells = searchAllAppropriateCells(denomination);
            if(appropriateCells.isEmpty()){
                throw new ThereIsNoAppropriateDenominationCell("Store have no cell for " +denomination.name());
            }

            int availableSpaceForDenomination = appropriateCells
                    .stream()
                    .map(Cell::getCellInfo)
                    .mapToInt(c ->c.getCapacity() - c.getSize())
                    .sum();

            if(availableSpaceForDenomination < amount){
                throw new SpaceInCellsIsNotEnough();
            }
        }

        return true;
    }

    private List<Cell> searchAllAppropriateCells(Denomination denomination){
        List<Cell> appropriateCells = new ArrayList<>();
        for(Map.Entry<Integer, Cell> cellData: cells.entrySet()){
            Cell cell = cellData.getValue();
            if(cell.getCellInfo().getDenomination() == denomination){
                appropriateCells.add(cell);
            }
        }

        return appropriateCells;
    }

    private Map<Integer, Cell> cells;
    private int lastCellId;
}