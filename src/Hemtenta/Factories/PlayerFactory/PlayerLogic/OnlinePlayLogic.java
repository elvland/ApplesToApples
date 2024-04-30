package Hemtenta.Factories.PlayerFactory.PlayerLogic;

import Hemtenta.Factories.PlayerFactory.PlayedCard;
import Hemtenta.Factories.PlayerFactory.Player;
import Hemtenta.IOhandler.ExceptionHandler;
import Hemtenta.Setup.GameSettings;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * OnlinePlayLogic class handles the game logic for online players
 */
public class OnlinePlayLogic implements CardPlayingStrategy {

    private GameSettings.VoteTypeMode voteTypeMode;
    private GameSettings.FirstPhaseMode firstPhaseMode;
    private GameSettings.CardTypeMode cardTypeMode;
    private final BufferedReader inFromClient;

    private final GameSettings gameSettings;

    public OnlinePlayLogic(BufferedReader inFromClient ,GameSettings settings) {
        this.inFromClient = inFromClient;
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
        PlayedCard submitedCard = null;
        switch (cardTypeMode) {

            case NORMAL:
                submitedCard =  onlinePlayCardNormal(player);

                break;
            case WILD_APPLES:
              //  botPlayCardNormal(player);
                break;
        }
        return submitedCard;
    }


    @Override
    public PlayedCard chooseCardJudge(ArrayList<PlayedCard> allPlayedCards) {
        PlayedCard choosenCard = null;
        switch (voteTypeMode) {

            case NORMAL:
                choosenCard = clientVoteCardNormal(allPlayedCards);

                break;
            case ALL_PLAYER_VOTE:
                //choosenCard = allVoteCard(player);
                break;
        }
        return choosenCard;
    }

    private PlayedCard onlinePlayCardNormal(Player player) {
        int playedCardIndex = Integer.parseInt(getInFromClient());  // Read the played card from the client
        return player.SubmitCard(playedCardIndex);
    }


    private PlayedCard clientVoteCardNormal(ArrayList<PlayedCard> allPlayedCards ){
        String chooseWinningCard = getInFromClient();
        int playedWinningIndex = Integer.parseInt(chooseWinningCard);

        return allPlayedCards.get(playedWinningIndex);
    }



    /**
     * Reads a line of text from the input stream connected to the client.
     * @return The line of text read from the client.
     * Returns null if an IO error occurs during reading.
     */
    public String getInFromClient()  {
        try {
            return this.inFromClient.readLine();
        } catch (IOException readError){
            ExceptionHandler.handler(readError);
            return null;
        }

    }

}
