
package Hemtenta.GamePlay.PhaseManager;

import Hemtenta.Communication.PlayerCommunicationManager;
import Hemtenta.Factories.PlayerFactory.PlayedCard;
import Hemtenta.Factories.PlayerFactory.Player;
import Hemtenta.IOhandler.ExceptionHandler;


import java.util.ArrayList;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The PlayCardPhase class manages the phase where players, except the judge, play their cards.
 */
public class PlayCardPhase {
    private final GameState gameState;
    private final PlayerCommunicationManager communication;

    public PlayCardPhase(GameState gameState, PlayerCommunicationManager communication) {
        this.communication = communication;
        this.gameState = gameState;
    }

    public void run(RoundState roundState) throws InterruptedException {
        int judge  = roundState.getCurrentJudge();

        allPlayCard(roundState,judge);

    }


    /**
     * Asynchronously allows each player (except the judge) to play a card simultaneously.
     * @throws InterruptedException if there are interruptions during execution.
    */
    private void allPlayCard(RoundState roundState,int judge) throws InterruptedException {
        ArrayList<Player> playerList = gameState.getAllPlayers();

        ExecutorService threadpool = Executors.newFixedThreadPool(playerList.size() - 1);


        for (Player currentPlayer : playerList){
            if (currentPlayer.getPlayerId() != judge){
                //Make sure every player can answer at the same time


                Runnable task = () -> {
                    try {
                        PlayedCard card = currentPlayer.playCard();  //Each player type has unique playcard() logic
                        roundState.addRedRoundCard(card);
                        communication.sendToSinglePlayer("printPlayedCard","PRINT",currentPlayer,card.getAppleCard());
                    } catch (NumberFormatException error) {
                        ExceptionHandler.handler(error);
                    }

                };
                threadpool.execute(task);
            }

        }

        threadpool.shutdown();
        //wait for all the answers to come in
        while (roundState.getRoundPlayedCards().size() < playerList.size() - 1) {

                Thread.sleep(1000);
            }
            while (!threadpool.isTerminated()) {
                Thread.sleep(100);
            }
    }


}

