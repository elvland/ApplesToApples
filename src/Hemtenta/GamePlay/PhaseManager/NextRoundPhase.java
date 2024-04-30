
package Hemtenta.GamePlay.PhaseManager;

import Hemtenta.Communication.PlayerCommunicationManager;
import Hemtenta.Factories.PlayerFactory.Player;

import Hemtenta.Setup.GameSettings;


import java.util.ArrayList;
/**
 * Executes the sequence of actions for an ending game round, including drawing an extra card,
 * checking for game over, discarding played cards, updating the scoreboard, and starting a new round.
 */
public class NextRoundPhase {
    private final GameSettings gameSettings;
    private final PlayerCommunicationManager communication;
    private final GameState gameState;
    private final ScoreHandler scoreHandler;


    public NextRoundPhase(GameSettings gameSettings, PlayerCommunicationManager communication,
                          GameState gameState, ScoreHandler scoreBoard) {
        this.gameSettings = gameSettings;
        this.communication = communication;
        this.gameState = gameState;
        this.scoreHandler = scoreBoard;


    }


    public void run(RoundState roundState)  {

        if (checkGameOver(roundState)){
            return; //if Gameover then terminate the game
        }
        discardPlayedCards(roundState);
        scoreBoardHandler();
        drawExtraCard(roundState);

        newRound(roundState);

    }

    /**
     * Instantiates a new round within the game.
     * This method is responsible for swapping judge, resetting the initial variables and draws a new green apple for the new round,
     */
    private void newRound(RoundState roundState) {
      changeJudge(roundState);
      roundState.resetRound();
      roundState.setGreenAppleCard();
    }
    /**
     * Swaps the judge by and assign the judge based on players ID.
     */
    private void changeJudge(RoundState roundState){
        int roundJudge = roundState.getCurrentJudge();
        int hostID = gameState.getHostID();
        // If the current judge is the host player, assign the judge to the next playerID assigned 0,
        roundState.setJudge(roundJudge == hostID ? 0 : roundJudge + 1);
    }


    /**
     * Checks if the game is over based on the score to win set in the game settings.
     * If the game is over, the winner is determined, and relevant messages are sent to all players.
     * @return A boolean indicating if the game is over.
     */
    private boolean checkGameOver(RoundState roundState) {
        int scoreToWin = gameSettings.getScoreToWin();
        boolean isGameOver =  scoreHandler.isGameFinished(scoreToWin);

        if (isGameOver) {
            Player playerWinner = scoreHandler.getWinner();
            roundState.setGameWinner(playerWinner);
            gameState.setGameOverFlag();

            communication.sendToAllPlayers("printScoreBoard","PRINT");
            communication.sendToAllPlayers("gameOverOutput","PRINT");
            communication.sendToAllPlayers("playerWinnerOutput","GAMEOVER");

        }
    return isGameOver;
    }


    /**
     * Sends and prints the scoreboard to all players
     */
    private void scoreBoardHandler()  {
        communication.sendToAllPlayers("printScoreBoard","PRINT");
    }


    private void discardPlayedCards(RoundState roundState){
        roundState.getRoundPlayedCards().clear();
    }

     /**
     * Providing each non-judge player with a new card and adds it to corresponding hands.
     */
    private void drawExtraCard(RoundState roundState) {
        ArrayList<Player> allPlayerList = gameState.getAllPlayers();
        int fullHand = gameSettings.getInitialCardsCount();
        int currentJudge = roundState.getCurrentJudge();

        for (int id = 0; id < allPlayerList.size(); id++) {
            Player player = allPlayerList.get(id);

            if (id != currentJudge) {
                while (player.getHand().size() < fullHand) {
                    String newDrawCard = drawRedCard();
                    player.addCard(newDrawCard);
                }
            }
        }
    }



    private String drawRedCard(){
        return gameState.getDeck().drawRedAppleCard();
    }
}

