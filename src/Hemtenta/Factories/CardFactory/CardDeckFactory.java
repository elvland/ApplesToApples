package Hemtenta.Factories.CardFactory;

import java.io.IOException;
import java.util.Collections;
/**
 * Factory class responsible for creating various types of CardDeck instances based on the provided deck type.
 * This factory also shuffles the created deck before returning it.
 */
public class CardDeckFactory{

    CardDeck deck = null;
    public CardDeck createCardDeck(String chosenDeck) throws IOException {
        if (chosenDeck.equals("apples2apples")){
            deck = new A2ADecks();
        }
        if(chosenDeck.equals("Wildapples2apples")){
             deck = new A2AWildDecks();
        }
        /*if(chosenDeck.equals("applesAndPears")){
            deck = new A2PDecks();
        }*/

        return deckShuffler(deck);
    }
        private CardDeck deckShuffler(CardDeck deck) {
        Collections.shuffle(deck.getRedAppleDeck());
        Collections.shuffle(deck.getGreenAppleDeck());
        return deck;
    }

}
