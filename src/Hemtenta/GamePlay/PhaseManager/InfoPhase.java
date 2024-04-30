package Hemtenta.GamePlay.PhaseManager;

import Hemtenta.Communication.PlayerCommunicationManager;


/**
 * The InfoPhase class represents the phase in the gameplay that handles informing players
 * about the current round. It sets a green apple card for the round and communicates
 * information to all players via the provided PlayerCommunicationManager.
 */
public class InfoPhase {

    private final PlayerCommunicationManager communication;

    public InfoPhase(PlayerCommunicationManager communication)   {
        this.communication = communication;
    }

    public void run(RoundState roundState){
        roundState.setGreenAppleCard();
        gameinfoPlayers();
    }

    private void gameinfoPlayers() {
        communication.sendToAllPlayers("newRoundOutput","PRINT");
        communication.sendToAllPlayers("printOutQuestionCard","PRINT");
        communication.sendToAllPlayers("printActionRequest","PLAY");

    }


}
