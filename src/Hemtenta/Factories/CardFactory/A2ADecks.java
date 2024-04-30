package Hemtenta.Factories.CardFactory;

import Hemtenta.IOhandler.ExceptionHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Apples 2 apples card deck generator (red / green cards).
 * Sets and returns the deck object to be cards applicable for the apples 2 apples game-mode.
 */
public class A2ADecks extends CardDeck{


    public A2ADecks() throws IOException {
        super("Appels2Apples",loadRedApplesDeck(),loadGreenApplesDeck());
    }
    /**
     * Loads the Red Apples deck from a text file.
     * @return An ArrayList of strings containing the Red Apples deck content.
     * @throws IOException if an error occurs while reading the file.
     */
    private static ArrayList<String> loadRedApplesDeck() throws IOException {
        try {
            return new ArrayList<>(Files.readAllLines(Paths.get("komp\\Decks\\Apples2Apples\\redApples.txt"), StandardCharsets.ISO_8859_1));
        } catch (IOException readError){
            ExceptionHandler.handler(readError);
            throw readError;
        }
    }
    /**
     * Loads the Green Apples deck from a text file.
     * @return An ArrayList of strings containing the Red Apples deck content.
     * @throws IOException if an error occurs while reading the file.
     */
    private static ArrayList<String> loadGreenApplesDeck() throws IOException {
        try {
            return new ArrayList<>(Files.readAllLines(Paths.get("komp\\Decks\\Apples2Apples\\greenApples.txt"), StandardCharsets.ISO_8859_1));
        } catch (IOException readError){
            ExceptionHandler.handler(readError);
            throw readError;
        }
    }
}
