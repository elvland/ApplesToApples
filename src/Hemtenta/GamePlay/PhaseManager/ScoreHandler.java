package Hemtenta.GamePlay.PhaseManager;

import Hemtenta.Factories.PlayerFactory.PlayedCard;
import Hemtenta.Factories.PlayerFactory.Player;

import java.util.ArrayList;
/**
 * The ScoreHandler class manage the score during the game and game ending conditions
 */
public class ScoreHandler {
    private final ArrayList<Player> playerList ;
    private Player gameWinner ;
    public ScoreHandler(ArrayList<Player> playerList){
        this.playerList = playerList;
    }

    /**
     * Adds a won green card to the player's hand and updates score.
     * @param redWinningCard The card that won the round.
     * @param greenAppleCard The question card for the current round.
     */
    protected void updateScores(PlayedCard redWinningCard, String greenAppleCard) {
        int winningPlayerID = redWinningCard.getPlayerID();
        Player winningPlayer = playerList.get(winningPlayerID);
        winningPlayer.addScore(greenAppleCard);
    }

    /**
     * Checks if a player has enough score (green apple cards) to win the game.
     * @param winningScore The total score needed to win the game.
     * @return true if enough score, and therefore game over , false otherwise.
     */
    protected boolean isGameFinished(int winningScore) {
        for (Player player : playerList) {
            if (player.getScore() >= winningScore) {
                this.gameWinner = player;
                return true;
            }
        }
        return false;
    }

    protected Player getWinner() {
        if (this.gameWinner != null) {
            return this.gameWinner;
        }
        return null;
    }

}
