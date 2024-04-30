package Hemtenta.IOhandler;

import Hemtenta.Factories.PlayerFactory.PlayedCard;
import Hemtenta.Factories.PlayerFactory.Player;
import Hemtenta.Factories.PlayerFactory.PlayerTypes.BotPlayer;
import Hemtenta.Factories.PlayerFactory.PlayerTypes.OnlinePlayer;
import Hemtenta.GamePlay.PhaseManager.RoundState;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class UI {
    private final Map<String, String> messages = new HashMap<>();
    private final RoundState roundState;
    private final ArrayList<Player> playerList;
    public UI(RoundState roundState, ArrayList<Player> playerList) {
        this.playerList = playerList;
        this.roundState = roundState;
        initializeMessages();
    }




    /**
     * Initializes the messages used within the game by populating the message repository.
     */
    private void initializeMessages() {
        messages.put("newRoundOutput","****************** %s - NEW ROUND *******************************");
        messages.put("startGameOutput","****************** GAME STARTED *******************************");
        messages.put("gameOverOutput", "****************** GAME OVER *******************************");
        messages.put("printOutQuestionCard", "The green-apple for this round is :   %s");
        messages.put("printFinishedGameOnline", "Player %s has won the game!");
        messages.put("printActionRequest", "Judge, wait for other players to submit their cards!");
        messages.put("printPlayedCards", "Following cards were played this round : %s" );
        messages.put("winnerOutPrint", "The winning card of the round was played by player: %s,   %s ");
        messages.put("printPlayedCard", "Wating for other players. The card you played this round was: %s");
        messages.put("printScoreBoard", "Scoreboard : %s");
        messages.put("playerWinnerOutput","The winner of the game is: %s ID %s");

    }

    /**
     * Retrieves a message based on the provided key and player, returning the corresponding message string.
     * @param key    The key used to retrieve the message from the message Hashmap.
     * @param player The player related to the message (if applicable).
     * @return The message string associated with the provided key and player.
     */
    public String getMessage(String key, Player player )  {

        String messageTemplate = messages.get(key);

        if (messageTemplate == null) {
            // Handle the case where the key is not found
            return "Message not found";
        }

        return switch (key) {
            case "newRoundOutput"       -> newRoundOutput(messageTemplate, player);
            case "printOutQuestionCard" -> String.format(messageTemplate, roundState.getRoundGreenApple());
            case "startGameOutput"      -> startGameOutput(messageTemplate);
            case "printActionRequest"   -> printActionTask(messageTemplate, player);
            case "printPlayedCards"     -> printPlayedCards(messageTemplate);
            case "winnerOutPrint"       ->
                    String.format(
                            messageTemplate
                            , this.roundState.getRoundWinner().getPlayerId()
                            , this.roundState.getWinningCard().getAppleCard()
                    );
            case "gameOverOutput"       -> String.format(messageTemplate);
            case "printScoreBoard"      -> String.format(messageTemplate,printScoreBoard());
            case "playerWinnerOutput"   -> printWinnerOutput(roundState.getGameWinner(),messageTemplate);
            default                     -> "Invalid key";
        };
    }

    private String printWinnerOutput(Player gameWinner, String messageTemplate) {
        String playerType = gameWinner instanceof BotPlayer ? "Bot" : "Player";
        int playerID  = gameWinner.getPlayerId();
        return  String.format(messageTemplate, playerType, playerID);
    }

    public String getMessage(String key, String msg) {
        String messageTemplate = messages.get(key);

        if (messageTemplate == null) {
            // Handle the case where the key is not found
            return "Message not found";
        }
        if (key.equals("printPlayedCard")) {
            return String.format(messageTemplate, msg);
        }
        return "Invalid key";

    }

    /**
     * This method generates the scoreboard based on each player's score,
     * @return The formatted scoreboard string .
     */
    private String printScoreBoard() {
        StringBuilder scoreboard = new StringBuilder();
        for (Player currentPlayer : playerList) {
            String playerType = (currentPlayer instanceof BotPlayer) ? "Bot" : "Player";
            int totalScore = currentPlayer.getScore();

            scoreboard.append("[").append(playerType).append(" ID ").append(currentPlayer.getPlayerId()).append(", Score: ").append(totalScore).append("]").append("\t");
        }

        return scoreboard.toString();
    }
    private String newRoundOutput(String message, Player player){
        String judgeText = roundState.isJudge(player) ? "JUDGE" : "PLAYER";
        return String.format(message, judgeText);
    }

    public String startGameOutput( String messageTemplate) {
        return String.format(messageTemplate);
    }

    /**
     * This method generates a string action task based on the provided message template and player,
     * taking into account the current round state.
     * @param messageTemplate The template for the message to be formatted.
     * @param player The player for whom the action task is intended.
     * @return The formatted string action task.
     */
    public String printActionTask(String messageTemplate,Player player)  {
        if(roundState.isJudge(player)){
            return String.format(messageTemplate);
        } else {
            return String.format(sendHandToPlayer(player));
        }
    }
    /**
     * If the player is an OnlinePlayer, the method joins the player's hand cards with "!!" delimiter.
     * Otherwise, it generates the host's hand using a different method.
     * @param player The player whose hand needs to be sent.
     * @return A formatted string representation of the player's hand suitable for sending over DataOutputStream.
     */
    private String sendHandToPlayer(Player player)  {
        if(player instanceof OnlinePlayer) {
            ArrayList<String> playerHand = player.getHand();
            return String.join("!!", playerHand);
        }
        else {
            return hostHandGenerator(player.getHand());
        }
    }

    /**
     * Formats and constructs a string with the played cards based on a message template.
     * @param messageTemplate The template to format the played cards.
     * @return A formatted string containing the played cards, based on the template.
     */
    private String printPlayedCards(String messageTemplate) {

        String playedCardsString = printStringOfPlayedCards(
                this.roundState.getRoundGreenApple(),
                this.roundState.getRoundPlayedCards()
        );
        return String.format(messageTemplate, playedCardsString) ;
    }
    /**
     * Generates a formatted string containing information about the played cards.
     * @param playedScenarioCard The scenario card played in the round.
     * @param playedCardList     List of cards played in the round.
     * @return A string representing the played cards' details.
     */
    private String printStringOfPlayedCards(String playedScenarioCard, ArrayList<PlayedCard> playedCardList) {
        StringBuilder playedCardsString = new StringBuilder(playedScenarioCard);

        for (int j = 0; j < playedCardList.size(); j++) {
            PlayedCard playedCard = playedCardList.get(j);
            playedCardsString.append("!!\t[").append(j).append("]").append(playedCard.getAppleCard());
        }
        return playedCardsString.toString();
    }

    public void printMsg(String msg) {
        System.out.println(msg.replaceAll("!!", "\n") + " \n"); //Some messages are separating items with two "!!"
    }

    private String hostHandGenerator(ArrayList<String> hand) {
       StringBuilder sb = new StringBuilder();
        sb.append("The following cards in your current hand are: \n");
        for (int i = 0; i < hand.size(); i++) {
           sb.append("[").append(i).append("] ").append(hand.get(i)).append("\n");
        }
        return sb.toString();
    }

    public static void numClientsRequest(){
        System.out.println("How many online players should join  [1-10]? :");
    }
    public static void hostServerRequest() {
        System.out.println("Would you like to host or join a server? [1 = Host, 0 = Joining]");
    }
    public static void onlineGameingRequest() {
        System.out.println("Should other online players be able to join? [1 = Yes, 0 = No] ");
    }
    public static void chooseDeckQuery() {
        //add more options after implementations of newer deck modes. like :2) Wild Apples to Apples"
        System.out.println("What game deck do you wanna use ? \n " +
                "0) Apples ´to apples \n" +
                " 1) WILD Apples to apples (the logic for this game mode is not implemented and is therefore not possible to choose) ");
    }

    public static void voteCardRequest() {
        System.out.println("Please vote for the best red apple :");
    }

    public static void lobbyCreatedPrint() {
        System.out.println("Gamelobby created. Please wait for other players to join");
    }
    public static void okConnectionPrint() {
        System.out.println("Player Connected!. Please wait for other players to join");
    }

}
