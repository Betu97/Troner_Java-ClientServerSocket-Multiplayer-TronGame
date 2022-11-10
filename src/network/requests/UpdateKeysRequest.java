package network.requests;

import java.io.Serializable;

public class UpdateKeysRequest extends Request implements Serializable {
    private int desiredUpKey;
    private int desiredDownKey;
    private int desiredLeftKey;
    private int desiredRightKey;

    public UpdateKeysRequest(int desiredUpKey, int desiredDownKey, int desiredLeftKey, int desiredRightKey) {
        this.desiredUpKey = desiredUpKey;
        this.desiredDownKey = desiredDownKey;
        this.desiredLeftKey = desiredLeftKey;
        this.desiredRightKey = desiredRightKey;
    }

    public int getDesiredUpKey() {
        return desiredUpKey;
    }

    public void setDesiredUpKey(int desiredUpKey) {
        this.desiredUpKey = desiredUpKey;
    }

    public int getDesiredDownKey() {
        return desiredDownKey;
    }

    public void setDesiredDownKey(int desiredDownKey) {
        this.desiredDownKey = desiredDownKey;
    }

    public int getDesiredLeftKey() {
        return desiredLeftKey;
    }

    public void setDesiredLeftKey(int desiredLeftKey) {
        this.desiredLeftKey = desiredLeftKey;
    }

    public int getDesiredRightKey() {
        return desiredRightKey;
    }

    public void setDesiredRightKey(int desiredRightKey) {
        this.desiredRightKey = desiredRightKey;
    }
}