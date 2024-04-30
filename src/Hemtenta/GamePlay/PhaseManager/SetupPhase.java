package Hemtenta.GamePlay.PhaseManager;

import Hemtenta.Factories.CardFactory.CardDeck;
import Hemtenta.Factories.PlayerFactory.Player;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The SetupPhase class handles the initial setup for a new game round, such as providing starting hand cards
 * to players and setting the initial judge for the round.
 */
public class SetupPhase {
    private final GameState gamestate;

    private final RoundState roundState;
    public SetupPhase(GameState gameState, int startHandSize,RoundState roundState) {
        this.roundState = roundState;
        this.gamestate = gameState;
        addStartingHandCards(startHandSize);
        setStartjudge();
    }

    /**
     * Sets the initial judge for the round randomly among the players.
     */
    private void setStartjudge() {
        int allPlayersSize = gamestate.getAllPlayers().size();
        Random rand = ThreadLocalRandom.current();
        roundState.setJudge(rand.nextInt(allPlayersSize)); //sets judge randomly with range [0 - (total players -1) ]
    }


    /**
     * Provides starting hand cards to all players in the game.
     * @param startHandSize The initial number of cards to be distributed to each player's starting hand.
     */
    private void addStartingHandCards(int startHandSize) {

        ArrayList<Player> allPlayereList = gamestate.getAllPlayers();
        for (Player currentPlayer : allPlayereList) {
            ArrayList<String> hand = currentPlayer.getHand();

            for (int i = 0; i < startHandSize; i++) {
                String drawnCard = gamestate.getDeck().drawRedAppleCard();
                hand.add(drawnCard);
            }
        }

    }
}