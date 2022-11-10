package network.responses;

import network.responses.Response;

import java.io.Serializable;

public class StopWaitingResponse extends Response implements Serializable{
    public StopWaitingResponse(boolean successful) {
        super(successful);
    }
}