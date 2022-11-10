package model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
/**
 * classe que porta la lògica del torneig
 * @author Team Troner (grupC3)
 * @version v_final
 */
public class Tournament {
    private LinkedList<IdentifiedClient> players;
    private LinkedList<IdentifiedClient> viewers;
    private LinkedList<LinkedList<Round>> stages;
    private int currentStage;
    private int currentRound;
    private boolean waitingMode;

    /**
     * Constructor Tournament
     * @param participant1
     * @param participant2
     * @param participant3
     * @param participant4
     */
    public Tournament(IdentifiedClient participant1, IdentifiedClient participant2, IdentifiedClient participant3, IdentifiedClient participant4) {
        // Inicialment està en mode d'espera (no es penalitza la seva retirada)
        this.waitingMode = true;

        this.players = new LinkedList<>();
        this.players.add(participant1);
        this.players.add(participant2);
        this.players.add(participant3);
        this.players.add(participant4);

        // A l'inici, l'etapa és la 1 i dins d'aquesta, la partida (ronda) és la 1
        // Guardem 0 perquè la LinkedList comença en 0 i així l'accés serà més còmode
        this.currentStage = 0;
        this.currentRound = 0;

        // Definim les rondes/partides només la primera etapa ja que encara no sabem els participants de la segona i tercera etapa
        this.stages = new LinkedList<>();
        this.stages.add(new LinkedList<>());
        this.stages.get(0).add(
            new Round(
                participant1,
                participant2,
                participant3,
                participant4,
                this // Mode torneig
            )
        );
        this.stages.get(0).add(
            new Round(
                participant1,
                participant2,
                participant3,
                participant4,
                this // Mode torneig
            )
        );
        this.stages.get(0).add(
            new Round(
                participant1,
                participant2,
                participant3,
                participant4,
                this // Mode torneig
            )
        );

        this.viewers = new LinkedList<>();
    }// tancament constructor

    public LinkedList<IdentifiedClient> getPlayers() {
        return players;
    }

    public void setPlayers(LinkedList<IdentifiedClient> players) {
        this.players = players;
    }

    /**
     * Mètode per obtenir una llista amb els participants del torneig
     * @return llista d'usuaris
     */
    public LinkedList<IdentifiedClient> computeParticipants() {
        LinkedList<IdentifiedClient> participants = (LinkedList<IdentifiedClient>) this.players.clone();
        participants.addAll(this.viewers);
        return participants;
    }

    public LinkedList<LinkedList<Round>> getStages() {
        return stages;
    }

    public void setStages(LinkedList<LinkedList<Round>> stages) {
        this.stages = stages;
    }

    public int getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(int currentStage) {
        this.currentStage = currentStage;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    /**
     * Mètode per saber si encara s'esta en mode
     * @return llista d'usuaris
     */
    public boolean isWaitingMode() {
        return waitingMode;
    } // tancament mètode

    /**
     * Mètode per passar al mode d'espera en un torneig
     */
    public void setWaitingMode(boolean waitingMode) {
        this.waitingMode = waitingMode;
    }// tancament mètode

    public LinkedList<IdentifiedClient> getViewers() {
        return viewers;
    }

    public void setViewers(LinkedList<IdentifiedClient> viewers) {
        this.viewers = viewers;
    }

    /**
     * Mètode per saber en quina ronda del torneig ens trobem
     * @return opbjecte Round amb la informació necessaria
     */
    public Round getCurrentRound() {
        return this.stages.get(this.currentStage).get(this.currentRound);
    }

    /**
     * Mètode per comprovar si el torneig es troba en la seva última etapa
     * @return booleà
     */
    public boolean isLastStage() {
        return this.currentStage >= 2;
    }

    /**
     * Mètode per verificar que la ronda que queda es la darrera
     * @return booleà
     */
    public boolean isLastRound() {
        return this.currentStage >= 2 && this.currentRound >= 2;
    }

    /**
     * Mètode per anar ensenyant misstages de que es preparin el jugadors
     * @return string amb el missatge
     */
    public String generatePreparationMessage(boolean doNext) {
        int currentStage = this.currentStage;
        int currentRound = this.currentRound;

        if (doNext) {
            if (currentRound < 2) {
                currentRound++;
            } else {
                // Cal passar a la següent etapa (stage)
                currentStage++;
                currentRound = 0;
            }
        }

        switch (currentStage) {
            case 0:
                switch (currentRound) {
                    case 0:
                        return "Prepara't per a la ronda 1 de l'etapa 1 (4 jugadors)...";
                    case 1:
                        return "Prepara't per a la ronda 2 de l'etapa 1 (4 jugadors)...";
                    case 2:
                        return "Prepara't per a la ronda 3 de l'etapa 1 (4 jugadors)...";
                    default:
                        return "Prepara't per a la següent ronda de l'etapa 1 (4 jugadors)...";
                }
            case 1:
                switch (currentRound) {
                    case 0:
                        return "Prepara't per a la ronda 1 de l'etapa 2 (3 jugadors)...";
                    case 1:
                        return "Prepara't per a la ronda 2 de l'etapa 2 (3 jugadors)...";
                    case 2:
                        return "Prepara't per a la ronda 3 de l'etapa 2 (3 jugadors)...";
                    default:
                        return "Prepara't per a la següent ronda de l'etapa 2 (3 jugadors)...";
                }
            case 2:
                switch (currentRound) {
                    case 0:
                        return "Prepara't per a la ronda 1 de l'etapa 3 (2 jugadors)...";
                    case 1:
                        return "Prepara't per a la ronda 2 de l'etapa 3 (2 jugadors)...";
                    case 2:
                        return "Prepara't per a la ronda 3 de l'etapa 3 (2 jugadors)...";
                    default:
                        return "Prepara't per a la següent ronda de l'etapa 3 (2 jugadors)...";
                }
            default:
                return "Prepara't per a la següent ronda...";
        }
    }

    /**
     * Mètode per generar missatges sobre quina etapa es troben
     * @return string amb el misstage sobre quina ronda es
     */
    public String generateRoundTitle() {
        switch (this.currentStage) {
            case 0:
                switch (this.currentRound) {
                    case 0:
                        return "Etapa 1, ronda 1";
                    case 1:
                        return "Etapa 1, ronda 2";
                    case 2:
                        return "Etapa 1, ronda 3";
                    default:
                        return "Etapa 1";
                }
            case 1:
                switch (this.currentRound) {
                    case 0:
                        return "Etapa 2, ronda 1";
                    case 1:
                        return "Etapa 2, ronda 2";
                    case 2:
                        return "Etapa 2, ronda 3";
                    default:
                        return "Etapa 2";
                }
            case 2:
                switch (this.currentRound) {
                    case 0:
                        return "Etapa 3, ronda 1";
                    case 1:
                        return "Etapa 3, ronda 2";
                    case 2:
                        return "Etapa 3, ronda 3";
                    default:
                        return "Etapa 3";
                }
            default:
                return "Ronda";
        }
    }

    /**
     * Mètode per preparar la següent ronda
     * @return usuari preparat per a la ronda
     */
    public IdentifiedClient prepareNextRound() {
        // En primer lloc, revivim a tots els jugadors
        for (IdentifiedClient participant: this.computeParticipants()) {
            participant.getPlayingStatus().setAlive(true);
        }

        if (this.currentRound < 2) {
            this.currentRound++;
        }
        else {
            // Cal passar a la següent etapa (stage)
            this.stages.add(new LinkedList<>());
            this.currentStage++;
            this.currentRound = 0;
            return prepareNextStage();
        }

        // Com que hem canviat d'etapa cal reiniciar el comptador de rondes guanyades
        for (IdentifiedClient participant: this.computeParticipants()) {
            participant.getPlayingStatus().setWonRounds(0);
        }

        return null;
    }

    /**
     * Mètode per peparar la següent etapa
     * @return usuari per la següent etapa
     */
    private IdentifiedClient prepareNextStage() {
        // Retorna de l'última ronda de l'etapa anterior

        // En primer lloc calculem els punts de cada jugador de l'última ronda de l'etapa anterior
        List<IdentifiedClient> participants = this.stages.get(this.currentStage - 1).getLast().getParticipants();
        for (IdentifiedClient participant : participants) {
            participant.getPlayingStatus().setTotalPointsInTournament(this.computeTotalPointsOf(participant));

            // Si és espectador (ha mort en una etapa anterior) l'esborrem per a que no sigui candidat a estar en la següent etapa
            if (this.viewers.contains(participant)) {
                System.out.println("ES DONA EL CAS. SI NO, HO PODEM ESBORRAR ---------------------------------------------------------------------------------------------------------------------------------------------------");
                participants.remove(participant);
            }
        }

        // Ordenem els IdentifiedClient en funció de la puntuació total que han aconseguit
        Collections.sort(participants, (participant1, participant2) -> Integer.compare(participant2.getPlayingStatus().getTotalPointsInTournament(), participant1.getPlayingStatus().getTotalPointsInTournament()));

        // Els afegim a una nova llista a tots menys l'últim
        int numberOfPartipants = 4 - this.currentStage;
        LinkedList<IdentifiedClient> newParticipants = new LinkedList<>();
        for (int i = 0; i < numberOfPartipants; i++) {
            newParticipants.add(participants.get(i));
        }

        // Aprofitem per a esborrar el jugador del torneig i posar-lo com a espectador (al que no hem afegit en la nova etapa)
        // Això ens servirà per a futures operacions, com la creació de les següents etapes
        if (!this.players.remove(participants.get(numberOfPartipants))) {
            System.out.println("compte: no s'ha esborrat ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        };
        this.viewers.add(participants.get(numberOfPartipants));

        // Creem les partides/rondes de la nova etapa
        switch (newParticipants.size()) {
            case 2:
                // Reajustem els índexs dels jugadors en la nova partida (per a assegurar-nos de que són 1 i 2)
                newParticipants.get(0).getPlayingStatus().setIndexInGame(1);
                newParticipants.get(1).getPlayingStatus().setIndexInGame(2);

                // Ara sí, creem les partides/rondes de la nova etapa
                this.stages.get(this.currentStage).add(new Round(newParticipants.get(0), newParticipants.get(1), this));
                this.stages.get(this.currentStage).add(new Round(newParticipants.get(0), newParticipants.get(1), this));
                this.stages.get(this.currentStage).add(new Round(newParticipants.get(0), newParticipants.get(1), this));
                System.out.println("prepareNextStage amb newParticipants.size() de " + 2);
                break;
            case 3:
                // Reajustem els índexs dels jugadors en la nova partida (per a assegurar-nos de que són 1, 2 i 3)
                newParticipants.get(0).getPlayingStatus().setIndexInGame(1);
                newParticipants.get(1).getPlayingStatus().setIndexInGame(2);
                newParticipants.get(2).getPlayingStatus().setIndexInGame(3);

                // Ara sí, creem les partides/rondes de la nova etapa
                this.stages.get(this.currentStage).add(new Round(newParticipants.get(0), newParticipants.get(1), newParticipants.get(2), this));
                this.stages.get(this.currentStage).add(new Round(newParticipants.get(0), newParticipants.get(1), newParticipants.get(2), this));
                this.stages.get(this.currentStage).add(new Round(newParticipants.get(0), newParticipants.get(1), newParticipants.get(2), this));
                System.out.println("prepareNextStage amb newParticipants.size() de " + 3);
                break;
        }

        // Retornem el jugador que s'ha eliminat
        return participants.get(numberOfPartipants);
    }

    public void prepareNextStageWithout(IdentifiedClient identifiedClient) {
        // En primer lloc, revivim a tots els jugadors
        for (IdentifiedClient participant: this.computeParticipants()) {
            participant.getPlayingStatus().setAlive(true);
        }
        
        // Cal passar a la següent etapa (stage)
        this.stages.add(new LinkedList<>());
        this.currentStage++;
        this.currentRound = 0;
        
        // Els afegim a una nova llista a tots menys l'indicat
        // A més, cal afegir NOMÉS AQUELLS QUE NO SIGUIN ESPECTADORS
        // És a dir, aquells que encara no hagin perdut
        // Perquè no tindria sentit preparar les partides/rondes de la nova etapa amb els espectadors ara jugant
        LinkedList<IdentifiedClient> newParticipants = new LinkedList<>();
        for (IdentifiedClient participant: this.players) {
            if (participant != identifiedClient) {
                newParticipants.add(participant);
            }
        }
        
        // Creem les partides/rondes de la nova etapa
        switch (newParticipants.size()) {
            case 2:
                // Reajustem els índexs dels jugadors en la nova partida (per a assegurar-nos de que són 1 i 2)
                newParticipants.get(0).getPlayingStatus().setIndexInGame(1);
                newParticipants.get(1).getPlayingStatus().setIndexInGame(2);

                // Ara sí, creem les partides/rondes de la nova etapa
                this.stages.get(this.currentStage).add(new Round(newParticipants.get(0), newParticipants.get(1), this));
                this.stages.get(this.currentStage).add(new Round(newParticipants.get(0), newParticipants.get(1), this));
                this.stages.get(this.currentStage).add(new Round(newParticipants.get(0), newParticipants.get(1), this));
                break;
            case 3:
                // Reajustem els índexs dels jugadors en la nova partida (per a assegurar-nos de que són 1, 2 i 3)
                newParticipants.get(0).getPlayingStatus().setIndexInGame(1);
                newParticipants.get(1).getPlayingStatus().setIndexInGame(2);
                newParticipants.get(2).getPlayingStatus().setIndexInGame(3);

                // Ara sí, creem les partides/rondes de la nova etapa
                this.stages.get(this.currentStage).add(new Round(newParticipants.get(0), newParticipants.get(1), newParticipants.get(2), this));
                this.stages.get(this.currentStage).add(new Round(newParticipants.get(0), newParticipants.get(1), newParticipants.get(2), this));
                this.stages.get(this.currentStage).add(new Round(newParticipants.get(0), newParticipants.get(1), newParticipants.get(2), this));
                break;
        }

        // Com que hem canviat d'etapa cal reiniciar el comptador de rondes guanyades
        for (IdentifiedClient participant: this.computeParticipants()) {
            participant.getPlayingStatus().setWonRounds(0);
        }
    }

    /**
     * Mètode per calcular les puntuacions totals per jugador
     * @return puntuació
     */
    public int computeTotalPointsOf(IdentifiedClient identifiedClient) {
        int totalPoints = 0;
        for (LinkedList<Round> stage : this.stages) {
            for (Round round : stage) {
                totalPoints = round.getPointsPerParticipant().get(identifiedClient).intValue();
            }
        }
        return totalPoints;
    }
}