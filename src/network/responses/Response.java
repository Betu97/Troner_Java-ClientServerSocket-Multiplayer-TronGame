package network.responses;

import java.io.Serializable;

public abstract class Response implements Serializable {
    private boolean successful;

    public Response(boolean successful) {
        this.successful = successful;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }
}