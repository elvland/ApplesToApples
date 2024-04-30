package Hemtenta.Factories.PlayerFactory;


import Hemtenta.IOhandler.ExceptionHandler;
import Hemtenta.IOhandler.UI;
import Hemtenta.Setup.GameSettings;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;



/**
 * PlayerManager class manages the creation and management of players in the card game.
 * This class also provides methods for sending data to clients and managing client connections.
 */
public class PlayerManager {

    private final ArrayList<Player> players = new ArrayList<>();
    private final PlayerFactory playerType;
    private int playerID = 0;
    public static int socket = 2048;
    private final GameSettings settings;



    public PlayerManager(GameSettings settings)  {
        this.settings = settings;
        this.playerType = new PlayerFactory();

        addPlayers(this.settings);

    }

    /**
     * Adds players to the game lobby based on the provided settings.
     * @param settings The game settings specifying the number of online players.
     */
    private void addPlayers(GameSettings settings) {
        int numberOfClients = settings.getONLINE_PLAYER_COUNT();
        int minAllowedPlayers = settings.getMINIMUM_PLAYERS();

        if (numberOfClients != 0 ) {
            addOnlinePlayers(numberOfClients);
        }
        if (numberOfClients < minAllowedPlayers) {

            addBotPlayers(numberOfClients);
        }
        addServerPlayer();
    }


    /**
     * Adds bot players to the game if they number of onlineplayers + host is less than four
     * @param totalOnlinePlayers Total number of online players/clients.
     */
    private void addBotPlayers(int totalOnlinePlayers ) {
        int minimalplayers = settings.getMINIMUM_PLAYERS();
        for (int i = totalOnlinePlayers; i < minimalplayers ; i++) { //IMPORTANT!! Adding botplayers after onlineclients
            {
                Player botPlayer = PlayerFactory.createBotPlayer(playerID++,settings);
                players.add(botPlayer);
            }

        }
    }

    /**
     * Adds Host/local player to the game by creating a LocalPlayer object from the PlayerFactory.
     */
    private void addServerPlayer() {
        Player localPlayer = playerType.createLocalPlayer(playerID++,settings);
        players.add(localPlayer);
    }


    /**
     * Accepts connections from online clients and sets up communication.
     * @param totalOnlinePlayers The total number of online players to connect.
     */
    private void addOnlinePlayers(int totalOnlinePlayers) {
        // Handle socket initialization
        // Accept incoming connections, etc.
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(socket++);

            for (int onlineClient = 0; onlineClient < totalOnlinePlayers; onlineClient++) {
                // Accept client connection
                Socket clientSocket = serverSocket.accept();

                UI.okConnectionPrint();

                handleClientConnection(clientSocket);
            }
        } catch (IOException error) {
            ExceptionHandler.handler(error);
        } finally {
            try {
                if (serverSocket != null && !serverSocket.isClosed()) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                ExceptionHandler.handler(e);
            }
        }
    }
    /**
     * Handles the connection with an individual online client.
     * @param clientSocket The socket for the connected client.
     * @throws IOException if an error occurs during communication.
     */
    private void handleClientConnection(Socket clientSocket) throws IOException {
        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        DataOutputStream outToClient = new DataOutputStream(clientSocket.getOutputStream());

        Player onlinePlayer = playerType.createOnlinePlayer(playerID++, inFromClient, outToClient,settings);

        players.add(onlinePlayer);

    }






    public  ArrayList<Player> getAllPlayers() {
        return players;
    }



}