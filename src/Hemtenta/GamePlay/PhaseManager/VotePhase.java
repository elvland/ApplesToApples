package Hemtenta.GamePlay.PhaseManager;

import Hemtenta.Communication.PlayerCommunicationManager;
import Hemtenta.Factories.PlayerFactory.PlayedCard;
import Hemtenta.Factories.PlayerFactory.Player;
import Hemtenta.IOhandler.UI;


import java.util.ArrayList;
import java.util.Collections;



/**
 * The VotePhase class manages the phase where players vote on the winning card chosen by the judge.
 * It handles the card selection process, updating scores, and displaying information about the winning card.
 */
public class VotePhase {

    private final ScoreHandler scoreHandler;
    private final PlayerCommunicationManager communication;

    public VotePhase(PlayerCommunicationManager communication
                    , ScoreHandler scoreHandler) {

        this.communication = communication;
        this.scoreHandler = scoreHandler;

    }


    public void showCards(RoundState roundState)  {
        shufflePlayedCards(roundState.getRoundPlayedCards());
        showPlayedCards();

    }

    public void voteRun(RoundState roundState) {

        PlayedCard winningCard = pickWinningCardPhase(roundState);

        updateScore(winningCard,roundState.getRoundGreenApple());
        sendWinnerInfo();
    }

    /**
     * Shuffles the played cards to randomize the order.
     * @param playedCardList list of all played cards needed to shuffle
     */
    private void shufflePlayedCards(ArrayList<PlayedCard> playedCardList) {
        Collections.shuffle(playedCardList);
    }
    /**
     * Prompts all the played cards for current round to every player's terminal
     */
    private void showPlayedCards()  {
        communication.sendToAllPlayers("printPlayedCards","PRINT");
    }

    /**
     * Allows the judge to select the winning card.
     * The chooseCardtoJudge function is different depending on each player type
     * @return The winning card chosen by the judge.
     */
    private PlayedCard pickWinningCardPhase(RoundState roundState) {
        Player judge =  roundState.getJudgePlayer();
        UI.voteCardRequest();
        PlayedCard winningCard = judge.chooseCardToJudge(roundState.getRoundPlayedCards());
        roundState.setRoundWinner(winningCard);
        return winningCard;
    }
    /**
     * Updates the score based on the winning card and the question card for the round.
     * @param redWinningCard       The card that won the round (red apple).
     * @param greenAppleCard The question card for the current round (green apple).
     */
    private void updateScore(PlayedCard redWinningCard, String greenAppleCard) {
        scoreHandler.updateScores(redWinningCard,greenAppleCard);
    }

    /**
     * Sends information about the winner to all players.
     */
    private void sendWinnerInfo()  {
        communication.sendToAllPlayers("winnerOutPrint","PRINT");
    }

}

