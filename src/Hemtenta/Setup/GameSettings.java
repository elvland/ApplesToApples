package Hemtenta.Setup;



/**
 * Gamesetting sets up the settings for a hosted server.
 */
public class GameSettings {

    //--------------------------------------------------------------------------------------------//
    /**
    * Code below , initialization for enum and gamemode variable (cardTypeMode,voteTypeMode, firstPhaseMode) are currently hardcoded
     * (this is because right now NORMAL gamemode is the only setting).
     * For future modification when different gamemodes are implemented. Assign these variables by e.g.
     * creating a simple gamesettingsmenu class, which host choose gamemodes
     * and sets desired gamemodes to selected gamemode variables
    */

    private final CardTypeMode cardTypeMode = CardTypeMode.NORMAL;
    private final VoteTypeMode voteTypeMode = VoteTypeMode.NORMAL;
    private final FirstPhaseMode firstPhaseMode = FirstPhaseMode.NORMAL;
    //---------------------------------------------------------------------------------------------//

    private final int MINIMUM_PLAYERS = 3;
    private final int WINNING_SCORE;
    private final int CARDS_IN_HAND;
    private final int ONLINE_PLAYER_TOTAL;
    private int TOTAL_NUM_PLAYERS;



    public GameSettings(int onlinePlayerCount) {
        this.ONLINE_PLAYER_TOTAL = onlinePlayerCount;
        this.TOTAL_NUM_PLAYERS = setTotalPlayers(onlinePlayerCount);
        this.WINNING_SCORE = winningScoreSetter(TOTAL_NUM_PLAYERS);
        this.CARDS_IN_HAND = 7;
    }



    /**
     * Sets the total number of players in the game, taking into account
     * the provided online player count and ensuring a minimum of 4 players.
     * @param onlinePlayerCount The number of online players joining the game.
     * @return The total number of players, including online players.
     */
    private int setTotalPlayers(int onlinePlayerCount){

        int totalNumberPlayers = MINIMUM_PLAYERS+1;
        if (onlinePlayerCount > MINIMUM_PLAYERS) {
            totalNumberPlayers = (onlinePlayerCount + 1);
        }
        return totalNumberPlayers;
    }

    /**
     * Sets the number of needed score (cards) for winning a game
     * @param playerCount Number of players in the game
     * @return The total score needed to win
     */
    private int winningScoreSetter(int playerCount) {
        int totWinScore = 8; // Default winning score for 4 players or fewer

        switch (playerCount) {
            case 5 -> totWinScore = 7;
            case 6 -> totWinScore = 6;
            case 7 -> totWinScore = 5;
            default -> {
                if (playerCount >= 8) {
                    totWinScore = 4;
                }
            }
        }
        return totWinScore;
    }


    /**
     * Enums representing various game modes
     */
    public enum PlayCardType{
        NORMAL,
        APPLES_AND_PEARS
    }
    public enum CardTypeMode {
        NORMAL,
        WILD_APPLES

    }

    public enum VoteTypeMode {
        NORMAL,
        ALL_PLAYER_VOTE

    }
    public enum FirstPhaseMode {
        NORMAL,
        JudgereplaceCard
    }
    
    /**
     * Getters of various game setting variables
     */
    public int getTOTAL_NUM_PLAYERS() {return TOTAL_NUM_PLAYERS;}
    public FirstPhaseMode getPhaseAlogic() {
        return firstPhaseMode;
    }

    public VoteTypeMode getVoteType() {
        return voteTypeMode;
    }

    public CardTypeMode getCardType() {
        return cardTypeMode;
    }

    public int getScoreToWin() {return WINNING_SCORE;}
    public int getONLINE_PLAYER_COUNT() {return ONLINE_PLAYER_TOTAL;}
    public int getInitialCardsCount() {return CARDS_IN_HAND;}
    public int getMINIMUM_PLAYERS() {return MINIMUM_PLAYERS;}

}