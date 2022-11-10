package network.responses;

public class SignUpResponse extends Response {
    private String grantedUsername;
    private int grantedUpKey;
    private int grantedDownKey;
    private int grantedLeftKey;
    private int grantedRightKey;
    private int failReason;

    public SignUpResponse(boolean successful, String grantedUsername, int failReason, int grantedUpKey, int grantedDownKey, int grantedLeftKey, int grantedRightKey) {
        super(successful);
        this.grantedUsername = grantedUsername;
        this.grantedUpKey = grantedUpKey;
        this.grantedDownKey = grantedDownKey;
        this.grantedLeftKey = grantedLeftKey;
        this.grantedRightKey = grantedRightKey;
        this.failReason = failReason;
    }

    public String getGrantedUsername() {
        return grantedUsername;
    }

    public void setGrantedUsername(String grantedUsername) {
        this.grantedUsername = grantedUsername;
    }

    public int getGrantedUpKey() {
        return grantedUpKey;
    }

    public void setGrantedUpKey(int grantedUpKey) {
        this.grantedUpKey = grantedUpKey;
    }

    public int getGrantedDownKey() {
        return grantedDownKey;
    }

    public void setGrantedDownKey(int grantedDownKey) {
        this.grantedDownKey = grantedDownKey;
    }

    public int getGrantedLeftKey() {
        return grantedLeftKey;
    }

    public void setGrantedLeftKey(int grantedLeftKey) {
        this.grantedLeftKey = grantedLeftKey;
    }

    public int getGrantedRightKey() {
        return grantedRightKey;
    }

    public void setGrantedRightKey(int grantedRightKey) {
        this.grantedRightKey = grantedRightKey;
    }

    public int getFailReason() {
        return failReason;
    }

    public void setFailReason(int failReason) {
        this.failReason = failReason;
    }
}