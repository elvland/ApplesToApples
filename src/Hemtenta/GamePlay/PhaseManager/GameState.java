package Hemtenta.GamePlay.PhaseManager;

import Hemtenta.Factories.CardFactory.CardDeck;

import Hemtenta.Factories.PlayerFactory.Player;

import java.util.ArrayList;
/**
 * The GameState class manages the state of the game by keeping track of game-related information,
 * such as the game over status, the list of players, and the game deck.
 */
public class GameState {

    private boolean gameOverFlag;
    private final ArrayList<Player> allPlayers;
    private final CardDeck deck;

    public GameState(ArrayList<Player> players,CardDeck deck) {
        this.deck = deck;
        this.gameOverFlag = false;
        this.allPlayers = players;
    }

    protected CardDeck getDeck() {
        return deck;
    }

    public boolean isGameOverFlag() {
        return gameOverFlag;
    }

    protected void setGameOverFlag() {
        this.gameOverFlag = true;
    }

    public ArrayList<Player> getAllPlayers() {
        return allPlayers;
    }

    protected int getHostID(){
        return allPlayers.get(allPlayers.size()-1).getPlayerId(); //HostID or Localplayer is always last player in the playerlist
    }

}
