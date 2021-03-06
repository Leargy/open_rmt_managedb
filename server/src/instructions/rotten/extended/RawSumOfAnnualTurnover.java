package instructions.rotten.extended;

import instructions.rotten.RawDecree;

import java.io.Serializable;

public final class RawSumOfAnnualTurnover extends RawDecree implements Serializable {
    public static final String NAME = "sum_of_annual_turnover";
    public static final String BRIEF = "выводит сумму поля \\\"turnover\\\" всех элементов коллекции";
    public static final String SYNTAX = NAME;
    public static final int ARGNUM = 0;
    public static boolean isArgumented() { return ARGNUM > 0; }
}
