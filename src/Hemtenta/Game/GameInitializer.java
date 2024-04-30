package Hemtenta.Game;


import Hemtenta.Factories.CardFactory.CardDeck;
import Hemtenta.Factories.CardFactory.DeckChosing;
import Hemtenta.IOhandler.ExceptionHandler;
import Hemtenta.IOhandler.UI;
import Hemtenta.Setup.ClientMode;
import Hemtenta.Setup.ServerMode;
import Hemtenta.IOhandler.Input;


/**
 * Initializes the game server or client joining process.
 * It hosts a server or joins a pre-made lobby.
 */
public class GameInitializer {

    private CardDeck deck;
    private boolean allowOnlinePlayers;
    private int numClientPlayers;



    public GameInitializer() {
        // Selects game server option
        boolean gameHost = hostDecider();

        if (gameHost) {
            this.allowOnlinePlayers = onlineDecider();

            if (allowOnlinePlayers) {
                numClientPlayers = choseNumClients();
            }

            deck = DeckChosing.cardDeckChoser();

        }
        // Creating server with bots only
        if (gameHost && !allowOnlinePlayers) {
            try {
                new ServerMode(0, deck);
            } catch (Exception e) {
                ExceptionHandler.handler(e);
            }
        }
        // Creating server with online players (and maybe bots if online players < 3)
        else if (gameHost && allowOnlinePlayers) {
            try {
                UI.lobbyCreatedPrint();
                new ServerMode(numClientPlayers, deck);
            } catch (Exception error) {
                ExceptionHandler.handler(error);

            }
        } else {
            // Player tries to join an existing server
            new ClientMode("127.0.0.1"); //Hosting locally for educational purpose
            // new ClientMode(args); for joining a lobby by ip adress.
        }
    }

    private int choseNumClients() {
        UI.numClientsRequest();
        return Input.getValidInput(1, 10);
    }

    private boolean hostDecider() {
        UI.hostServerRequest();
        return Input.binaryInputValidator();
    }

    private boolean onlineDecider() {
        UI.onlineGameingRequest();
        return Input.binaryInputValidator();
    }
}
