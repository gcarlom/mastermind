package com.gcmassari.mastermind.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.gcmassari.mastermind.model.Result;
import com.gcmassari.mastermind.model.Round;
import com.gcmassari.mastermind.model.Sequence;

@Service // TODO or declare as more general @Component, of as @Repository??
public class DataServiceMock implements DataService {
	// TODO add logging private static final Log LOG = new Log() {

  // TODO test the new ConcurrentHashMap<String, String>();
	private static Map<String, History> histories = new ConcurrentHashMap<String, History>();
	private static Map<String, Sequence> secretSequences = new ConcurrentHashMap<String, Sequence>();
	private static Map<String, Date> sessionTimestamps = new ConcurrentHashMap<String, Date>();

	public History getHistory(String sessionId) {
		if (sessionId == null) {
			// TODO Log.warn(), remove sysout
			System.out.println("WARNING: .getHistory(): move or seesionId invalid!");
			return null;
		}
		// TODO check if sessId is not registered / invalid
		return histories.get(sessionId);
	}

	public History getHistoryAfterMove(Sequence move, String sessionId) {
		if ((move != null) && (sessionId != null)) {
			// TODO check if move & sessionId are Valid()!

			Sequence secretSequence = getSecretSequence(sessionId);
			Result res = secretSequence.compareTo(move);
			Round latestRound = new Round(move, res);
			History history = getHistory(sessionId);
			history.add(latestRound);
			return history;
		}
		// if move or sessionId is invalid
		// TODO Log.warn(), remove sysout
		System.out.println("WARNING: .getHistory(): move or sessionId invalid!");
		return null;
	}

	public Sequence getSecretSequence(String sessionId) {
		return secretSequences.get(sessionId);
	}

	@Override
	public String getSessionIdForNewMatch() {
	    if (tooManyActiveSessions()) {
	        return null;
	    }
	    String newSessionId;
	    do {
	        newSessionId = createNewSessionId();
	    } while (secretSequences.containsKey(newSessionId));

	    Sequence newSecretSequence = Sequence.random();
	    secretSequences.put(newSessionId, newSecretSequence);
	    histories.put(newSessionId, new History());
	    sessionTimestamps.put(newSessionId, new Date());
	    return newSessionId;
	}

	private boolean tooManyActiveSessions() {
        return (getSessions().keySet().size() > Constants.MAX_GAME_NUMBER);
    }

    @Override
	public boolean isRegisteredPlay(String sessionId) {
		if (sessionId == null) {
			return false;
		}
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
		return Long.toString(System.currentTimeMillis(), 16);

	}

	// TODO make it synchronized ?
	/**
	 * Remove any reference to the passed sessionId
	 * <br/>
	 * In practice: remove the associated <li>secret sequence</li> and <li>history </li>from storage)
	 */
	@Override
	public boolean removeSession(String sessionId) {
		Sequence seqRemoved = secretSequences.remove(sessionId);
		History listRemoved = histories.remove(sessionId);
		Date timeStamp = sessionTimestamps.remove(sessionId);

		// returns true if removal was ok
		return (seqRemoved != null && listRemoved != null && timeStamp != null);
	}

    @Override
    public Date getSessionTimestamp(String sessionId) {
        return sessionTimestamps.get(sessionId);
    }

    @Override
    public List<SessionInfo> getSessionInfo() {
        List<SessionInfo> res = new ArrayList<SessionInfo>();
        for (String sessId : sessionTimestamps.keySet()) {
            Date timestamp = sessionTimestamps.get(sessId);
            Sequence secret = secretSequences.get(sessId);
            History history = histories.get(sessId);

            res.add(new SessionInfo(sessId, timestamp, secret, history));
        }
        return res;
    }

	// TODO Add a service (cron job?) which removes all sessions older than xxx days

}
