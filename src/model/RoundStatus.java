package model;

/**
 * classe encarregada de controlar l'estat de la partida
 * @author Team Troner (grupC3)
 * @version v_final
 */
public class RoundStatus {
    private int[][] positionsMap;
    private int[][] positions;
    private int[] directions;

    /**
     * Constructor RoundStatus
     * @param round
     */
    public RoundStatus(Round round) {
        positionsMap = new int[(round.getPositionsMap()).length][(round.getPositionsMap()).length];
        for (int i = 0; i < positionsMap.length; i++) {
            for (int j = 0; j < positionsMap.length; j++) {
                positionsMap[i][j] = (round.getPositionsMap())[i][j];
            }
        }
        positions = new int[round.getPositions().values().size()][2];
        System.out.println("el round tiene de positions size" + positions.length);
        for (IdentifiedClient identifiedClient : round.getParticipants()) {
            System.out.println("es vol entrar en" + (identifiedClient.getPlayingStatus().getIndexInGame() - 1));
            System.out.println("round.computeParticipants()" + round.getParticipants().size());
            positions[identifiedClient.getPlayingStatus().getIndexInGame() - 1] = new int[2];
            positions[identifiedClient.getPlayingStatus().getIndexInGame() - 1][0] = (round.getPositions().get(identifiedClient))[0];
            positions[identifiedClient.getPlayingStatus().getIndexInGame() - 1][1] = (round.getPositions().get(identifiedClient))[1];
        }
        directions = new int[round.getDirections().values().size()];
        for (IdentifiedClient identifiedClient : round.getParticipants()) {
            directions[identifiedClient.getPlayingStatus().getIndexInGame() - 1] = ((Integer)(round.getDirections().get(identifiedClient))).intValue();
        }
    } // tancament constructor

    /**
     * Mètode per obtenir les posicions del mapa
     * @return array d'enter dos dimensions amb les posicions del mapa
     */
    public int[][] getPositionsMap() {
        return positionsMap;
    } // tancament mètode

    /**
     * Mètode per obtenir les posicions del jugador
     * @return array d'enter dos dimensions amb les posicions del jugador
     */
    public int[][] getPositions() {
        return positions;
    } // tancament mètode

    /**
     * Mètode per obtenir les direccions del jugador
     * @return array d'enter amb les direccions de la moto
     */
    public int[] getDirections() {
        return directions;
    } // tancament mètode
} // tancament classe