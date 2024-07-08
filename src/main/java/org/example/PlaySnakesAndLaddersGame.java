package org.example;

import netscape.javascript.JSObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;


public class PlaySnakesAndLaddersGame {

    public static void main(String[] args) {
        try{
            Board board;
            String jsonString = getJSONFromFile("/Users/krrai/IdeaProjects/NewSnakeAndLadder/src/main/resources/GameInput.json");
            if(jsonString.isEmpty()){
                System.out.println("Configurations not found");
                return;
            }
            JSONParser parser = new JSONParser();
            try{
                JSONObject object = (JSONObject) parser.parse(jsonString);

                Long boardSize =  (Long)object.get("BoardSize");
                System.out.println("BoardSize="+boardSize);

                Long numberOfDices=  (Long)object.get("NumberOfDice");
                System.out.println("Number of Dices="+numberOfDices);
                if(numberOfDices<1L){
                    System.out.println("Number of Dice should be atleast 1");
                    return;
                }

                Long numberOfPlayers =  (Long)object.get("NumberOfPlayers");
                System.out.println("Number of Players="+numberOfPlayers);
                ArrayList<Object> playerDetails = inputPlayerDetails(numberOfPlayers);
                Queue<Player> playersQueue = (Queue<Player>) playerDetails.get(0);
                HashMap<String,Long> playersPosition = (HashMap<String,Long>) playerDetails.get(1);

                Long numberOfSnakes=  (Long)object.get("NumberOfSnakes");
                System.out.println("Number of Snakes="+numberOfSnakes);

                JSONArray snakesArray=  (JSONArray) object.get("SnakePositions");
                List<Jumper> snakesList = inputSnakePositions(snakesArray);

                Long numberOfLadders=  (Long)object.get("NumberOfLadders");
                System.out.println("Number of Ladders="+numberOfLadders);

                JSONArray laddersArray=  (JSONArray) object.get("LadderPositions");
                List<Jumper> laddersList = inputLadderPositions(laddersArray);

                JSONArray crocodileArray=  (JSONArray) object.get("CrocodilePositions");
                List<Long> crocodilePositions = inputCrocodilePositions(crocodileArray);
                String movementStrategy=  (String)object.get("MovementStrategy");
                System.out.println("Movement Strategy="+movementStrategy);

                Dice dice = new Dice(numberOfDices,movementStrategy);
                board = new Board(boardSize, playersQueue, snakesList, laddersList, dice, playersPosition,crocodilePositions);

            }catch (Exception e){
                System.out.println("Exception while parsing the json config "+e);
                return;
            }
            board.startGame();
        }catch(Exception e){
            System.out.println("Exception occurred "+e);
        }

    }

    private static ArrayList<Object> inputPlayerDetails(Long numberOfPlayers){
        Scanner sc = new Scanner(System.in);
        Queue<Player> players = new LinkedList<>();
        ArrayList<Object> objectList = new ArrayList<>();
        HashMap<String,Long> playersPosition = new HashMap<>();
        for(int i=0;i<numberOfPlayers;i++){
            long id = i+1L;
            System.out.println("Enter player name for id="+id+"?");
            String name = sc.next();
            Player player = new Player(id,name);
            players.offer(player);
            playersPosition.put(name,0L);
        }
        objectList.add(players);
        objectList.add(playersPosition);
        return objectList;
    }


    private static List<Jumper> inputSnakePositions(JSONArray snakesArray){
        List<Jumper> snakes = new ArrayList<>();
        for(int i=0;i<snakesArray.size();i++){
            JSONObject object = (JSONObject)snakesArray.get(i);
            Jumper snake = new Jumper((Long)object.get("StartPoint"),(Long)object.get("EndPoint"));
            snakes.add(snake);
        }
        return snakes;
    }

    private static List<Jumper> inputLadderPositions(JSONArray laddersArray){
        List<Jumper> ladders = new ArrayList<>();
        for(int i=0;i<laddersArray.size();i++){
            JSONObject object = (JSONObject)laddersArray.get(i);
            Jumper ladder = new Jumper((Long)object.get("StartPoint"),(Long)object.get("EndPoint"));
            ladders.add(ladder);
        }
        return ladders;
    }

    private static List<Long> inputCrocodilePositions(JSONArray crocodilesArray){
        List<Long> crocodilePositions = new ArrayList<>();
        for(int i=0;i<crocodilesArray.size();i++){
            Long position = (Long)((JSONObject)crocodilesArray.get(i)).get("Position");
            crocodilePositions.add(position);
        }
        return crocodilePositions;
    }

    private static String getJSONFromFile(String filename){
        StringBuilder jsonText = new StringBuilder();
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            String line;
            while((line=bufferedReader.readLine())!=null){
                jsonText.append(line).append("\n");
            }
        }catch (Exception e){
            System.out.println("Exception while reading the file "+e);
        }
        return jsonText.toString();
    }

}