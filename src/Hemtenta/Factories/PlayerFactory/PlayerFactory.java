package Hemtenta.Factories.PlayerFactory;

import Hemtenta.Factories.PlayerFactory.PlayerTypes.LocalPlayer;
import Hemtenta.Factories.PlayerFactory.PlayerTypes.OnlinePlayer;
import Hemtenta.Factories.PlayerFactory.PlayerTypes.BotPlayer;
import Hemtenta.Setup.GameSettings;

import java.io.BufferedReader;
import java.io.DataOutputStream;


/**
 * The PlayerFactory class is responsible for creating different types of players
 * (BotPlayer, OnlinePlayer, LocalPlayer) with the specified parameters.
 */
public class PlayerFactory {
    public static Player createBotPlayer(int playerID, GameSettings settings) {
        return new BotPlayer(playerID,settings);
    }
    public Player createOnlinePlayer(int playerID, BufferedReader inFromClient, DataOutputStream outToClient, GameSettings settings) {
        return new OnlinePlayer(playerID, inFromClient, outToClient,settings);
    }
    public Player createLocalPlayer(int playerID, GameSettings settings) {
        return new LocalPlayer(playerID,settings);
    }
}