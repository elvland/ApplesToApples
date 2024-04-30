package Hemtenta.Factories.PlayerFactory.PlayerLogic;

import Hemtenta.Factories.PlayerFactory.PlayedCard;
import Hemtenta.Factories.PlayerFactory.Player;

import java.util.ArrayList;

// An interface defining the strategy for playing and judging cards
public interface CardPlayingStrategy {
    /**
     * Plays a card according to the specified card type mode.
     * @param player The player who is supposed to submits the card.
     */
    PlayedCard playCard(Player player);

    /**
     * Chooses a card for judging based on the specified vote type mode.
     * @param allPlayedCards List of all cards played.
     */
    PlayedCard chooseCardJudge(ArrayList<PlayedCard> allPlayedCards);
}
