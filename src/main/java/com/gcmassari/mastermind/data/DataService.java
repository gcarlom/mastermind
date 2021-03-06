package com.gcmassari.mastermind.data;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.gcmassari.mastermind.model.Sequence;

public interface DataService {
	public Sequence getSecretSequence(String sessionId);

	public History getHistory(String sessionId);

	public History getHistoryAfterMove(Sequence move, String sessionId);

	public String startNewGame();

	public boolean isRegisteredPlay(String sessionId);

	public Map<String, String> getSessionsIdAndSecretSequences();

	public Date getSessionTimestamp(String sessionId);

    public List<SessionInfo> getSessionInfo();

    public boolean removeSession(String sessionId);

    int removeGameSessionsOlderThan(Date givenDate);

    int removeOldestGameSessions(int noOfSessions);
}
