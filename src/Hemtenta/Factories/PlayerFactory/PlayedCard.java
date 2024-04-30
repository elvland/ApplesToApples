package Hemtenta.Factories.PlayerFactory;

/**
 * Class representing data for a played card during runtime.
 * A PlayedCard stores both the player's ID and the played green apple card as a string.
 */
public class PlayedCard {


    private final int playerID;
    private final String submitedCard;


    public PlayedCard(int playerID, String greenAppleCard) {
        this.playerID = playerID;
        this.submitedCard = greenAppleCard;
    }
    public int getPlayerID() {
        return playerID;
    }

    public String getAppleCard() {
        return submitedCard;
    }

}