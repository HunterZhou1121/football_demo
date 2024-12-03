package org.example.football_backend_docker;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "players")  // Add this line to specify the table name
public class Player {
    @Id
    private String playerName;

    private int playerAge;

    private String playerClub;

    private String playerCountry;

    // Getters and setters

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPlayerAge() {
        return playerAge;
    }

    public void setPlayerAge(int playerAge) {
        this.playerAge = playerAge;
    }

    public String getPlayerClub() {
        return playerClub;
    }

    public void setPlayerClub(String playerClub) {
        this.playerClub = playerClub;
    }

    public String getPlayerCountry() {
        return playerCountry;
    }

    public void setPlayerCountry(String playerCountry) {
        this.playerCountry = playerCountry;
    }

    // Add this constructor
    public Player() {
    }

    // Add this constructor
    public Player(String playerName, int playerAge, String playerClub, String playerCountry) {
        this.playerName = playerName;
        this.playerAge = playerAge;
        this.playerClub = playerClub;
        this.playerCountry = playerCountry;
    }

}
