package Tests;


import java.io.IOException;
import java.util.ArrayList;

import Hemtenta.Communication.PlayerCommunicationManager;
import Hemtenta.Factories.CardFactory.A2ADecks;
import Hemtenta.Factories.CardFactory.CardDeck;
import Hemtenta.Factories.CardFactory.CardDeckFactory;

import Hemtenta.Factories.PlayerFactory.PlayedCard;
import Hemtenta.Factories.PlayerFactory.Player;
import Hemtenta.Factories.PlayerFactory.PlayerLogic.BotPlayLogic;
import Hemtenta.Factories.PlayerFactory.PlayerManager;
import Hemtenta.GamePlay.PhaseManager.*;

import Hemtenta.IOhandler.UI;

import Hemtenta.Setup.GameSettings;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.*;
import org.junit.Before;



public class GameTests {
    private final CardDeckFactory gamedeck = new CardDeckFactory();
    private CardDeck deck;
    private GameState gameState;
    private GameSettings gameSettings;
    private final int HANDSIZE =  7;
    private RoundState roundState;
    private SetupPhase setupGame;
    private NextRoundPhase nextRoundPhase;
    ArrayList<Player> allPlayerList;
    @Before
    public void setUp() throws Exception {

        deck = gamedeck.createCardDeck("apples2apples");

        gameSettings =  new GameSettings(0);
        PlayerManager playerManager = new PlayerManager(gameSettings);
        gameState =     new GameState(playerManager.getAllPlayers(), deck);
        roundState =    new RoundState(gameState,0); //Phase A. sets up states for current round

        setupGame = new SetupPhase(gameState,HANDSIZE,roundState);
        PlayerCommunicationManager communication = new PlayerCommunicationManager(gameState.getAllPlayers(),
                new UI(roundState, gameState.getAllPlayers()));

        ScoreHandler scoreboardHandler = new ScoreHandler(gameState.getAllPlayers());
        nextRoundPhase = new NextRoundPhase(gameSettings, communication, gameState, scoreboardHandler);
        allPlayerList = gameState.getAllPlayers();

    }


    @Test
    public void testPlayerDeckRead() throws IOException {
        // Test 1) Read all the green apples (adjectives) from a file and add to the green apples deck.
        // Create an instance of A2ACards and check if playerDeck is not empty
        CardDeck a2aDeck = gamedeck.createCardDeck("apples2apples");
        assertNotNull(a2aDeck.getGreenAppleDeck());
        assertFalse(a2aDeck.getGreenAppleDeck().isEmpty());
    }

    @Test
    public void testQuestionDeckRead() throws IOException {
        // Test 2) Read all the red apples (nouns) from a file and add to the red apples deck.
        // Create an instance of A2ACards and check if questionDeck is not empty
        CardDeck a2aDeck = gamedeck.createCardDeck("apples2apples");
        assertNotNull(a2aDeck.getRedAppleDeck());
        assertFalse(a2aDeck.getRedAppleDeck().isEmpty());
    }

    @Test
    public void testDeckEquality() throws IOException {

        // Test 3)  Shuffle both the green apples and red apples decks

        // Shuffled Deck
        CardDeck shuffledDeck = deck;
        // unshuffled deck
        CardDeck unshuffleddeck = new A2ADecks();


        // Assert that the shuffled decks are not equal
        assertNotEquals(shuffledDeck.getRedAppleDeck(), unshuffleddeck.getRedAppleDeck());
        assertNotEquals(shuffledDeck.getGreenAppleDeck(), unshuffleddeck.getGreenAppleDeck());
    }




    @Test
    public void testPlayerHandsSize() {
        // Test 4). Deal seven red apples to each player, including the judge.

        //Sets up a new game lobby with new players (bots).
        PlayerManager playerManager = new PlayerManager(gameSettings);
        GameState gs =     new GameState(playerManager.getAllPlayers(), deck);
        RoundState rs =    new RoundState(gs,3);
        for(Player currentPlayer: gs.getAllPlayers()){
            assertTrue(currentPlayer.getHand().isEmpty());
        }

        //add 7 red apples to starting hand
        new SetupPhase(gs,7,rs);

        //Makes sure every player has 7 cards.
        for(Player currentPlayer: allPlayerList){
            assertEquals(currentPlayer.getHand().size(),7);
        }
    }







    @Test
    public void randomJudgeTest(){
        //Test 5) Randomise which player starts being the judge.

        int uniqueJudges = 0;

        for (int trial = 0; trial < 50; trial++) {
            //Gets current judge for first game setup
            int judge1 = roundState.getCurrentJudge();

            setupGame = new SetupPhase(gameState,HANDSIZE,roundState);
            //Gets a new randomized judge for game two
            int judge2 = roundState.getCurrentJudge();

            // If different judges are selected in two consecutive trials, increment uniqueJudges
            if (judge1 != judge2) {
                uniqueJudges++;
            }
        }

        //if at least one uniqueJudges then test is proven right.
        assertTrue(uniqueJudges > 0);

    }

    @Test

    public void greenAppleDrawTest()   {

        //Test 6) A green apple is drawn from the pile and shown to everyone

        String drawnGreenApple = roundState.getRoundGreenApple();

        // Assert that the drawn green apple card is not null or empty
        assertNotNull(drawnGreenApple);
        assertFalse(drawnGreenApple.isEmpty());



    }

    @Test

    public void redCardPlayTest() throws InterruptedException {

        //Test 7) All players (except the judge) choose one red apple from their hand (based on the green apple) and plays it.

        this.roundState = new RoundState(gameState,3);

        PlayCardPhase playPhase = new PlayCardPhase(gameState,new PlayerCommunicationManager(allPlayerList,new UI(roundState, allPlayerList)));
        //proves that 0 red apples card are played.
        assertTrue(roundState.getRoundPlayedCards().isEmpty());
        // Playing red cards
        playPhase.run(roundState);

        //proves that 3 red apples card are played (4 players where one is the judge => 4-1 = 3 red apples).
        assertEquals(roundState.getRoundPlayedCards().size(),3);

        //Shows that each player has placed a red apple and has 7-1 6 red apples in hand except the judge who has 7
        for (Player currentplayer : allPlayerList){
            if(currentplayer.getPlayerId() == roundState.getCurrentJudge()){
                assertEquals(currentplayer.getHand().size(), HANDSIZE);
            } else {
                assertEquals(currentplayer.getHand().size(), HANDSIZE-1);
            }
        }

    }

    @Test

    public void showRandomCards() throws InterruptedException {

        //Test 8. The printed order of the played red apples should be randomised before shown to everyone
        // (THIS TEST MAY FAIL DUO TO only 3 cards are being played and therefore the shuffled list may occure as the unshuffled one  )

        this.roundState = new RoundState(gameState,3);

        PlayCardPhase playCard = new PlayCardPhase(gameState,new PlayerCommunicationManager(allPlayerList,new UI(roundState, allPlayerList)));

        playCard.run(roundState);
        //List of played red apples before shuffled
        ArrayList<PlayedCard> initialCardList = new ArrayList<>(roundState.getRoundPlayedCards());

        VotePhase voteCard = new VotePhase(new PlayerCommunicationManager(allPlayerList,new UI(roundState, allPlayerList)),new ScoreHandler(allPlayerList));
        //Shuffles the played card list
        voteCard.showCards(roundState);

        //Test to prove that the lists are different before and after being shuffled
        assertNotEquals(initialCardList,roundState.getRoundPlayedCards());


    }

    @Test

    public void showCardTest() throws InterruptedException {

        //Test 9. All players (except the judge) must play their red apples before the results are shown

        this.roundState = new RoundState(gameState,3);
        //Shows that the played cardlist is empty when not a single card is played
        VotePhase v1 = new VotePhase(new PlayerCommunicationManager(allPlayerList,new UI(roundState, allPlayerList)),new ScoreHandler(allPlayerList));
        v1.showCards(roundState);
        assertTrue(roundState.getRoundPlayedCards().isEmpty());

        //Plays red apple cards
        PlayCardPhase p1 = new PlayCardPhase(gameState,new PlayerCommunicationManager(allPlayerList,new UI(roundState, allPlayerList)));
        assertTrue(roundState.getRoundPlayedCards().isEmpty());
        p1.run(roundState);
        assertTrue(!roundState.getRoundPlayedCards().isEmpty());
    }


    @Test

    public void addScoreTest()  {
        // Test 10)The judge selects a favourite red apple. The player who submitted the favourite red apple is rewarded the green
        //apple as a point (rule 14)

        BotPlayLogic b1 = new BotPlayLogic(gameSettings);

        for (Player currentPlayer : allPlayerList){
            assertEquals(currentPlayer.getScore(),0);
        }


        PlayedCard testCard1 = new PlayedCard(0,"test1");
        PlayedCard testCard2 = new PlayedCard(1,"test2");
        PlayedCard testCard3 = new PlayedCard(2,"test3");

        roundState.getRoundPlayedCards().add(testCard1);
        roundState.getRoundPlayedCards().add(testCard2);
        roundState.getRoundPlayedCards().add(testCard3);

        PlayedCard winningCard = b1.chooseCardJudge(roundState.getRoundPlayedCards());
        int playerID = winningCard.getPlayerID();
        Player winningPlayer = allPlayerList.get(playerID);
        winningPlayer.addScore(winningCard.getAppleCard());


        for (Player currentPlayer : allPlayerList){
            if(currentPlayer == winningPlayer){
                assertEquals(winningPlayer.getScore(),1);
            } else {
                assertEquals(currentPlayer.getScore(),0);
            }

        }
        assertTrue(winningPlayer.getWinningCards().contains(testCard1.getAppleCard())
                || winningPlayer.getWinningCards().contains(testCard2.getAppleCard())
                || winningPlayer.getWinningCards().contains(testCard3.getAppleCard()));



    }

    @Test
    public void discardTest() {
        // Test 11) All the submitted red apples are discarded
        PlayedCard testCard1 = new PlayedCard(0,"test1");
        PlayedCard testCard2 = new PlayedCard(1,"test2");
        PlayedCard testCard3 = new PlayedCard(2,"test3");

        roundState.getRoundPlayedCards().add(testCard1);
        roundState.getRoundPlayedCards().add(testCard2);
        roundState.getRoundPlayedCards().add(testCard3);

        assertEquals(roundState.getRoundPlayedCards().size(),3);
        nextRoundPhase.run(roundState);
        assertEquals(roundState.getRoundPlayedCards().size(),0);



    }
    @Test
    public void drawCardTest() {
        //Test 12) All players are given new red apple cards until they have 7 in hand
        final int cardsToDraw = 4;
        // Ensure all non-judge players have played cardsToDraw number of red apples from hand
        for (Player currentPlayer : allPlayerList){
            if(currentPlayer.getPlayerId() != roundState.getCurrentJudge()){

                helpPlay(cardsToDraw,currentPlayer);
                assertEquals(currentPlayer.getHand().size(),HANDSIZE-cardsToDraw);
            }
        }
        //Draws and adds right amount of cards
        nextRoundPhase.run(roundState);

        // Ensure that each player has 7(full hand) red apples in hand after new round
        for (Player currentPlayer : allPlayerList){
            assertEquals(currentPlayer.getHand().size(),HANDSIZE);
        }


    }
    private void helpPlay(int num, Player player){
        for (int i = 0; i < num; i++) {
            player.SubmitCard(i);
        }
    }

    @Test
    public void switchJudgeTest() {
        // Test 13) The next player in the list becomes the judge
        for(int i = 0 ; i < 10; i++){
            int currentJudge = roundState.getCurrentJudge();

            nextRoundPhase.run(roundState);

            int nextRoundJudge = roundState.getCurrentJudge();
            assertNotEquals(currentJudge,nextRoundJudge);


        }



    }

    @Test
    public void storeTrackingTest()  {
      //Test 14) Keep score by keeping the green apples you’ve won
        PlayedCard testCard1 = new PlayedCard(0,"Test1");


        Player testPlayer = allPlayerList.get(3);
        int score = testPlayer.getScore();
        assertEquals(score,0);


        testPlayer.addScore(testCard1.getAppleCard());
        int updatedScore = testPlayer.getScore();

        assertEquals(score + 1, updatedScore);
        assertTrue(testPlayer.getWinningCards().contains(testCard1.getAppleCard()));



    }





    @Test
    public void winningscoreTester(){
        // 15) Test with different player counts
        //Number of total players lobby is always onlineplayerCount + 1 (since hostplayer)
        int fourPlayers = new GameSettings(3).getScoreToWin();
        int fivePlayers = new GameSettings(4).getScoreToWin();
        int sixPlayers = new GameSettings(5).getScoreToWin();
        int sevenPlayers = new GameSettings(6).getScoreToWin();
        int eightPlayer = new GameSettings(7).getScoreToWin();
        int eightPlusPlayer = new GameSettings(12).getScoreToWin();


        assertEquals(8, fourPlayers);
        assertEquals(7, fivePlayers);
        assertEquals(6, sixPlayers);
        assertEquals(5, sevenPlayers);
        assertEquals(4, eightPlayer);
        assertEquals(4, eightPlusPlayer);
    }
}


