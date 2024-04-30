package Hemtenta.Factories.PlayerFactory.PlayerTypes;

import Hemtenta.Factories.PlayerFactory.PlayerLogic.LocalPlayLogic;
import Hemtenta.Factories.PlayerFactory.Player;
import Hemtenta.Setup.GameSettings;

/**
 * LocalPlayer represents the host of the server.
 */
public class LocalPlayer extends Player {
    public LocalPlayer(int playerID, GameSettings settings) {
        super(playerID, new LocalPlayLogic(settings));
    }
}