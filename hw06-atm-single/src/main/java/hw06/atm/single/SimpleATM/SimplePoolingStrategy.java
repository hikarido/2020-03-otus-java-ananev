package hw06.atm.single.SimpleATM;

import hw06.atm.single.Cell;
import hw06.atm.single.Denomination;
import hw06.atm.single.MoneyExtractStrategy;
import hw06.atm.single.Store;
import hw06.atm.single.exceptions.CantExtractRequiredAmount;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimplePoolingStrategy implements MoneyExtractStrategy {

    @Override
    public List<Denomination> extract(List<Cell> cells, int sum) throws CantExtractRequiredAmount {
        int thousandsCount = sum / 1000;
        int onesCout = sum - thousandsCount * 1000;
        boolean thousandsOK = false;
        boolean onesOk = false;

        Cell thousands = null;
        Cell ones = null;

        for(Cell cell: cells){
            if(cell.getCellInfo().getDenomination() == Denomination.THOUSAND){
                thousands = cell;
            }

            if(cell.getCellInfo().getDenomination() == Denomination.ONE){
                ones = cell;
            }
        }

        if(thousands.getCellInfo().getSize() - thousandsCount >= 0
                && ones.getCellInfo().getSize() - onesCout >= 0){

            thousands.get(thousandsCount);
            ones.get(onesCout);

            List<Denomination> money = new ArrayList<>();
            money.addAll(Collections.nCopies(thousandsCount, Denomination.THOUSAND));
            money.addAll(Collections.nCopies(onesCout, Denomination.ONE));

            return money;
        }

        throw new CantExtractRequiredAmount("Simple strategy fall");
    }
}