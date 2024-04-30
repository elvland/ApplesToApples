package Hemtenta.Factories.PlayerFactory.PlayerTypes;

import Hemtenta.Factories.PlayerFactory.PlayerLogic.OnlinePlayLogic;
import Hemtenta.Factories.PlayerFactory.Player;
import Hemtenta.Setup.GameSettings;

import java.io.*;


/**
 * OnlinePlayer template for representing  a player, connected via a socket in the online card game. mode
 */
public class OnlinePlayer extends Player   {

    private DataOutputStream outToClient;


    public OnlinePlayer(int playerID, BufferedReader inFromClient, DataOutputStream outToClient, GameSettings settings) {
        super(playerID ,new OnlinePlayLogic(inFromClient,settings));
        this.outToClient = outToClient;
    }
    public DataOutputStream getOutToClient() {
        return outToClient;
    }



}
