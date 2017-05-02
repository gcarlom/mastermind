package com.gcmassari.mastermind.data;

import java.nio.ByteBuffer;
import java.util.zip.CRC32;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomUtils;


public class GameId {

    /**
     * @return a new GameId represented in Base64 in the url-safe format (that is containing no "+", "/" characters)
     *
     * GameId is 12 Bytes long which means its Base64 representation is 16 chars long
     */
    public static String createNew() {
        int RAND_BYTES = 4;
        byte[] randPart = RandomUtils.nextBytes(RAND_BYTES);
        ByteBuffer bytes = ByteBuffer.allocate(Long.SIZE/Byte.SIZE + RAND_BYTES);
        bytes.putLong(System.currentTimeMillis()).put(randPart);
        String id = Base64.encodeBase64URLSafeString(bytes.array());
        return id;
    }

// TODO GC: remove this method if not used
    public static String createNewWithChecksum() {
        int RAND_BYTES = 4;
        byte[] randPart = RandomUtils.nextBytes(RAND_BYTES);
        ByteBuffer bytes = ByteBuffer.allocate(Long.SIZE/Byte.SIZE + RAND_BYTES);
        bytes.putLong(System.currentTimeMillis()).put(randPart);

        CRC32 crc = new CRC32();
        crc.update(bytes.array());
        long checkSum = crc.getValue();
        byte[] dataWithCrc = bytes.putLong(checkSum).array();

        String id = Base64.encodeBase64URLSafeString(dataWithCrc);
        return id;
    }

}
