package com.gcmassari.mastermind.data;

import java.util.List;
import java.util.Map;

import com.gcmassari.mastermind.model.Round;
import com.gcmassari.mastermind.model.Sequence;

public interface DataService {
	public Sequence getSecretSequence(String sessionId);

	public List<Round> getHistory(String sessionId);

	public List<Round> getHistoryAfterMove(Sequence move, String sessionId);

	public String getSessionIdForNewMatch();
	
	public boolean removeSession(String sessionId);

	public boolean isRegisteredPlay(String sessionId);
	
	public Map<String, String> getSessions();
}
