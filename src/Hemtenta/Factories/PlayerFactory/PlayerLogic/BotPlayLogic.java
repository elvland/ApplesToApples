package Hemtenta.Factories.PlayerFactory.PlayerLogic;

import Hemtenta.Factories.PlayerFactory.PlayedCard;
import Hemtenta.Factories.PlayerFactory.Player;
import Hemtenta.IOhandler.ExceptionHandler;

import Hemtenta.Setup.GameSettings;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * BotPlayLogic class handles the game logic for bot players
 */
public class BotPlayLogic implements CardPlayingStrategy {
    private GameSettings.VoteTypeMode voteTypeMode;
    private GameSettings.FirstPhaseMode firstPhaseMode;
    private GameSettings.CardTypeMode cardTypeMode;

    private final GameSettings gameSettings;


    public BotPlayLogic(GameSettings settings) {
        this.gameSettings = settings;
        // Retrieve the enum values from GameSettings
        gameModeSettings();
    }

    private void gameModeSettings() {
        this.voteTypeMode = gameSettings.getVoteType();
        this.firstPhaseMode = gameSettings.getPhaseAlogic();
        this.cardTypeMode = gameSettings.getCardType();

    }

    @Override
    public PlayedCard playCard(Player player) {
        PlayedCard submittedCard = null;
        switch (cardTypeMode) {

            case NORMAL:
                submittedCard = botPlayCardNormal(player);

                break;
            case WILD_APPLES:
                // botPlayCardWILD(player);
                break;
        }
        return submittedCard;
    }

    @Override
    public PlayedCard chooseCardJudge(ArrayList<PlayedCard> allPlayedCards) {
        PlayedCard chosenCard = null;
        switch (voteTypeMode) {

            case NORMAL:
                chosenCard = botVoteCardNormal(allPlayedCards);

                break;
            case ALL_PLAYER_VOTE:
                //chosenCard = botAllCardVote(allPlayedCards);
                break;
        }
        return chosenCard;
    }


    /**
     * Logic below for setting judge voting logic for various game modes.
     */

    //----------------------------------------------------------------------------------------------------//
    /**
     * Simulates a bot's choice for voting a card when the voteTypeMode is NORMAL.
     * @param allPlayedCards The list of cards played by all players.
     * @return The card chosen by the bot to vote for.
     */
    private PlayedCard botVoteCardNormal(ArrayList<PlayedCard> allPlayedCards) {

        Random random = ThreadLocalRandom.current();
        try {
            Thread.sleep(random.nextInt(500));
        } catch (InterruptedException threadError) {
            ExceptionHandler.handler(threadError);
        }
        Random randomCard = new Random();
        // The vote range is from 0 to the number of players - 2 (list indexing)
        int cardChoice = randomCard.nextInt(0,gameSettings.getTOTAL_NUM_PLAYERS()-2);

        return allPlayedCards.get(cardChoice);

    }


    // private PlayedCard botAllCardVote(player){
    //future implementation
    //}
    //---------------------------------------------------------------------------------------------------//






    /**
     * Logic below for setting play card logic
     */
    //---------------------------------------------------------------------------------------------------//

    /**
     * Simulates a bot's choice for choosing a card when the cardTypeMode is NORMAL.
     * @param player The bot player choosing a card.
     * @return The card chosen by the bot to play.
     */
    private PlayedCard botPlayCardNormal(Player player){
        Random rnd = ThreadLocalRandom.current();
        try{
            Thread.sleep(rnd.nextInt(500));
        }
        catch(InterruptedException threadError){
            ExceptionHandler.handler(threadError);
        }
        // The vote range is from 0 to the number of players - 2 (list indexing)
        int rndChoice = rnd.nextInt(player.playCardRange()); //Random integer between (0- number of players-1)
        return player.SubmitCard(rndChoice);

    }


    //private void botAllCardVote(Player player){
    //  logic for bot to playcard if all vote bot logic was implemented.
    // }
}
//---------------------------------------------------------------------------------------------------//