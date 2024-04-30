package Hemtenta.Client;

import Hemtenta.IOhandler.ExceptionHandler;
import Hemtenta.IOhandler.Input;
import Hemtenta.IOhandler.UI;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.Socket;
import java.nio.channels.ClosedChannelException;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * ClientListener manages client-server communication and actions for the client-side game loop.
 */
public class ClientListener {

    private final DataOutputStream outToServer;
    private final BufferedReader inFromServer;


    public ClientListener(DataOutputStream outToServer, BufferedReader inFromServer) {
        this.outToServer = outToServer;
        this.inFromServer = inFromServer;
    }

    /**
     * Initiates the game loop on the client-side by reading from the server and performing actions based on the message.
     * Closes the socket and throws an exception on game over.
     * @param aSocket The client-side socket for the game connection.
     * @throws IOException If an I/O error occurs during the game loop.
     */
    public void gameLoop(Socket aSocket) throws IOException {
        boolean isConnected = aSocket.isConnected();

        while (isConnected) {
            String[] readData = readFromServer();
            String message = getMessage(readData);
            String action = getAction(readData);

            switch (action) {
                case "PRINT" -> printMessage(message);
                case "PLAY" -> clientPlayPhase(message);
                case "JUDGE" -> clientVotePick(message);
                case "GAMEOVER" -> {
                    printMessage(message);
                    aSocket.close();
                    throw new ClosedChannelException();
                }
                default -> printMessage("Action not found");
            }
        }
    }



    /**
     * Handles the vote-picking action for the client side.
     * @param message The message string for vote picking.
     * @throws IOException If an I/O error occurs during the vote picking process.
     */
    private void clientVotePick(String message)  {
        printMessage(message);

        // Retrieve the played cards and form the vote list
        String[] roundPlayedCards = readFromServer();
        ArrayList<String> voteList = getPlayedCards(roundPlayedCards[0]);

        UI.voteCardRequest();

        // Get a valid vote card index from user input
        int cardIndex = Input.getValidInput(0, voteList.size() - 2);

        // Send the selected card index to the server
        sendToServer(String.valueOf(cardIndex));
    }

    /**
     * This method represents the phase where the client plays a red apple card from their hand.
     * It fetches the cards from the server, generates a printable hand, prompts the user for input,
     * and sends the selected card index to the server.
     * @param message The message containing the cards from the server.
     */
    private void clientPlayPhase(String message) {
        // Fetch the cards from the server message
        ArrayList<String> handList = fetchServerCards(message);

        // Generate a printable representation of the hand.
        generateHandPrint(handList);

        // Prompt the user to select a card from their hand.
        int cardIndex = Input.getValidInput(0, handList.size() - 1);

        // Send the selected card index of the hand to the server.
        sendToServer(String.valueOf(cardIndex));
    }


    private void generateHandPrint(ArrayList<String> hand) {
        StringBuilder sb = new StringBuilder();
        sb.append("The following cards in your current hand are: \n \n");
        for (int i = 0; i < hand.size(); i++) {
            sb.append("[").append(i).append("] ").append(hand.get(i)).append("\n");
        }
        printMessage(sb.toString());
    }


    /**
     * Parses the server message to retrieve the playing cards.
     * The message is split based on the "!!" delimiter to extract individual card strings.
     * @param message The server message containing playing card information.
     * @return An ArrayList containing the playing cards as strings.
     */

    private ArrayList<String> fetchServerCards(String message) {
        String[] playingCardString = (message).split("!!");
        return new ArrayList<>(Arrays.asList(playingCardString));
    }

    /**
     * Retrieves and prints the list of played cards from the server message.
     * The message is split based on the "!!" delimiter to extract individual card strings,
     * and each card is printed as a message.
     * @param message The server message containing the played card information.
     * @return An ArrayList containing the played cards as strings.
     */

    private ArrayList<String> getPlayedCards(String message) {
        String[] playingCardList = (message).split("!!");
        for (String card : playingCardList){
            printMessage(card);
        }
        return new ArrayList<>(Arrays.asList(playingCardList));
    }

    /**
     * Sends a message to the server thriught the dataoutputstream.
     * @param message The message to be sent to the server.
     */
    private void sendToServer(String message) {
        try {
            outToServer.writeBytes(message + "\n");
        } catch (IOException writeError) {
            ExceptionHandler.handler(writeError);
        }
    }
    /**
     * Reads a message from the server and splits it message and action by the "#" delimiter e.g "Hello world#PRINT".
     * @return An array of strings obtained by splitting the message read from the server using the "#" delimiter.
     */
    private  String[] readFromServer()  {
        try {
            String readMessage =  inFromServer.readLine().trim();  // Read a message from the server
            return readMessage.split("#");
        } catch (IOException readError){
            ExceptionHandler.handler(readError);
        }
        return null;
    }
    /**
     * Retrieves the message content from the given array of read data.
     * @param readData The array of strings representing the read data from the server
     * @return The message content extracted from the read data
     */
    private String getMessage(String[] readData){
        return readData[0];
    }
    private String getAction(String[] readData){
        return readData[1];
    }
    private void printMessage(String message) {
        System.out.println(message.replaceAll("!!", "\n") + "\n" );
    }

}