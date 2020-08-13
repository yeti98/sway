package com.devculi.sway.utils.security;

import com.devculi.sway.sharedmodel.constants.StandardConstant;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;


public class Protector {

  private static final String ALGORITHM = "AES";
  private static final int ITERATIONS = 3;

  private static Logger logger = Logger.getLogger(Protector.class.getName());

  public static String encrypt(String value, String salt) {
    Key key = generateKey();
    Cipher cipher = null;
    try {
      cipher = Cipher.getInstance(ALGORITHM);
      cipher.init(Cipher.ENCRYPT_MODE, key);
      String valueToEncode;
      String encodedValue = value;
      for (int loopIndex = 0; loopIndex < ITERATIONS; loopIndex++) {
        valueToEncode = salt + encodedValue;
        byte[] encValue = cipher.doFinal(valueToEncode.getBytes());
        encodedValue = Base64.getEncoder().encodeToString(encValue);
      }
      return encodedValue;
    } catch (NoSuchAlgorithmException
        | NoSuchPaddingException
        | InvalidKeyException
        | BadPaddingException
        | IllegalBlockSizeException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String decrypt(String value, String salt) throws Exception {
    Key key = generateKey();
    Cipher cipher = Cipher.getInstance(ALGORITHM);
    cipher.init(Cipher.DECRYPT_MODE, key);

    String dValue = null;
    String valueToDecrypt = value;
    for (int i = 0; i < ITERATIONS; i++) {
      // byte[] decodedValue = new BASE64Decoder().decodeBuffer(valueToDecrypt);
      byte[] decodedValue = Base64.getDecoder().decode(valueToDecrypt);
      byte[] decValue = cipher.doFinal(decodedValue);
      dValue = new String(decValue).substring(salt.length());
      valueToDecrypt = dValue;
    }
    return dValue;
  }

  private static Key generateKey() {
    return new SecretKeySpec(StandardConstant.AES_KEY_VAL, ALGORITHM);
  }

  public static String generateSalt() {
    SecureRandom random = new SecureRandom();
    // Salt generation 64 bits long
    byte[] bSalt = new byte[8];
    random.nextBytes(bSalt);
    // Digest computation
    return byteToBase64(bSalt);
  }

  public static String generatePassword(int length) {
    // ASCII range - alphanumeric (0-9, a-z, A-Z)
    final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    SecureRandom random = new SecureRandom();
    StringBuilder sb = new StringBuilder();

    // each iteration of loop choose a character randomly from the given ASCII range
    // and append it to StringBuilder instance

    for (int i = 0; i < length; i++) {
      int randomIndex = random.nextInt(chars.length());
      sb.append(chars.charAt(randomIndex));
    }

    return sb.toString();
  }
  /**
   * From a byte[] returns a base 64 representation
   *
   * @param data byte[]
   * @return String
   */
  public static String byteToBase64(byte[] data) {

    return Base64.getEncoder().encodeToString(data);
  }

  public static boolean isMatch(String inputPassword, String passwordEncrypted, String saltValue) {
    boolean result = false;
    try {
      if (inputPassword.equals(decrypt(passwordEncrypted, saltValue))) {
        result = true;
      }
    } catch (Exception ex) {
      System.out.println("Protector.isMatch().ex: " + ex.toString());
    }
    return result;
  }

  public static String getSourceIp(HttpServletRequest request) {

    String currentIP = "";

    try {
      currentIP = request.getHeader("X-FORWARDED-FOR");
      if (currentIP == null) {
        currentIP = request.getRemoteAddr();
      }
    } catch (Exception ex) {
      logger.error("getSourceIp.ex: " + ex.toString());
    }

    return currentIP;
  }

  public static void main(String[] args) throws Exception {


    String plainPassword = generatePassword(8);
    String salt = generateSalt();
    String valueEnc = encrypt(plainPassword, salt);
    String passwordDec = decrypt(valueEnc, salt);

    System.out.println("Password plaint text : " + plainPassword);
    System.out.println("Salt value : " + salt);
    System.out.println("Encrypted value : " + valueEnc);
    System.out.println("Decrypted value : " + passwordDec);

    System.out.println(
        Protector.isMatch(
            "trangnh",
            "g1DpeSINzvffUaKfHsE6v6hTTCxcsUt58Ye6W8iB+3z7KH5wgFPh0mPj4PJ6v+3gcrYc5qbgERAVJqs2TXnmak8hI4ppo80KSeRoFpNSNguRWj0+1JOhu6cWzEmj9b4ZOVrz/1tLuWRPlj3ynnoF7g==",
            "5FBpV3ewtpY="));
  }
}
