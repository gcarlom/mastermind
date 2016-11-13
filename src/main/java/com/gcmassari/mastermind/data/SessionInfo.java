package com.gcmassari.mastermind.data;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.gcmassari.mastermind.model.Sequence;

public class SessionInfo {
    private String sessionId;
    private Date timestamp;
    private Sequence secret;
    private History history;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss.SSS");

    public SessionInfo(String sessionId, Date timestamp, Sequence secret, History history) {
        this.sessionId = sessionId;
        this.timestamp = timestamp;
        this.secret = secret;
        this.history = history;
    }

    public String getSessionId() {
        return sessionId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Sequence getSecret() {
        return secret;
    }

    public History getHistory() {
        return history;
    }

    public String getFormattedTimestamp() {
        if (timestamp != null) {
            return DATE_FORMAT.format(timestamp);
        }
        return "-";
    }

    public int getNumberOfMoves() {
        return history.getLength();
    }

}
