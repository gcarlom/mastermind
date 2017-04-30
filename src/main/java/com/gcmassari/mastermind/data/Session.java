package com.gcmassari.mastermind.data;

import java.nio.ByteBuffer;
import java.util.zip.CRC32;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.codec.binary.Base64;

public class Session {
	String sessionId;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

    public boolean isValid() {
        // TODO GC to remove //System.out.println("--> sessionId="+ sessionId);
        if (sessionId== null || sessionId.length() < 18 ) {
            return false;
        }

        String[] s = sessionId.split("@");

        // TODO GC to remove //System.out.println("--> s[0]="+ s[0] + " (" + s[0].length());
        // TODO GC to remove //System.out.println("--> s[1]="+ s[1] + " (" + s[1].length());
        if (s.length != 2 || s[0].length() < 16 || s[1].length() < 12) {
            return false;
        }
        byte[] randPart = Base64.decodeBase64(s[0]);
        CRC32 calcCrc = new CRC32();
        calcCrc.update(randPart);
        long calcCheckSum = calcCrc.getValue();
        // TODO GC to remove //System.out.println("--> isValid(): calc. CRC =" + calcCheckSum);

        byte[] txCrcAsBytes = Base64.decodeBase64(s[1]);

        ByteBuffer buff = ByteBuffer.allocate(Long.SIZE).put(txCrcAsBytes);
        buff.flip();
        long txCrc = buff.getLong();

        // TODO GC to remove // System.out.println("--> isValid(): (converted) transm. CRC =" + txCrc);
        return (calcCheckSum == txCrc);
    }

    // TODO GC: rewrite using a real secure algorithm (this is just for demo)
    public static Session createNew() {
        byte[] randPart = RandomUtils.nextBytes(12);
        CRC32 crc = new CRC32();
        crc.update(randPart);
        long checkSum = crc.getValue();
        String rand = Base64.encodeBase64String(randPart);
        // TODO GC to remove // System.out.println("--> createNew():  rand=" + "'" + rand + "'");
        byte[] checkBytes = ByteBuffer.allocate(Long.SIZE / Byte.SIZE).putLong(checkSum).array();
        String checkSumAsBase64 = Base64.encodeBase64String(checkBytes);
        // TODO GC to remove // System.out.println("--> createNew():  checkSum=" + checkSum + ",  checkSumAsBase64=" + "'" + checkSumAsBase64 + "'");

        Session res = new Session();
        res.setSessionId(rand + "@" + checkSumAsBase64);
        return res;
    }

}