package Hemtenta.GamePlay.PhaseManager;

import Hemtenta.Factories.PlayerFactory.PlayedCard;
import Hemtenta.Factories.PlayerFactory.Player;

import java.util.ArrayList;

/**
 * Roundstate class keeps track of current state variables of the round
 */
public class RoundState {
    private String roundGreenApple;
    private Integer roundJudge;
    private final ArrayList<PlayedCard> roundPlayedCards;
    private Player roundWinner;
    private boolean roundWinnerSet;
    private PlayedCard roundWinningCard;
    private final GameState gameState;
    private Player gameWinner;
    private boolean gameWinnerSet;

    public RoundState(GameState gameState,int judge)  {
        this.roundWinnerSet = false;
        this.gameState = gameState;
        this.roundPlayedCards = new ArrayList<>();
        setGreenAppleCard();
        setJudge(judge);
    }


    public int getCurrentJudge() {
        return this.roundJudge;
    }
    protected Player getJudgePlayer(){
        return gameState.getAllPlayers().get(getCurrentJudge());
    }
    protected void setJudge(int playerID) {
         this.roundJudge = playerID;
    }
    public boolean isJudge(Player player){
        return player.getPlayerId() == getCurrentJudge();
    }

    public String getRoundGreenApple(){
        return this.roundGreenApple;
    }
    protected void setGreenAppleCard( ) {
        this.roundGreenApple =  gameState.getDeck().drawTopQuestionCard();
    }
    public ArrayList<PlayedCard> getRoundPlayedCards(){
        return this.roundPlayedCards;
    }
    protected void addRedRoundCard(PlayedCard redApple){
        roundPlayedCards.add(redApple);
    }

    /**
     * Sets the winner of the current round if the winner hasn't been already set.
     * @param wonCard The card that won the round.
     */
    protected void setRoundWinner(PlayedCard wonCard) {
        if (!roundWinnerSet) {
            roundWinner = gameState.getAllPlayers().get(wonCard.getPlayerID());
            setRoundWinningCard(wonCard);

            roundWinnerSet = true;
        }
    }
    private void setRoundWinningCard(PlayedCard wonCard) {
        this.roundWinningCard = wonCard;
    }

    public Player getRoundWinner() {
        return roundWinner;
    }

    public PlayedCard getWinningCard() {
        return roundWinningCard;
    }

    protected void setGameWinner(Player playerWinner) {
        if (!gameWinnerSet) {
            gameWinner = playerWinner;

            gameWinnerSet = true;
        }
    }

    public Player getGameWinner(){
        if (gameWinnerSet) {
            return gameWinner;
        } else {
           return null;
        }
    }
    protected void resetRound() {
        roundWinner = null;
        roundWinnerSet = false;
        roundWinningCard = null;
        roundGreenApple = null;
    }

}
