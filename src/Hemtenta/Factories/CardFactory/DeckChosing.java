package Hemtenta.Factories.CardFactory;

import Hemtenta.IOhandler.ExceptionHandler;
import Hemtenta.IOhandler.Input;
import Hemtenta.IOhandler.UI;

import java.io.IOException;
import java.util.Scanner;

public class DeckChosing {
    /**
     *  Output/menu for card deck choosing
     *  Returning given card deck
     *  input = 1 => returns Appels2Apples card deck
     *  input = 2 => returns Cards against humanity card deck
     */
    public static CardDeck cardDeckChoser() {
        int input= 1;

        CardDeckFactory gameDeck = new CardDeckFactory();






        while (true) {
            UI.chooseDeckQuery(); //Asks for input
            input =  Input.getValidInput(0,1);
            try {

                if (input == 0) {
                    return gameDeck.createCardDeck("apples2apples");
                } else if (input == 1) {
                    System.out.println("NOT IMPLEMENTED YET");
                 //   return gameDeck.createCardDeck("Wildapples2apples");
                }
            } catch (IllegalArgumentException | IOException error) {
                ExceptionHandler.handler(error);
            }
        }
    }

}
