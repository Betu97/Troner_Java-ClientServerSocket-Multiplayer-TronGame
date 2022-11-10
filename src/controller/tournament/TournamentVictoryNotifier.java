package controller.tournament;

import model.IdentifiedClient;
import network.responses.TournamentResultResponse;

import java.io.IOException;

/**
 * Created by cristian on 30/6/17.
 */
public class TournamentVictoryNotifier {
    public static void notifyForcedVictoryAfterDepartureOf(IdentifiedClient identifiedClient) {
        // Ja estem en l'última etapa (2 jugadors)
        // Per tant, el jugador restant (el que no ha demanat abandonar) és el guanyador
        // Cal avisar només a aquest jugador
        // El cerquem
        for (IdentifiedClient participant: identifiedClient.getPlayingStatus().getRound().getParticipants()) {
            System.out.println("De moment NO l'hem trobat");
            if (participant != identifiedClient && participant.getPlayingStatus().isAlive()) {
                System.out.println("Ara sí: l'hem trobat");
                // L'hem trobat
                // Preparem la TournamentResultResponse
                TournamentResultResponse tournamentResultResponse = new TournamentResultResponse(
                        true,
                        participant.getPlayingStatus().getPositionInRound(),
                        participant.getPlayingStatus().getPointsInRound(),
                        participant.getPlayingStatus().getTotalPointsInTournament()
                );
                try {
                    participant.getObjectOutputStream().writeObject(tournamentResultResponse);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Com que sabem que això només ho volem fer per a 1 usuari podem acabar el bucle aquí
                break;
            }
        }
    }
}