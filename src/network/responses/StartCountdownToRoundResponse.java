package network.responses;

import java.io.Serializable;

/**
 * Classe retornada pel servidor per iniciar la compte enrere
 * @author Team Troner (grupC3)
 * @version v_final
 */

public class StartCountdownToRoundResponse extends Response implements Serializable {
    private String message;
    private boolean applicablePenalty;
    private String nameToLeave;

    public StartCountdownToRoundResponse(boolean successful, String message, boolean applicablePenalty, String nameToLeave) {
        super(successful);
        this.message = message;
        this.applicablePenalty = applicablePenalty;
        this.nameToLeave = nameToLeave;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isApplicablePenalty() {
        return applicablePenalty;
    }

    public void setApplicablePenalty(boolean applicablePenalty) {
        this.applicablePenalty = applicablePenalty;
    }

    public String getNameToLeave() {
        return nameToLeave;
    }

    public void setNameToLeave(String nameToLeave) {
        this.nameToLeave = nameToLeave;
    }
}