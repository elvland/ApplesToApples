package Hemtenta.Setup;

import Hemtenta.Client.ClientListener;
import Hemtenta.Factories.PlayerFactory.PlayerManager;
import Hemtenta.IOhandler.ExceptionHandler;
import Hemtenta.IOhandler.UI;


import java.io.*;

import java.net.Socket;


/**
 * ClientMode manages the client-side connection and game loop for the player.
 */
public class ClientMode {


    private DataOutputStream outToServer;
    private BufferedReader inFromServer;
    private  Socket aSocket;
    public ClientMode(String IPaddress) {
        try {
            setUpConnection(IPaddress);
            UI.okConnectionPrint();
            startGameLoop();
        } catch (IOException | ClassNotFoundException error) {
            ExceptionHandler.handler(error);
        }
    }

    /**
     * Establishes a connection with the server using the provided IP address.
     * @param IPaddress    The IP address to connect to.
     * @throws IOException If an I/O error occurs while setting up the connection.
     */
    private void setUpConnection(String IPaddress) throws IOException {
        aSocket = new Socket(IPaddress, PlayerManager.socket);
        outToServer = new DataOutputStream(aSocket.getOutputStream());
        inFromServer = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
    }

    /**
     * Initiates the game loop, handled by the ClientListener, after a successful connection.
     * @throws IOException            If an I/O error occurs during the game loop.
     * @throws ClassNotFoundException If a class cannot be found during the game loop.
     */
    private void startGameLoop() throws IOException, ClassNotFoundException {
        if (aSocket.isConnected()) {
            new ClientListener(outToServer, inFromServer).gameLoop(aSocket);
        }
    }
}
