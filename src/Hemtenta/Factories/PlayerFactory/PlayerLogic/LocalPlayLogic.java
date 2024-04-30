package Hemtenta.Factories.PlayerFactory.PlayerLogic;

import Hemtenta.Factories.PlayerFactory.PlayedCard;
import Hemtenta.Factories.PlayerFactory.Player;
import Hemtenta.IOhandler.Input;
import Hemtenta.Setup.GameSettings;

import java.util.ArrayList;

/**
 * LocalPlayLogic class handles the game logic for host/local player
 */
public class LocalPlayLogic implements CardPlayingStrategy {

    private GameSettings.VoteTypeMode voteTypeMode;
    private GameSettings.FirstPhaseMode firstPhaseMode;
    private GameSettings.CardTypeMode cardTypeMode;

    private final GameSettings gameSettings;

    public LocalPlayLogic(GameSettings settings) {
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
                submittedCard = hostPlayCardNormal(player);

                break;
             case WILD_APPLES:
             //   hostPlayWildCard(player);
               break;
        }
    return submittedCard;
    }

    @Override
    public PlayedCard chooseCardJudge(ArrayList<PlayedCard> allPlayedCards) {
        PlayedCard chosenCard = null;
        switch (voteTypeMode) {

            case NORMAL:
                chosenCard = hostVoteNormalLogic(allPlayedCards);

                break;
            case ALL_PLAYER_VOTE:
               //chosenCard = hostVoteallLogic(allPlayedCards)
        }
        return chosenCard;
    }




    /**
    * Logic below for setting judge voting logic for various modes.
    */

    //----------------------------------------------------------------------------------------------------//
    private PlayedCard hostVoteNormalLogic(ArrayList<PlayedCard> allPlayedCards) {
        int cardChoice = Input.getValidInput(0,gameSettings.getTOTAL_NUM_PLAYERS()-2); //vote range = (number of players-2) (indexing)
        return allPlayedCards.get(cardChoice);
    }

    // private PlayedCard hostVoteNormalLogic(ArrayList<PlayedCard> allPlayedCards) {
    // Logic should be implemented her for all player vote for favorite card mode
    // }


    //---------------------------------------------------------------------------------------------------//



    /**
     * Logic below for setting play card logic
     */
    //---------------------------------------------------------------------------------------------------//

    private PlayedCard hostPlayCardNormal(Player player) {
        int chosenCardIndex = Input.getValidInput(0,player.playCardRange());
        return player.SubmitCard(chosenCardIndex);
    }



    //---------------------------------------------------------------------------------------------------//

}
