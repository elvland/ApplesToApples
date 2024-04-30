package Hemtenta.Setup;

import Hemtenta.Factories.CardFactory.CardDeck;

import Hemtenta.GamePlay.GameRunner;

import Hemtenta.IOhandler.ExceptionHandler;

import java.io.IOException;

/**
 * The ServerMode class is responsible for setting up and initializing the game server inorder to play.
 */
public class ServerMode {
    private final GameRunner gameRunner;


    public ServerMode(int onlinePlayerCount, CardDeck deckChosen) {
        GameSettings gameSettings = new GameSettings(onlinePlayerCount);
        gameRunner = new GameRunner(gameSettings,deckChosen);
        startGame();
    }


    /**
     * Starts the game by initializing game phases.
     * Handles potential exceptions and passes them to the ExceptionHandler
     */
    private void startGame() {
        try {
            gameRunner.initGamePhases();
        } catch (InterruptedException | IOException error) {
            ExceptionHandler.handler(error);
        }
    }

}