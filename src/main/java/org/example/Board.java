package org.example;

import java.util.*;

public class Board {
     private Long boardSize;
     private Queue<Player> playerQueue;
     private List<Jumper> snakes;
     private List<Jumper> ladders;

     private List<Long> crocodilePosition;
     private Dice dice;
     private HashMap<String,Long> playerCurrentPosition;

    public Board(Long boardSize, Queue<Player> playerQueue, List<Jumper> snakes, List<Jumper> ladders, Dice dice, HashMap<String, Long> playerCurrentPosition,List<Long> crocodilePosition) {
        this.boardSize = boardSize;
        this.playerQueue = playerQueue;
        this.snakes = snakes;
        this.ladders = ladders;
        this.dice = dice;
        this.playerCurrentPosition = playerCurrentPosition;
        this.crocodilePosition = crocodilePosition;
    }

    public void startGame() throws Exception{
        System.out.println("Inside startGame()");
        while(!playerQueue.isEmpty()){
            Player player = playerQueue.poll();
            Long diceValue = dice.rollDice();
            System.out.println(player.getPlayerName()+ " has rolled the dice with dice value "+diceValue);
            Long nextCell = playerCurrentPosition.get(player.getPlayerName()) + diceValue;
            if(Objects.equals(nextCell, boardSize)){
                System.out.println(player.getPlayerName()+ " has won the game");
                return;
            }else if(nextCell>boardSize){
                playerQueue.offer(player);
            }else{
                Long nextPosition = nextCell;
                nextPosition=checkIfBittenByCrocodile(player,nextPosition);
                checkForOtherPlayerInNextCell(player,nextPosition);
                nextPosition = checkIfBittenBySnake(player,nextPosition);
                nextPosition = checkIfFoundLadder(player,nextPosition);
                System.out.println(player.getPlayerName()+ " moves to position "+nextPosition);
                if(Objects.equals(nextPosition, boardSize)){
                    System.out.println(player.getPlayerName()+ " has won the game");
                    return;
                }
                playerCurrentPosition.put(player.getPlayerName(),nextPosition);
                playerQueue.offer(player);
            }
            System.out.println();
        }
        System.out.println("Exit startGame()");
    }

    private long checkIfBittenByCrocodile(Player player,Long nextPosition){
        for (Long crocodile : crocodilePosition) {
            if (Objects.equals(nextPosition, crocodile)) {
                System.out.println(player.getPlayerName()+ " got bitten by crocodile at position "+nextPosition);
                nextPosition -= 5;
            }
        }
        return nextPosition;
    }
    private void checkForOtherPlayerInNextCell(Player player,Long nextPosition){
        for(HashMap.Entry<String,Long> entry : playerCurrentPosition.entrySet()){
            if(!(Objects.equals(player.getPlayerName(),entry.getKey())) && Objects.equals(entry.getValue(), nextPosition)){
                System.out.println("Resetting position of "+entry.getKey()+" to 1");
                playerCurrentPosition.put(entry.getKey(),1L);
            }
        }
    }

    private Long checkIfBittenBySnake(Player player,Long nextPosition){
        for(Jumper snake : snakes){
            if(Objects.equals(snake.getStartPoint(), nextPosition)){
                System.out.println(player.getPlayerName()+ " got bitten by snake at position "+nextPosition);
                nextPosition = snake.getEndPoint();
                checkForOtherPlayerInNextCell(player,nextPosition);
            }
        }
        return nextPosition;
    }

    private Long checkIfFoundLadder(Player player,Long nextPosition){
        for(Jumper ladder : ladders){
            if(Objects.equals(ladder.getStartPoint(), nextPosition)){
                System.out.println(player.getPlayerName()+ " takes the ladder at position "+nextPosition);
                nextPosition = ladder.getEndPoint();
                checkForOtherPlayerInNextCell(player,nextPosition);
            }
        }
        return nextPosition;
    }

    public Long getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(Long boardSize) {
        this.boardSize = boardSize;
    }

    public Queue<Player> getPlayerQueue() {
        return playerQueue;
    }

    public void setPlayerQueue(Queue<Player> playerQueue) {
        this.playerQueue = playerQueue;
    }

    public List<Jumper> getSnakes() {
        return snakes;
    }

    public void setSnakes(List<Jumper> snakes) {
        this.snakes = snakes;
    }

    public List<Jumper> getLadders() {
        return ladders;
    }

    public void setLadders(List<Jumper> ladders) {
        this.ladders = ladders;
    }

    public Dice getDice() {
        return dice;
    }

    public void setDice(Dice dice) {
        this.dice = dice;
    }

    public HashMap<String, Long> getPlayerCurrentPosition() {
        return playerCurrentPosition;
    }

    public void setPlayerCurrentPosition(HashMap<String, Long> playerCurrentPosition) {
        this.playerCurrentPosition = playerCurrentPosition;
    }

    public List<Long> getCrocodilePosition() {
        return crocodilePosition;
    }

    public void setCrocodilePosition(List<Long> crocodilePosition) {
        this.crocodilePosition = crocodilePosition;
    }
}
