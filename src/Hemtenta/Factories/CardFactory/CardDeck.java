package Hemtenta.Factories.CardFactory;

import java.util.ArrayList;
/**
 * Represents a Card Deck in the context of a card game. This class provides functionality
 * for managing both red and green apple decks, along with drawing cards from the decks.
 * Subclasses will implement specific deck types.
 */
public abstract class CardDeck {

  private final ArrayList<String> redAppleDeck;
  private final ArrayList<String> greenAppleDeck;
  private final String deckName;

  public CardDeck(String deckName, ArrayList<String> redAppleDeck, ArrayList<String> greenAppleDeck) {
   this.deckName = deckName;
   this.redAppleDeck = redAppleDeck;
   this.greenAppleDeck = greenAppleDeck;
 }

 public String drawRedAppleCard() {
  return redAppleDeck.remove(0);
 }

 public ArrayList<String> getRedAppleDeck(){
  return this.redAppleDeck;
 }
 public ArrayList<String> getGreenAppleDeck(){
  return this.greenAppleDeck;
 }

 public String drawTopQuestionCard() {
  return greenAppleDeck.remove(0);
 }

}
