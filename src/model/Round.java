package model;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * classe encarregada de controlar el diferents tipus de partida i de resetejar-los quan es necessari.
 * A més, és l'encarregada de controlar el moviment de jugador depenent de si vol mourer's a l'esquerra, dreta, adalt o abaix
 * @author Team Troner (grupC3)
 * @version v_final
 */
public class Round {
    private LinkedList<IdentifiedClient> participants;
    private HashMap<IdentifiedClient, Integer> pointsPerParticipant;
    private int[][] positionsMap;
    private HashMap<IdentifiedClient, int[]> positions;
    private HashMap<IdentifiedClient, Integer> directions;
    private Tournament associatedTournament;
    private int roundType;
    private boolean waitingMode;

    /**
     * Constructor Round per al mode 2X
     * @param participant1
     * @param participant2
     */
    public Round(IdentifiedClient participant1, IdentifiedClient participant2) {
        reset(participant1, participant2, 1);

        // Com que es tracta de l'inici del mode 2X...
        participant1.getPlayingStatus().setWonRounds(0);
        participant2.getPlayingStatus().setWonRounds(0);
    }

    /**
     * Constructor Round per al mode tournament
     * @param participant1
     * @param participant2
     * @param associatedTournament
     */
    public Round(IdentifiedClient participant1, IdentifiedClient participant2, Tournament associatedTournament) {
        reset(participant1, participant2, 3);
        this.associatedTournament = associatedTournament;
    }

    /**
     * Mètode per resetejar la partida al finalitzar una, aquest serveix per a la partida 2X i el torneig al quedar 2 jugadors
     * @param participant1
     * @param participant2
     * @param gameType
     */
    public void reset(IdentifiedClient participant1, IdentifiedClient participant2, int gameType) {
        // Inicialment està en mode d'espera
        this.waitingMode = true;
        // Com que la partida és de 2 jugadors, pot ser de tipus 1 (2X) o de tipus 3 (torneig)
        this.roundType = gameType;

        this.participants = new LinkedList<>();
        this.participants.add(participant1);
        this.participants.add(participant2);

        participant1.getPlayingStatus().setPositionInGame(1);// Per defecte és el guanyador
        participant1.getPlayingStatus().setPointsInGame(0);// Per defecte té 0 punts
        participant1.getPlayingStatus().setAlive(true);// Per defecte està viu

        participant2.getPlayingStatus().setPositionInGame(1);// Per defecte és el guanyador
        participant2.getPlayingStatus().setPointsInGame(0);// Per defecte té 0 punts
        participant2.getPlayingStatus().setAlive(true);// Per defecte està viu

        this.pointsPerParticipant = new HashMap<>(2);
        this.pointsPerParticipant.put(participant1, new Integer(0));// Per defecte té 0 punts
        this.pointsPerParticipant.put(participant2, new Integer(0));// Per defecte té 0 punts

        // Com que la partida és de 2 jugadors...
        positionsMap = new int[600][600];

        positions = new HashMap<>(2);
        positions.put(participant1, new int[2]);
        (positions.get(participant1))[0] = 50;
        (positions.get(participant1))[1] = 300;
        positions.put(participant2, new int[2]);
        (positions.get(participant2))[0] = 550;
        (positions.get(participant2))[1] = 300;

        directions = new HashMap<>(2);
        directions.put(participants.get(0), 0);// Significa "right"
        directions.put(participants.get(1), 1);// Significa "left"

        // Inicialitzem la matriu per a que tingui tot 0
        for(int i = 0; i < 600; i++){
            for(int j = 0; j < 600; j++){
                positionsMap[i][j] = 0;
            }
        }
        
        // Col·loquem els jugadors en la seva posició inicial
        positionsMap[(positions.get(participant1))[0]][(positions.get(participant1))[1]] = 1;
        positionsMap[(positions.get(participant2))[0]][(positions.get(participant2))[1]] = 2;
    }

    public Round(IdentifiedClient participant1, IdentifiedClient participant2, IdentifiedClient participant3, Tournament associatedTournament) {
        reset(participant1, participant2, participant3);
        this.associatedTournament = associatedTournament;
    }

    /**
     * Mètode per resetejar la partida al finalitzar una, aquest serveix per a la partida quan queden 3 jugadors
     * @param participant1
     * @param participant2
     * @param participant3
     */
    public void reset(IdentifiedClient participant1, IdentifiedClient participant2, IdentifiedClient participant3) {
        // Inicialment està en mode d'espera
        this.waitingMode = true;
        // Com que la partida és de 3 jugadors, segur que és de tipus 3 (Torneig)
        this.roundType = 3;

        this.participants = new LinkedList<>();
        this.participants.add(participant1);
        this.participants.add(participant2);
        this.participants.add(participant3);

        participant1.getPlayingStatus().setPositionInGame(1);// Per defecte és el guanyador
        participant1.getPlayingStatus().setPointsInGame(0);// Per defecte té 0 punts
        participant1.getPlayingStatus().setAlive(true);// Per defecte està viu

        participant2.getPlayingStatus().setPositionInGame(1);// Per defecte és el guanyador
        participant2.getPlayingStatus().setPointsInGame(0);// Per defecte té 0 punts
        participant2.getPlayingStatus().setAlive(true);// Per defecte està viu

        participant3.getPlayingStatus().setPositionInGame(1);// Per defecte és el guanyador
        participant3.getPlayingStatus().setPointsInGame(0);// Per defecte té 0 punts
        participant3.getPlayingStatus().setAlive(true);// Per defecte està viu

        this.pointsPerParticipant = new HashMap<>(2);
        this.pointsPerParticipant.put(participant1, new Integer(0));// Per defecte té 0 punts
        this.pointsPerParticipant.put(participant2, new Integer(0));// Per defecte té 0 punts
        this.pointsPerParticipant.put(participant3, new Integer(0));// Per defecte té 0 punts

        // Com que la partida és de 3 jugadors...
        positionsMap = new int[600][600];

        positions = new HashMap<>(3);
        positions.put(participant1, new int[2]);
        (positions.get(participant1))[0] = 50;
        (positions.get(participant1))[1] = 50;
        positions.put(participant2, new int[2]);
        (positions.get(participant2))[0] = 550;
        (positions.get(participant2))[1] = 50;
        positions.put(participant3, new int[2]);
        (positions.get(participant3))[0] = 300;
        (positions.get(participant3))[1] = 550;

        directions = new HashMap<>(3);
        directions.put(participants.get(0), 3);// Significa "down"
        directions.put(participants.get(1), 3);// Significa "down"
        directions.put(participants.get(2), 2);// Significa "up"

        // Inicialitzem la matriu per a que tingui tot 0
        for (int i = 0; i < 600; i++){
            for (int j = 0; j < 600; j++){
                positionsMap[i][j] = 0;
            }
        }

        // Col·loquem els jugadors en la seva posició inicial
        positionsMap[(positions.get(participant1))[0]][(positions.get(participant1))[1]] = 1;
        positionsMap[(positions.get(participant2))[0]][(positions.get(participant2))[1]] = 2;
        positionsMap[(positions.get(participant3))[0]][(positions.get(participant3))[1]] = 3;
    }

    public Round(IdentifiedClient participant1, IdentifiedClient participant2, IdentifiedClient participant3, IdentifiedClient participant4) {
        reset(participant1, participant2, participant3, participant4, 2);

        // Com que es tracta de l'inici del mode 4X...
        participant1.getPlayingStatus().setWonRounds(0);
        participant2.getPlayingStatus().setWonRounds(0);
        participant3.getPlayingStatus().setWonRounds(0);
        participant4.getPlayingStatus().setWonRounds(0);
    }

    public Round(IdentifiedClient participant1, IdentifiedClient participant2, IdentifiedClient participant3, IdentifiedClient participant4, Tournament associatedTournament) {
        reset(participant1, participant2, participant3, participant4, 3);
        this.associatedTournament = associatedTournament;
    }
    /**
     * Mètode per resetejar la partida al finalitzar una, aquest serveix per a la partida 4X
     * @param participant1
     * @param participant2
     * @param participant3
     * @param participant4
     * @param gameType
     */
    public void reset(IdentifiedClient participant1, IdentifiedClient participant2, IdentifiedClient participant3, IdentifiedClient participant4, int gameType) {
        // Inicialment està en mode d'espera
        this.waitingMode = true;
        // Com que la partida és de 4 jugadors, pot ser de tipus 2 o 3 (4X o Torneig)
        this.roundType = gameType;

        this.participants = new LinkedList<>();
        this.participants.add(participant1);
        this.participants.add(participant2);
        this.participants.add(participant3);
        this.participants.add(participant4);

        participant1.getPlayingStatus().setPositionInGame(1);// Per defecte és el guanyador
        participant1.getPlayingStatus().setPointsInGame(0);// Per defecte té 0 punts
        participant1.getPlayingStatus().setAlive(true);// Per defecte està viu

        participant2.getPlayingStatus().setPositionInGame(1);// Per defecte és el guanyador
        participant2.getPlayingStatus().setPointsInGame(0);// Per defecte té 0 punts
        participant2.getPlayingStatus().setAlive(true);// Per defecte està viu

        participant3.getPlayingStatus().setPositionInGame(1);// Per defecte és el guanyador
        participant3.getPlayingStatus().setPointsInGame(0);// Per defecte té 0 punts
        participant3.getPlayingStatus().setAlive(true);// Per defecte està viu

        participant4.getPlayingStatus().setPositionInGame(1);// Per defecte és el guanyador
        participant4.getPlayingStatus().setPointsInGame(0);// Per defecte té 0 punts
        participant4.getPlayingStatus().setAlive(true);// Per defecte està viu

        this.pointsPerParticipant = new HashMap<>(2);
        this.pointsPerParticipant.put(participant1, new Integer(0));// Per defecte té 0 punts
        this.pointsPerParticipant.put(participant2, new Integer(0));// Per defecte té 0 punts
        this.pointsPerParticipant.put(participant3, new Integer(0));// Per defecte té 0 punts
        this.pointsPerParticipant.put(participant4, new Integer(0));// Per defecte té 0 punts

        // Com que la partida és de 4 jugadors...
        positionsMap = new int[600][600];

        positions = new HashMap<>(4);
        positions.put(participant1, new int[2]);
        (positions.get(participant1))[0] = 50;
        (positions.get(participant1))[1] = 50;
        positions.put(participant2, new int[2]);
        (positions.get(participant2))[0] = 550;
        (positions.get(participant2))[1] = 50;
        positions.put(participant3, new int[2]);
        (positions.get(participant3))[0] = 50;
        (positions.get(participant3))[1] = 550;
        positions.put(participant4, new int[2]);
        (positions.get(participant4))[0] = 550;
        (positions.get(participant4))[1] = 550;

        directions = new HashMap<>(4);
        directions.put(participants.get(0), 0);// Significa "right"
        directions.put(participants.get(1), 1);// Significa "left"
        directions.put(participants.get(2), 0);// Significa "right"
        directions.put(participants.get(3), 1);// Significa "left"

        // Inicialitzem la matriu per a que tingui tot 0
        for(int i = 0; i < 600; i++){
            for(int j = 0; j < 600; j++){
                positionsMap[i][j] = 0;
            }
        }

        // Col·loquem els jugadors en la seva posició inicial
        positionsMap[(positions.get(participant1))[0]][(positions.get(participant1))[1]] = 1;
        positionsMap[(positions.get(participant2))[0]][(positions.get(participant2))[1]] = 2;
        positionsMap[(positions.get(participant3))[0]][(positions.get(participant3))[1]] = 3;
        positionsMap[(positions.get(participant4))[0]][(positions.get(participant4))[1]] = 4;
    }

    /**
     * Mètode per obtenir el participants
     * @return una llista amb el usuaris loggejats
     */
    public LinkedList<IdentifiedClient> getParticipants() {
        return participants;
    }

    /**
     * Mètode per obtenir la puntuació per jugador
     * @return un mapa amb l'usuari apuntant a la puntuació en concret
     */
    public HashMap<IdentifiedClient, Integer> getPointsPerParticipant() {
        return pointsPerParticipant;
    }

    /**
     * Mètode per obtenir el mapa amb les posicions
     * @return un array de dos dimensions amb les pòsicions del mapa
     */
    public int[][] getPositionsMap() {
        return positionsMap;
    }

    /**
     * Mètode per obtenir les posicions de cada un dels jugadors
     * @return un mapa apuntant a un array de posicions depenent del jugador
     */
    public HashMap<IdentifiedClient, int[]> getPositions() {
        return positions;
    }

    /**
     * Mètode per obtenir les direccions de cada un dels jugadors
     * @return un mapa apuntant a un enter de direcció depenent del jugador
     */
    public HashMap<IdentifiedClient, Integer> getDirections() {
        return directions;
    }

    /**
     * Mètode per obtenir el tipus de joc
     * @return un enter què és el tipus de joc
     */
    public int getRoundType() {
        return roundType;
    }

    public void setRoundType(int roundType) {
        this.roundType = roundType;
    }

    /**
     * Mètode per obtenir un objecte de tipus torneig
     * @return objecte tipus torneig associat al joc que s'està jugant
     */
    public Tournament getAssociatedTournament() {
        return associatedTournament;
    }

    public void setAssociatedTournament(Tournament associatedTournament) {
        this.associatedTournament = associatedTournament;
    }

    /**
     * Mètode per saber si estem en mode d'espera abans de començar un joc
     * @return un booleà que determina si s'està en mode d'espera
     */
    public boolean isWaitingMode() {
        return waitingMode;
    }

    /**
     * Mètode per passar a mode d'espera
     */
    public void setWaitingMode(boolean waitingMode) {
        this.waitingMode = waitingMode;

        // Si té un torneig associat, propaguem el mode d'espera
        if (this.associatedTournament != null) {
            this.associatedTournament.setWaitingMode(waitingMode);
        }
    }

    /**
     * Mètode per obtenir la direcció d'un jugador
     * @return un enter de forma sincronitzada amb lo qual només pot accedir-hi de forma ordenada
     */
    public synchronized int getDirectionOf(IdentifiedClient identifiedClient) {
        return this.directions.get(identifiedClient);
    }

    /**
     * Mètode per crear un nou RoundStatus
     * @return un objecte tipus RoundStatus
     */
    public synchronized RoundStatus prepareStatus() {
        return new RoundStatus(this);
    }

    /**
     * Mètode per moure el jugador a la esquerra
     * @param identifiedClient
     */
    public synchronized boolean moveLeft(IdentifiedClient identifiedClient) {
        // Retorna false si no s'ha pogut moure (ha perdut), true si s'ha pogut moure

        // Com que estem en el servidor, aquest mètode treballa amb un LoogedInUser
        // Mirem si està en el límit
        try {
            if ((this.positions.get(identifiedClient)[0] > 0)) {
                // Com que no està en el límit, el podem moure
                int x = (positions.get(identifiedClient))[0];
                int y = (positions.get(identifiedClient))[1];
                // Mirem si hi ha un altre jugador o el mateix jugador en les 5 següents caselles cap a left
                if (positionsMap[x - 1 - 4][y + 0] == 0 &&
                        positionsMap[x - 1 - 4][y + 1] == 0 &&
                        positionsMap[x - 1 - 4][y + 2] == 0 &&
                        positionsMap[x - 1 - 4][y + 3] == 0 &&
                        positionsMap[x - 1 - 4][y + 4] == 0) {
                    int[] newPosition = this.positions.get(identifiedClient);
                    newPosition[0] -= 1;
                    this.positions.put(identifiedClient, newPosition);

                    // Col·loquem el jugador en la seva posició nova
                    positionsMap[(positions.get(identifiedClient))[0]][(positions.get(identifiedClient))[1]] = identifiedClient.getPlayingStatus().getIndexInGame();

                    return true;
                }
            }
        }
        catch (java.lang.ArrayIndexOutOfBoundsException e) {
            // Si ens hem sortit fora del array vol dir que moure el jugador implicaria posar-lo fora del mapa
            // Per tant, no hem pogut moure el jugador -> return false
            return false;
        }

        // No hem pogut moure el jugador perquè sortiria fora del mapa o bé impactaria contra un altre jugador
        // Com que no hem pogut moure el jugador -> return false
        return false;
    }
    /**
     * Mètode per moure el jugador a la dreta
     * @param identifiedClient
     */
    public synchronized boolean moveRight(IdentifiedClient identifiedClient) {
        // Retorna false si no s'ha pogut moure (ha perdut), true si s'ha pogut moure

        // Com que estem en el servidor, aquest mètode treballa amb un LoogedInUser
        // Mirem si està en el límit
        try {
            if ((this.positions.get(identifiedClient)[0] < 599)) {
                // Com que no està en el límit, el podem moure
                int x = (positions.get(identifiedClient))[0];
                int y = (positions.get(identifiedClient))[1];
                // Mirem si hi ha un altre jugador o el mateix jugador en les 5 següents caselles cap a right
                if (positionsMap[x + 1 + 4][y + 0] == 0 &&
                        positionsMap[x + 1 + 4][y + 1] == 0 &&
                        positionsMap[x + 1 + 4][y + 2] == 0 &&
                        positionsMap[x + 1 + 4][y + 3] == 0 &&
                        positionsMap[x + 1 + 4][y + 4] == 0) {
                    int[] newPosition = this.positions.get(identifiedClient);
                    newPosition[0] += 1;
                    this.positions.put(identifiedClient, newPosition);

                    // Col·loquem el jugador en la seva posició nova
                    //System.out.println("la seva posició nova" + );
                    positionsMap[(positions.get(identifiedClient))[0]][(positions.get(identifiedClient))[1]] = identifiedClient.getPlayingStatus().getIndexInGame();

                    return true;
                }
            }
        }
        catch (java.lang.ArrayIndexOutOfBoundsException e) {
            // Si ens hem sortit fora del array vol dir que moure el jugador implicaria posar-lo fora del mapa
            // Per tant, no hem pogut moure el jugador -> return false
            return false;
        }

        // No hem pogut moure el jugador perquè sortiria fora del mapa o bé impactaria contra un altre jugador
        // Com que no hem pogut moure el jugador -> return false
        return false;
    }

    /**
     * Mètode per moure el jugador cap abaix
     * @param identifiedClient
     */
    public synchronized boolean moveDown(IdentifiedClient identifiedClient) {
        // Retorna false si no s'ha pogut moure (ha perdut), true si s'ha pogut moure

        // Com que estem en el servidor, aquest mètode treballa amb un LoogedInUser
        // Mirem si està en el límit
        try {
            if ((this.positions.get(identifiedClient)[1] < 599)) {
                // Com que no està en el límit, el podem moure
                int x = (positions.get(identifiedClient))[0];
                int y = (positions.get(identifiedClient))[1];
                // Mirem si hi ha un altre jugador o el mateix jugador en les 5 següents caselles cap a down
                if (positionsMap[x + 0][y + 1 + 4] == 0 &&
                        positionsMap[x + 1][y + 1 + 4] == 0 &&
                        positionsMap[x + 2][y + 1 + 4] == 0 &&
                        positionsMap[x + 3][y + 1 + 4] == 0 &&
                        positionsMap[x + 4][y + 1 + 4] == 0) {
                    int[] newPosition = this.positions.get(identifiedClient);
                    newPosition[1] += 1;
                    this.positions.put(identifiedClient, newPosition);

                    // Col·loquem el jugador en la seva posició nova
                    positionsMap[(positions.get(identifiedClient))[0]][(positions.get(identifiedClient))[1]] = identifiedClient.getPlayingStatus().getIndexInGame();

                    return true;
                }
            }
        }
        catch (java.lang.ArrayIndexOutOfBoundsException e) {
            // Si ens hem sortit fora del array vol dir que moure el jugador implicaria posar-lo fora del mapa
            // Per tant, no hem pogut moure el jugador -> return false
            return false;
        }

        // No hem pogut moure el jugador perquè sortiria fora del mapa o bé impactaria contra un altre jugador
        // Com que no hem pogut moure el jugador -> return false
        return false;
    }

    /**
     * Mètode per moure el jugador cap adalt
     * @param identifiedClient
     */
    public synchronized boolean moveUp(IdentifiedClient identifiedClient) {
        // Retorna false si no s'ha pogut moure (ha perdut), true si s'ha pogut moure

        // Com que estem en el servidor, aquest mètode treballa amb un LoogedInUser
        // Mirem si està en el límit
        try {
            if ((this.positions.get(identifiedClient)[1] > 0)) {
                // Com que no està en el límit, el podem moure
                int x = (positions.get(identifiedClient))[0];
                int y = (positions.get(identifiedClient))[1];
                // Mirem si hi ha un altre jugador o el mateix jugador en les 5 següents caselles cap a up
                if (positionsMap[x + 0][y - 1 - 4] == 0 &&
                        positionsMap[x + 1][y - 1 - 4] == 0 &&
                        positionsMap[x + 2][y - 1 - 4] == 0 &&
                        positionsMap[x + 3][y - 1 - 4] == 0 &&
                        positionsMap[x + 4][y - 1 - 4] == 0) {
                    int[] newPosition = this.positions.get(identifiedClient);
                    newPosition[1] -= 1;
                    this.positions.put(identifiedClient, newPosition);

                    // Col·loquem el jugador en la seva posició nova
                    positionsMap[(positions.get(identifiedClient))[0]][(positions.get(identifiedClient))[1]] = identifiedClient.getPlayingStatus().getIndexInGame();

                    return true;
                }
            }
        }
        catch (java.lang.ArrayIndexOutOfBoundsException e) {
            // Si ens hem sortit fora del array vol dir que moure el jugador implicaria posar-lo fora del mapa
            // Per tant, no hem pogut moure el jugador -> return false
            return false;
        }

        // No hem pogut moure el jugador perquè sortiria fora del mapa o bé impactaria contra un altre jugador
        // Com que no hem pogut moure el jugador -> return false
        return false;
    }

    /**
     * Mètode per "calcular i verificar" el canvi de direcció
     * @param identifiedClient
     * @param newDirection
     */
    public synchronized void changeDirection(IdentifiedClient identifiedClient, int newDirection) {
        this.directions.put(identifiedClient, newDirection);
    }

    public synchronized boolean move(IdentifiedClient identifiedClient) {
        // A priori, establim que el moviment no generi un round over
        boolean gameOver = false;

        if (this.getDirectionOf(identifiedClient) == 0) {
            if (!this.moveRight(identifiedClient)) {
                // Ha perdut
                gameOver = true;
            }
        }
        if (this.getDirectionOf(identifiedClient) == 1) {
            if (!this.moveLeft(identifiedClient)) {
                // Ha perdut
                gameOver = true;
            }
        }
        if (this.getDirectionOf(identifiedClient) == 2) {
            if (!this.moveUp(identifiedClient)) {
                // Ha perdut
                gameOver = true;
            }
        }
        if (this.getDirectionOf(identifiedClient) == 3) {
            if (!this.moveDown(identifiedClient)) {
                // Ha perdut
                gameOver = true;
            }
        }

        return gameOver;
    }

    public synchronized int computeResultOf(IdentifiedClient identifiedClient) {
        // Cal calcular la seva posició i puntuació per després quan acabi la partida mostrar la seva puntuació
        // Per a fer-ho, cal comprovar quants jugadors estan vius
        int nAliveParticipants = 0;
        for (IdentifiedClient participant : this.participants) {
            if (participant.getPlayingStatus().isAlive()) {
                nAliveParticipants++;
            }
        }
        // Llavors en funció dels jugadors que estan vius i els jugadors totals...
        // Nota: en aquest moment no s'assigna la puntuació del jugador guanyador
        // ja que això es farà al finalitzar la partida
        if (this.participants.size() == 2) {
            // Es tracta d'una partida de 2 jugadors
            if (nAliveParticipants == 1) {
                identifiedClient.getPlayingStatus().setPositionInGame(2);
                identifiedClient.getPlayingStatus().setPointsInGame(-1);
                this.pointsPerParticipant.put(identifiedClient, new Integer(-1));
            }
        }
        if (this.participants.size() == 3) {
            // Es tracta d'una partida de 3 jugadors
            if (nAliveParticipants == 2) {
                identifiedClient.getPlayingStatus().setPositionInGame(3);
                identifiedClient.getPlayingStatus().setPointsInGame(-1);
                this.pointsPerParticipant.put(identifiedClient, new Integer(-1));
            }
            if (nAliveParticipants == 1) {
                identifiedClient.getPlayingStatus().setPositionInGame(2);
                identifiedClient.getPlayingStatus().setPointsInGame(0);
                this.pointsPerParticipant.put(identifiedClient, new Integer(0));
            }
        }
        if (this.participants.size() == 4) {
            // Es tracta d'una partida de 4 jugadors
            if (nAliveParticipants == 3) {
                identifiedClient.getPlayingStatus().setPositionInGame(4);
                identifiedClient.getPlayingStatus().setPointsInGame(-2);
                this.pointsPerParticipant.put(identifiedClient, new Integer(-2));
            }
            if (nAliveParticipants == 2) {
                identifiedClient.getPlayingStatus().setPositionInGame(3);
                identifiedClient.getPlayingStatus().setPointsInGame(-1);
                this.pointsPerParticipant.put(identifiedClient, new Integer(-1));
            }
            if (nAliveParticipants == 1) {
                identifiedClient.getPlayingStatus().setPositionInGame(2);
                identifiedClient.getPlayingStatus().setPointsInGame(1);
                this.pointsPerParticipant.put(identifiedClient, new Integer(1));
            }
        }

        return nAliveParticipants;
    }

    public String generatePreparationMessage(boolean doNext) {
        // Preparem el missatge que avança la nova partida
        String message = null;
        switch (this.roundType) {
            case 1:
                message = Playroom.START_MESSAGE_2X;
                break;
            case 2:
                message = Playroom.START_MESSAGE_4X;
                break;
            case 3:
                message = this.associatedTournament.generatePreparationMessage(doNext);
                break;
            default:
                message = "";
                break;
        }

        return message;
    }
}