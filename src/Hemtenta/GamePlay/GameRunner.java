package Hemtenta.GamePlay;

import Hemtenta.Communication.PlayerCommunicationManager;
import Hemtenta.Factories.CardFactory.CardDeck;


import Hemtenta.Factories.PlayerFactory.PlayerManager;
import Hemtenta.GamePlay.PhaseManager.*;

import Hemtenta.IOhandler.UI;
import Hemtenta.Setup.GameSettings;

import java.io.IOException;

/**
 * The GameRunner class manages the gameplay flow for a card game, handling different phases of the game and player interactions.
 */
public class GameRunner {
    private final GameState gameState;
    private final GameSettings gameSettings;
    private final ScoreHandler scoreboardHandler;
    private RoundState roundState;
    public GameRunner( GameSettings settings, CardDeck deck)  {
        this.gameSettings = settings;
        PlayerManager playerManager = new PlayerManager(gameSettings);
        this.gameState = new GameState(playerManager.getAllPlayers(), deck);
        this.scoreboardHandler = new ScoreHandler(gameState.getAllPlayers());

    }

    /**
     * Initializes different game phases and orchestrates the game flow.
     * @throws IOException        Signals that an I/O exception has occurred.
     * @throws InterruptedException Signals that an application has been interrupted.
     */
    public void initGamePhases() throws IOException, InterruptedException {
        this.roundState = new RoundState(gameState,0); //Phase A. sets up states for current round
        new SetupPhase(gameState, gameSettings.getInitialCardsCount(),roundState);

        PlayerCommunicationManager communication = new PlayerCommunicationManager(gameState.getAllPlayers(), new UI(roundState,gameState.getAllPlayers()));
        communication.sendToAllPlayers("startGameOutput", "PRINT");

        playGamePhases(gameState, communication);
    }

    /**
     * Plays through the different phases of the game until it is completed.
     * @param gameState      The current state of the game.
     * @param communication  Manages communication between players.
     * @throws InterruptedException Signals that an application has been interrupted.

     */
    private void playGamePhases(GameState gameState, PlayerCommunicationManager communication) throws InterruptedException{
        InfoPhase infoPhase = new InfoPhase(communication);
        PlayCardPhase playCardPhase = new PlayCardPhase(gameState, communication);
        VotePhase votePhase = new VotePhase(communication, scoreboardHandler);
        NextRoundPhase nextRoundPhase = new NextRoundPhase(gameSettings, communication, gameState, scoreboardHandler);


        while (!gameState.isGameOverFlag()) {

            //Phase A) Draws a green apple card and Informs all players with information regarding current round states.
            infoPhase.run(roundState);

            //Phase B) Playcard phase for players to submit red apples.
            playCardPhase.run(roundState);

            //Phase C) Vote phase for judge to pick round winning red apple.
            votePhase.showCards(roundState);

            votePhase.voteRun(roundState);
            //Phase D) Updates Game state by updating score and sets up for new round
            nextRoundPhase.run(roundState);

        }
    }
}

