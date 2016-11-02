package com.gcmassari.mastermind.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.gcmassari.mastermind.model.Result;
import com.gcmassari.mastermind.model.Round;
import com.gcmassari.mastermind.model.Sequence;

@Service // TODO or declare as more general @Component, of as @Repository??
public class DataServiceMock implements DataService {
	// TODO Implement interface 
	// add logging private static final Log LOG = new Log() {
		

	private static Map<String, List<Round>> histories = new HashMap<String, List<Round>>();//ArrayList<Round>();
	private static Map<String, Sequence> secretSequences = new HashMap<String, Sequence>();
	
	public List<Round> getHistory(String sessionId) {
		if (sessionId ==null) {
			// TODO Log.warn(), remove sysout
			System.out.println("WARNING: .getHistory(): move or seesionId invalid!");
			return null;
		}
		// TODO check if sessId is not registered / invalid
		return histories.get(sessionId);
	}
	
	public List<Round> getHistoryAfterMove(Sequence move, String sessionId) {
		if ((move != null) && (sessionId !=null)) {
			// TODO check if move & sessionId are Valid()!
				
			Sequence secretSequence = getSecretSequence(sessionId);
			Result res = secretSequence.compareTo(move);
			Round latestRound = new Round(move, res);
			List<Round> history = getHistory(sessionId);
			history.add(latestRound);
			return history;
		} 
		// if move or sessionId is invalid
		// TODO Log.warn(), remove sysout
		System.out.println("WARNING: .getHistory(): move or seesionId invalid!");
		return null;
	}

	public Sequence getSecretSequence(String sessionId) {
		return secretSequences.get(sessionId);
	}

	@Override
	public String getSessionIdForNewMatch() {
		String newSessionId;
		do {
			newSessionId = createNewSessionId();
		} while (secretSequences.containsKey(newSessionId));
		
		Sequence newSecretSequence = Sequence.random();
		secretSequences.put(newSessionId, newSecretSequence);
		histories.put(newSessionId, new ArrayList<Round>());
		return newSessionId;
	}

	@Override
	public boolean isRegisteredPlay(String sessionId) {
		return secretSequences.containsKey(sessionId);
	}

	@Override
	public Map<String, String> getSessions() {
		Map<String, String> res = new HashMap<String, String>();
		;
		for (Entry<String, Sequence> sessionAndSequence : secretSequences.entrySet()) {
			res.put(sessionAndSequence.getKey(), sessionAndSequence.getValue().toString());
		}
		return res;
	}
	
	private static String createNewSessionId() {
		return Long.toString(System.currentTimeMillis(),16);
		
	}

	@Override
	public boolean removeSession(String sessionId) {
		Sequence seqRemoved = secretSequences.remove(sessionId);
		List<Round> histRemoved = histories.remove(sessionId);

		// returns true if rmeoval was ok
		return (seqRemoved!= null && histRemoved != null);
	}

}
