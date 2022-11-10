package controller.round;

import model.Round;
import model.IdentifiedClient;
import network.responses.GameOverResponse;

import java.io.IOException;

/**
 * Created by cristian on 30/6/17.
 */
public class PlayerDeathNotifier {
    public static void notifyGameOverOf(IdentifiedClient identifiedClient, Round round) {
        // D'aquesta manera el podran aturar visualment
        for (IdentifiedClient participant : round.getParticipants()) {
            if (participant == identifiedClient) {
                try {
                    GameOverResponse gameOverResponse = new GameOverResponse(true, identifiedClient.getPlayingStatus().getIndexInGame(), true);
                    participant.getObjectOutputStream().writeObject(gameOverResponse);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    GameOverResponse gameOverResponse = new GameOverResponse(true, identifiedClient.getPlayingStatus().getIndexInGame(), false);
                    participant.getObjectOutputStream().writeObject(gameOverResponse);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // També cal avisar a tots els espectadors de que identifiedClient ha perdut
        // Si el joc pertany a un torneig pot tenir espectadors. Per tant, cal avisar-los
        if (round.getAssociatedTournament() != null) {
            // Avisem a tots els espectadors de que identifiedClient ha perdut
            for (IdentifiedClient viewer : round.getAssociatedTournament().getViewers()) {
                // Només fem això si l'espectador no és un participant (si no comprovéssim això possiblement ho enviaríem dos cops)
                if (!round.getParticipants().contains(viewer)) {
                    try {
                        GameOverResponse gameOverResponse = new GameOverResponse(true, identifiedClient.getPlayingStatus().getIndexInGame(), false);
                        viewer.getObjectOutputStream().writeObject(gameOverResponse);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}