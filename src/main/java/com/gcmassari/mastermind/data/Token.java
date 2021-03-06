package com.gcmassari.mastermind.data;

import java.nio.ByteBuffer;
import java.util.zip.CRC32;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomUtils;

public class Token {
	String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

    public boolean isValid() {
        if (id == null || id.length() < 18 ) {
            return false;
        }

        String[] s = id.split("@");

        if (s.length != 2 || s[0].length() < 16 || s[1].length() < 12) {
            return false;
        }
        byte[] randPart = Base64.decodeBase64(s[0]);
        CRC32 calcCrc = new CRC32();
        calcCrc.update(randPart);
        long calcCheckSum = calcCrc.getValue();

        byte[] txCrcAsBytes = Base64.decodeBase64(s[1]);

        ByteBuffer buff = ByteBuffer.allocate(Long.SIZE).put(txCrcAsBytes);
        buff.flip();
        long txCrc = buff.getLong();

        return (calcCheckSum == txCrc);
    }

    // TODO GC: rewrite using a real secure algorithm (this is just for demo)
    public static Token createNew() {
        byte[] randPart = RandomUtils.nextBytes(12);
        CRC32 crc = new CRC32();
        crc.update(randPart);
        long checkSum = crc.getValue();
        String rand = Base64.encodeBase64String(randPart);
        byte[] checkBytes = ByteBuffer.allocate(Long.SIZE / Byte.SIZE).putLong(checkSum).array();
        String checkSumAsBase64 = Base64.encodeBase64String(checkBytes);

        Token res = new Token();
        res.setId(rand + "@" + checkSumAsBase64);
        return res;
    }

}