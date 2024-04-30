package Hemtenta.Factories.PlayerFactory.PlayerTypes;

import Hemtenta.Factories.PlayerFactory.PlayerLogic.BotPlayLogic;
import Hemtenta.Factories.PlayerFactory.Player;
import Hemtenta.Setup.GameSettings;

/**
 * BotPlayer represents a computer-controlled bot player in the card game.
 * A bot player is choosing the winning card during the game rounds, emulating random actions.
 */
public class BotPlayer extends Player {
    public BotPlayer(int playerID, GameSettings settings) {
        super(playerID, new BotPlayLogic(settings));
    }
}
