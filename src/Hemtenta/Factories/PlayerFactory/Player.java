package Hemtenta.Factories.PlayerFactory;

import Hemtenta.Factories.PlayerFactory.PlayerLogic.CardPlayingStrategy;
import Hemtenta.IOhandler.ExceptionHandler;

import java.util.ArrayList;


/**
 * Abstract Player class which sets up a template for a player object (local,bot or online player)
 */
public abstract class Player {
    private final int playerID;
    private ArrayList<String> hand;
    private ArrayList<String> winningCards;
    private final CardPlayingStrategy playLogic;


    public Player(int playerID, CardPlayingStrategy playLogic) {
        this.playerID = playerID;
        this.hand = new ArrayList<>();
        this.winningCards = new ArrayList<>();
        this.playLogic = playLogic;
    }

    /**
     * Methods to be implemented and used for strategy design pattern.
     */
    public PlayedCard playCard(){
        return playLogic.playCard(this);
    }
    public PlayedCard chooseCardToJudge(ArrayList<PlayedCard> playedCards){
        return playLogic.chooseCardJudge(playedCards);
    }


    /**
     * Submits a player's chosen card to the game.
     * Removes the submited card from players hand and deals with various exceptions.
     * @param chosenCardIndex The index of the chosen card in the player's hand.
     */
    public PlayedCard SubmitCard(int chosenCardIndex){
        try{
            String chosenCard = hand.get(chosenCardIndex);
            PlayedCard playedCard = new PlayedCard(playerID, chosenCard);
            hand.remove(chosenCardIndex);
            return playedCard;
        } catch (Exception otherError) {
            ExceptionHandler.handler(otherError);
        }
        return null;
    }

    /**
     * Adds the winning card to players collection .
     * Total number of wonQuestionCards represent the total score
     * @param wonQuestionCard round winning card
     */
    public void addScore(String wonQuestionCard) {
        winningCards.add(wonQuestionCard);
    }

    /**
     * Adds a player card to players hand collection .
     * @param drawnCard new drawn player card added to players hand
     */
    public void addCard(String drawnCard) {
        getHand().add(drawnCard);
    }
    /**
     * Determines the max range for a player to play a card from its hand.
     */
    public int playCardRange(){
        return getHand().size()-1; // Hand list indexing ends at hand length-1
    }


    public int getPlayerId() {
        return this.playerID;
    }
    public ArrayList<String> getHand(){
        return this.hand;
    }
    public int getScore(){
        return getWinningCards().size();
    }
    public ArrayList<String> getWinningCards() {
        return new ArrayList<>(this.winningCards);
    }

}
