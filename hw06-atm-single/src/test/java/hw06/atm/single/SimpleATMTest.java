package hw06.atm.single;


import hw06.atm.single.SimpleATM.*;

import hw06.atm.single.exceptions.CantExtractRequiredAmount;
import hw06.atm.single.exceptions.SpaceInCellsIsNotEnough;
import hw06.atm.single.exceptions.ThereIsNoAppropriateDenominationCell;
import org.junit.Test;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SimpleATMTest {
    @Test
    public void createATM(){
        SimpleATM atm = new SimpleATM();
        System.out.println(atm.getStorageInfo());
        System.out.println(atm.getBalance());
    }

    @Test
    public void getOne(){
        ClientATM atm = new SimpleATM();
        List<Denomination> expected = new ArrayList<>();
        expected.add(Denomination.ONE);
        int expectedResidual = atm.getBalance() - 1;

        try {
            List<Denomination> result = atm.withdraw(1);
            Assert.assertArrayEquals(expected.toArray(), result.toArray());
            Assert.assertEquals(expectedResidual, atm.getBalance());
        } catch (CantExtractRequiredAmount cantExtractRequiredAmount) {
            throw new RuntimeException(cantExtractRequiredAmount);
        }
    }

    @Test
    public void addOneAfterWithdraw(){
        ClientATM atm = new SimpleATM();
        int expectedBalance = atm.getBalance();
        try {
            List<Denomination> notes = atm.withdraw(1);
            atm.replenish(notes);
            Assert.assertEquals(expectedBalance, atm.getBalance());
        } catch (CantExtractRequiredAmount | SpaceInCellsIsNotEnough | ThereIsNoAppropriateDenominationCell
                cantExtractRequiredAmount) {
            throw new RuntimeException(cantExtractRequiredAmount);
        }
    }

    @Test
    public void add1001(){
        ServiceATM atm = new SimpleATM();
        List<Cell> cells = Arrays.asList(
                new SimpleCell(Denomination.ONE, 0, 1000),
                new SimpleCell(Denomination.THOUSAND, 0, 1000)
        );
        SimpleStore storeWithEmptyCells = new SimpleStore(cells);
        atm.changeStore(storeWithEmptyCells);
        ClientATM clientAtm = (ClientATM) atm;
        int expectedBalance = 1001;
        try {
            clientAtm.replenish(Arrays.asList(Denomination.ONE, Denomination.THOUSAND));
            Assert.assertEquals(expectedBalance, clientAtm.getBalance());

            for(int id: atm.getStorageInfo().getCellIdentifiers()){
                CellInfo info = atm.getStorageInfo().getCellInfo(id);
                if (info.getDenomination() == Denomination.ONE){
                    Assert.assertEquals(info.getSize(), 1);
                }

                if(info.getDenomination() == Denomination.THOUSAND){
                    Assert.assertEquals(info.getSize(),1);
                }
            }
        } catch (SpaceInCellsIsNotEnough | ThereIsNoAppropriateDenominationCell
                cantExtractRequiredAmount) {
            throw new RuntimeException(cantExtractRequiredAmount);
        }
    }
}