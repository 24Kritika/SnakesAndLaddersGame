package org.example;

public class Player {

    private Long id;
    private String playerName;

    public Player(Long id, String playerName){
        this.id=id;
        this.playerName=playerName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
