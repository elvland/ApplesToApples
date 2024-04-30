package Hemtenta.Communication;

import Hemtenta.Factories.PlayerFactory.Player;
import Hemtenta.Factories.PlayerFactory.PlayerTypes.LocalPlayer;
import Hemtenta.Factories.PlayerFactory.PlayerTypes.OnlinePlayer;

import Hemtenta.IOhandler.ExceptionHandler;
import Hemtenta.IOhandler.UI;

import java.io.IOException;
import java.util.ArrayList;

/**
 * PlayerCommunicationManager handles communication between the server
 * and online players in the game.
 */
public class PlayerCommunicationManager {


    private final ArrayList<Player> playerList;
    private UI outPrints;


    public PlayerCommunicationManager(ArrayList<Player> playerList, UI outPrints){
        this.playerList = playerList;
        this.outPrints = outPrints;
    }



    /**
     * Sends a message to all players based on the message type.
     * @param messageKey   The message key used to retrieve the message from hashmap.
     * @param messageType  The type of action sent to online players.
     */
        public void sendToAllPlayers(String messageKey, String messageType) {
            for (Player player : playerList) {
                String message = outPrints.getMessage(messageKey,player);
                if (player instanceof OnlinePlayer) {

                   sendToOnlinePlayer((OnlinePlayer) player, message,messageType);
                } else if (player instanceof LocalPlayer) {
                    outPrints.printMsg(message);
                }
            }
        }



    /**
     * Sends a message to a single player based on the message type.
     */
        public void sendToSinglePlayer(String message,String messageType, Player player,String data)  {
            String cardText = outPrints.getMessage(message, data);
            if (player instanceof OnlinePlayer) {
                sendToOnlinePlayer((OnlinePlayer) player, cardText,messageType);
            } else if (player instanceof LocalPlayer) {
                outPrints.printMsg(cardText);
            }
        }



    public void sendToOnlinePlayer(OnlinePlayer player, String message,String messageType) {
        String formatString = formatMessage(message,messageType);

        try {
            sendInfoToClient(player,formatString);
        } catch (IOException error) {
            ExceptionHandler.handler(error);
        }
    }

    /**
     * Formats the message based on its action type.
     * Modifies the formation of the messagetype if player is the judge.
     */
   private String formatMessage(String message, String messageType) {
       if(message.startsWith("Judge")){
        return message + "#" + "JUDGE";
       } else {
           return message + "#" + messageType; // Add a space between message content and message type
       }
    }



    /**
     * Sends inforrmation out to ever onlineplayer client.
     * @param player The online player which gets sent data to.
     * @throws IOException If an error occurs during communication.
     */
    private void sendInfoToClient(OnlinePlayer player , String message ) throws IOException {
        player.getOutToClient().writeBytes(message+ "\n");
        player.getOutToClient().flush();

    }

}
