package com.devculi.sway.utils.security;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Converter {

  public static String encode(String input) throws Exception {
    try {
      return Base64.getEncoder().encodeToString(input.getBytes(StandardCharsets.UTF_8));
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  public static String decode(String base64encodedString) throws Exception {
    try {
      // Decode
      byte[] base64decodedBytes = Base64.getDecoder().decode(base64encodedString);
      return new String(base64decodedBytes, StandardCharsets.UTF_8);
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }
}
