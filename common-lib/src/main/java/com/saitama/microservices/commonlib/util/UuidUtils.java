package com.saitama.microservices.commonlib.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.UUID;

/**
 * Implementation is from: 
 * https://github.com/eugenp/tutorials/blob/master/core-java-modules/core-java-uuid/src/main/java/com/baeldung/uuid/UUIDGenerator.java
 */
public final class UuidUtils {

    private UuidUtils() {}
    
    
	/**
	 * Type 3 UUID Generation
	 * @throws UnsupportedEncodingException 
	 */
    public static UUID generateType3UUID(String namespace, String name) throws UnsupportedEncodingException {
    	byte[] nameSpaceBytes = bytesFromUUID(namespace);
    	byte[] nameBytes = name.getBytes("UTF-8");
    	byte[] result = joinBytes(nameSpaceBytes, nameBytes);

    	return UUID.nameUUIDFromBytes(result);
    }

    /**
     * Type 4 UUID Generation
     */
    public static UUID generateType4UUID() {
        return UUID.randomUUID();
    }

    /**
     * Type 5 UUID Generation
     */
    public static UUID generateType5UUID(String namespace, String name) {

        final byte[] nameSpaceBytes = bytesFromUUID(namespace);
        final byte[] nameBytes = name.getBytes(StandardCharsets.UTF_8);
        final byte[] result = joinBytes(nameSpaceBytes, nameBytes);

        return type5UUIDFromBytes(result);
    }

    public static UUID type5UUIDFromBytes(byte[] name) {
        final MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException exception) {
            throw new InternalError("SHA-1 not supported", exception);
        }
        final byte[] bytes = Arrays.copyOfRange(md.digest(name), 0, 16);
        bytes[6] &= 0x0f; /* clear version        */
        bytes[6] |= 0x50; /* set to version 5     */
        bytes[8] &= 0x3f; /* clear variant        */
        bytes[8] |= 0x80; /* set to IETF variant  */
        return constructType5UUID(bytes);
    }

    private static UUID constructType5UUID(byte[] data) {
        long msb = 0;
        long lsb = 0;
        assert data.length == 16 : "data must be 16 bytes in length";

        for (int i = 0; i < 8; i++) {msb = (msb << 8) | (data[i] & 0xff);}

        for (int i = 8; i < 16; i++) {lsb = (lsb << 8) | (data[i] & 0xff);}
        return new UUID(msb, lsb);
    }

    private static byte[] bytesFromUUID(String uuidHexString) {
        final String normalizedUUIDHexString = uuidHexString.replace("-", "");

        assert normalizedUUIDHexString.length() == 32;
        
        final byte[] bytes = new byte[16];
        for (int i = 0; i < 16; i++) {
            final byte b = hexToByte(normalizedUUIDHexString.substring(i * 2, i * 2 + 2));
            bytes[i] = b;
        }
        return bytes;
    }

    public static byte hexToByte(String hexString) {
        final int firstDigit = Character.digit(hexString.charAt(0), 16);
        final int secondDigit = Character.digit(hexString.charAt(1), 16);
        return (byte) ((firstDigit << 4) + secondDigit);
    }

    public static byte[] joinBytes(byte[] byteArray1, byte[] byteArray2) {
        final int finalLength = byteArray1.length + byteArray2.length;
        final byte[] result = new byte[finalLength];

        System.arraycopy(byteArray1, 0, result, 0, byteArray1.length);
        System.arraycopy(byteArray2, 0, result, byteArray1.length, byteArray2.length);
        return result;
    }

    public static UUID generateType5UUID(String name) {

        try {

            final byte[] bytes = name.getBytes(StandardCharsets.UTF_8);
            final MessageDigest md = MessageDigest.getInstance("SHA-1");

            final byte[] hash = md.digest(bytes);

            long msb = getLeastAndMostSignificantBitsVersion5(hash, 0);
            long lsb = getLeastAndMostSignificantBitsVersion5(hash, 8);
            // Set the version field
            msb &= ~(0xfL << 12);
            msb |= 5L << 12;
            // Set the variant field to 2
            lsb &= ~(0x3L << 62);
            lsb |= 2L << 62;
            return new UUID(msb, lsb);

        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }
    }

    private static long getLeastAndMostSignificantBitsVersion5(final byte[] src, final int offset) {
        long ans = 0;
        for (int i = offset + 7; i >= offset; i -= 1) {
            ans <<= 8;
            ans |= src[i] & 0xffL;
        }
        return ans;
    }
}