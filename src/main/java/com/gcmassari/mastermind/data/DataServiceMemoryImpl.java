package com.gcmassari.mastermind.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

@Service // TODO GC: or declare as more general @Component, of as @Repository??
public class DataServiceMemoryImpl implements DataService {
	// TODO GC: add logging private static final Log LOG = new Log() {

	private static Map<String, History> histories = new ConcurrentHashMap<String, History>();
	private static Map<String, Sequence> secretSequences = new ConcurrentHashMap<String, Sequence>();
	private static Map<String, Date> sessionTimestamps = new ConcurrentHashMap<String, Date>();

	@Override
    public History getHistory(String sessionId) {
		if (sessionId == null) {
			// TODO GC: Log.warn(), remove sysout and log this
			System.out.println("WARNING: .getHistory(): move or seesionId invalid!");
			return null;
		}
		// TODO GC: check if sessId is not registered / invalid
		return histories.get(sessionId);
	}

	@Override
    public History getHistoryAfterMove(Sequence move, String sessionId) {
		if ((move != null) && (sessionId != null)) {
			// TODO GC: check if move & sessionId are Valid()!

			Sequence secretSequence = getSecretSequence(sessionId);
			Result res = secretSequence.compareTo(move);
			Round latestRound = new Round(move, res);
			History history = getHistory(sessionId);
			history.add(latestRound);
			return history;
		}
		// if move or sessionId is invalid
		// TODO GC: Log.warn(), remove sysout
		System.out.println("WARNING: .getHistory(): move or sessionId invalid!");
		return null;
	}

	@Override
    public Sequence getSecretSequence(String sessionId) {
		return secretSequences.get(sessionId);
	}

	@Override
	public synchronized String getSessionIdForNewMatch() {
	    if (moreSessionsThan(Constants.MAX_NUMBER_OF_GAMES)) {
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

    @Override
	public boolean isRegisteredPlay(String sessionId) {
		if (sessionId == null) {
			return false;
		}
		return secretSequences.containsKey(sessionId);
	}

	@Override
	public Map<String, String> getSessionsIdAndSecretSequences() {
		Map<String, String> res = new HashMap<String, String>();
		;
		for (Entry<String, Sequence> sessionAndSequence : secretSequences.entrySet()) {
			res.put(sessionAndSequence.getKey(), sessionAndSequence.getValue().toString());
		}
		return res;
	}

	private static String createNewSessionId() {
	    // TODO GC: rewrite this method by introducing some element of randomness + checksum
		return Long.toString(System.currentTimeMillis(), 16);

	}

	/**
	 * Remove any reference to the passed sessionId
	 * <br/>
	 * In practice: remove the associated <li>secret sequence</li> and <li>history </li>from storage)
	 */
	@Override
	public synchronized boolean removeSession(String sessionId) {
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

    @Override
    public synchronized int removeOldestGameSessions(int noOfSessions) {
        List<String> oldestSessions = getOldestSession(noOfSessions);
        // TODO GC: remove sysout
        System.out.println("--> ************ About removing sessions:"+ oldestSessions);
        int noOfSessionToRemove = oldestSessions.size();
        for (String sessionId : oldestSessions) {
            removeSession(sessionId);
        }
        return noOfSessionToRemove;
    }

    @Override
    public synchronized int removeGameSessionsOlderThan(Date givenDate) {
        List<String> sessionsToRemove = getSessionsOlderThan(givenDate);
        // TODO GC: remove sysout
        // System.out.println("--> ************ About removing sessions:"+ sessionsToRemove);
        int noOfSessionToRemove = sessionsToRemove.size();
        for (String sessionId : sessionsToRemove) {
            removeSession(sessionId);
        }
        return noOfSessionToRemove;
    }

    private long getActiveSessionNumber() {
        return getSessionsIdAndSecretSequences().keySet().size();
    }

    private boolean moreSessionsThan(long noOfSessions) {
        return (getActiveSessionNumber() > noOfSessions);
    }

    private List<String> getOldestSession(int noOfSession) {
        List<Entry<String, Date>> sessionTimestampsList = new ArrayList<Entry<String, Date>>(sessionTimestamps.entrySet());
        // TODO GC: remove commented sysout
        // System.out.println("--> 1. sessTSlist="+ sessionTimestampsList);
        Collections.sort(sessionTimestampsList, new Comparator<Map.Entry<String, Date>>() {
            @Override
            public int compare(Entry<String, Date> o1, Entry<String, Date> o2) {
                if (o1.getValue().before(o2.getValue())) {
                    return -1;
                }
                if (o1.getValue().after(o2.getValue())) {
                    return 1;
                }
                return 0;
            }
        });
        // TODO GC: remove commented sysout
        // System.out.printf("--> *** sessTSList.size=%d\n", sessionTimestampsList.size());
        int listLength = Math.min(noOfSession, sessionTimestampsList.size());

        List<String> resultList = new ArrayList<String>();
        for (int i = 0; i < listLength; i++) {
            resultList.add(sessionTimestampsList.get(i).getKey());
        }
        // TODO GC: remove commented sysout
        // System.out.println("--> *** resList="+ resultList);
        return resultList;
    }

    private List<String> getSessionsOlderThan(Date givenDate) {
        if (givenDate == null) {
            throw new IllegalArgumentException("Class " + this.getClass().getSimpleName() + ": parameter 'givenDate' may not be null");
        }

        List<Entry<String, Date>> sessionTimestampsList = new ArrayList<Entry<String, Date>>(sessionTimestamps.entrySet());

        List<String> resultList = new ArrayList<String>();
        for (int i = 0; i < sessionTimestampsList.size(); i++) {
            Date ts = sessionTimestampsList.get(i).getValue();
            if (ts == null || ts.before(givenDate)) {
                resultList.add(sessionTimestampsList.get(i).getKey());
            }
        }
        // TODO GC: remove commented sysout
        // System.out.println("--> *** resList="+ resultList);
        return resultList;
    }

}
