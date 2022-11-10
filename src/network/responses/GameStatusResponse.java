package network.responses;

public class GameStatusResponse extends Response {
    private int[][] positionsMap;
    private int[][] positions;
    private int[] directions;

    public GameStatusResponse(int[][] positionsMap, int[][] positions, int[] directions) {
        super(true);
        this.positionsMap = positionsMap;
        this.positions = positions;
        this.directions = directions;
    }

    public int[][] getPositionsMap() {
        return positionsMap;
    }

    public void setPositionsMap(int[][] positionsMap) {
        this.positionsMap = positionsMap;
    }

    public int[][] getPositions() {
        return positions;
    }

    public void setPositions(int[][] positions) {
        this.positions = positions;
    }

    public int[] getDirections() {
        return directions;
    }

    public void setDirections(int[] directions) {
        this.directions = directions;
    }
}