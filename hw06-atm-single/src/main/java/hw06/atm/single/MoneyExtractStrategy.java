package hw06.atm.single;

import hw06.atm.single.exceptions.CantExtractRequiredAmount;

import java.util.List;

/**
 * You can collect money in different ways by implementing this strategy
 */
@FunctionalInterface
public interface MoneyExtractStrategy {
    List<Denomination> extract(List<Cell> cells, int sum) throws CantExtractRequiredAmount;
}