package org.example;

public class Dice {

    private Long diceCount;
    private String movementStrategy;

    public Dice(Long diceCount, String movementStrategy) {
        this.diceCount = diceCount;
        this.movementStrategy = movementStrategy;
    }

    public Long rollDice() throws Exception{
        return switch (this.movementStrategy) {
            case ("MAX") -> diceValueMaxStrategy();
            case ("MIN") -> diceValueMinStrategy();
            case ("SUM") -> diceValueSumStrategy();
            case null, default -> throw new Exception("Wrong movement strategy.");
        };
    }

    private Long diceValueMaxStrategy(){
        long valueFromDice = Long.MIN_VALUE;
        for(int i=0;i<this.diceCount;i++){
            valueFromDice = Math.max(valueFromDice,getNumberOnDice());
        }
        return valueFromDice;
    }

    private Long diceValueMinStrategy(){
        long valueFromDice = Long.MAX_VALUE;
        for(int i=0;i<this.diceCount;i++){
            valueFromDice = Math.min(valueFromDice,getNumberOnDice());
        }
        return valueFromDice;
    }

    private Long diceValueSumStrategy(){
        long valueFromDice = 0;
        for(int i=0;i<this.diceCount;i++){
            valueFromDice += getNumberOnDice();
        }
        return valueFromDice;    }

    private long getNumberOnDice(){
        return ((long)(Math.random()*(7-1)))+1;
    }

    public Long getDiceCount() {
        return diceCount;
    }

    public void setDiceCount(Long diceCount) {
        this.diceCount = diceCount;
    }

    public String getMovementStrategy() {
        return movementStrategy;
    }

    public void setMovementStrategy(String movementStrategy) {
        this.movementStrategy = movementStrategy;
    }
}
