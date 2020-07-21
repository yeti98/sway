package com.devculi.sway.utils.security;

import com.devculi.sway.sharedmodel.constants.StandardConstant;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Protector {

  private static final String ALGORITHM = "AES";
  private static final int ITERATIONS = 3;

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

  public static void main(String[] args) throws Exception {

    String plainPassword = "!@#$%^&* để";
    String salt = generateSalt();
    String valueEnc = encrypt(plainPassword, salt);
    String passwordDec = decrypt(valueEnc, salt);

    System.out.println("Password plaint text : " + plainPassword);
    System.out.println("Salt value : " + salt);
    System.out.println("Encrypted value : " + valueEnc);
    System.out.println("Decrypted value : " + passwordDec);

    System.out.println(
        isMatch(
            "123456",
            "j9CLi9dDFRcED+afNZrZ+2FjK199LjnJGOhfYuQ63rfzkIbwOjScZZHdQKtQcgfUm2JB0L6WdqJa1mPWYCqsxA==",
            "hbMFuUEoDkY="));

    // System.out.println( decrypt("j9CLi9dDFRcED afNZrZ
    // 2FjK199LjnJGOhfYuQ63rfzkIbwOjScZZHdQKtQcgfUm2JB0L6WdqJa1mPWYCqsxA==", "hbMFuUEoDkY=") );
    System.out.println(
        decrypt(
            "j9CLi9dDFRcED+afNZrZ+2FjK199LjnJGOhfYuQ63rfzkIbwOjScZZHdQKtQcgfUm2JB0L6WdqJa1mPWYCqsxA==",
            "hbMFuUEoDkY="));
  }
}
