package com.devculi.sway.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

  public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
      Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
  public static String Empty = "";
  static CharsetEncoder asciiEncoder = StandardCharsets.US_ASCII.newEncoder();
  public static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
  public static final Pattern WHITESPACE = Pattern.compile("\\s+");

  public static String putArrayStringIntoParameter(String input) {

    if (isNullOrEmpty(input)) return "";

    String output = input.substring(0, input.length() - 1).replaceAll(",", "','");

    return "('" + output + "')";
  }

  public static String getComputerName() {
    try {
      InetAddress addr;
      addr = InetAddress.getLocalHost();
      return addr.getHostName();
    } catch (Throwable ex) {
      try {
        Map<String, String> env = System.getenv();
        if (env.containsKey("COMPUTERNAME")) {
          return env.get("COMPUTERNAME");
        }
        return env.getOrDefault("HOSTNAME", "Unknown");
      } catch (Exception e) {
        return "Unknown";
      }
    }
  }

  public static boolean equalsIgnoreCase(String input, String input1) {
    if (input == null && input1 == null) return true;

    if (input == null && input1 != null) return false;

    if (input != null && input1 == null) return false;

    return input.equalsIgnoreCase(input1);
  }

  public static boolean isNullOrEmpty(String input) {

    return input == null || input.trim().isEmpty();
  }

  public static boolean isPureAscii(String v) {
    return asciiEncoder.canEncode(v);
  }

  public static boolean isNumeric(String s) {
    if (isNullOrEmpty(s)) return false;

    return s.matches("[-+]?\\d*\\.?\\d+");
  }

  public static boolean isDigit(String s) {
    if (isNullOrEmpty(s)) return false;

    return s.matches("\\d+");
  }

  public static String consolidate(String s) {
    if (isNullOrEmpty(s)) return Empty;
    else {
      s = s.trim();
      return s;
    }
  }

  public static String toLiteral(String str) {
    if (str == null || str.isEmpty() || str.trim().isEmpty()) {
      return "''";
    } else {
      return "'" + str + "'";
    }
  }

  public static String consolidate(String s, String outValue) {
    if (isNullOrEmpty(s)) return outValue;
    else {
      s = s.trim();
      return s;
    }
  }

  public static boolean isNotContainSpecialCharacter(String s) {
    if (isNullOrEmpty(s)) return false;

    return s.matches("^[a-zA-Z0-9]*$");
  }

  public static Integer toInt(String s) throws Exception {
    if (isNullOrEmpty(s)) throw new Exception("Input is required.");

    try {
      return Integer.valueOf(s.trim());
    } catch (Exception ex) {
      throw new Exception("Input is invalid format.");
    }
  }

  public static Long toLong(String input) {
    if (isNullOrEmpty(input) || !isNumeric(input)) return null;
    return Long.parseLong(input);
  }

  public static int intFromString(String input) {
    if (isNullOrEmpty(input) || !isNumberic(input)) return 0;
    return Integer.parseInt(input);
  }

  public static String currencyFormat(String input) {
    double myNum = Double.parseDouble(input);
    NumberFormat nf = NumberFormat.getCurrencyInstance();
    try {
      return nf.format(myNum).replace("$", "");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return input;
  }

  public static String convertFromFloatingPointToInt(String disbursementAmount) {

    BigDecimal bd = new BigDecimal(disbursementAmount);
    bd.setScale(0, BigDecimal.ROUND_HALF_UP);
    return bd.stripTrailingZeros().toPlainString();
  }

  public static boolean validLength(String field, int maxLength) {
    return isNullOrEmpty(field) || field.length() <= maxLength;
  }

  public static String convertFromFloatingPoint(String disbursementAmount, int scale) {

    BigDecimal bd = new BigDecimal(disbursementAmount);
    bd.setScale(scale, BigDecimal.ROUND_HALF_UP);
    return bd.stripTrailingZeros().toPlainString();
  }

  public static String getStackTrace(Throwable t) {
    StringWriter sw = new StringWriter();
    t.printStackTrace(new PrintWriter(sw));
    return sw.toString();
  }

  public static BigDecimal toBigDecimal(String input) {
    return toBigDecimal(input, 0, BigDecimal.ROUND_HALF_UP);
  }

  public static BigDecimal toBigDecimal(String input, int roundingMode) {
    return toBigDecimal(input, 0, roundingMode);
  }

  public static BigDecimal toBigDecimal(String input, int scale, int roundingMode) {
    BigDecimal output = new BigDecimal(input);
    output.setScale(scale, roundingMode);
    return output;
  }

  public static String toCurrency(String amount) {
    return String.format("%,.0f", Double.valueOf(amount));
  }

  public static String nullToEmpty(Object input) {
    return (input == null ? "" : ("null".equals(input) ? "" : input.toString()));
  }

  public static boolean isNumberic(String sNumber) {
    if (sNumber == null || "".equals(sNumber)) {
      return false;
    }
    char ch_max = (char) 0x39;
    char ch_min = (char) 0x30;

    for (int i = 0; i < sNumber.length(); i++) {
      char ch = sNumber.charAt(i);
      if ((ch < ch_min) || (ch > ch_max)) {
        return false;
      }
    }
    return true;
  }

  public static boolean validateEmail(String emailStr) {
    if (isNullOrEmpty(emailStr)) return false;
    Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
    return matcher.find();
  }

  public static boolean checkMobilePhoneNumber(String number) {
    if (number == null) return false;

    boolean result = false;

    Pattern pattern = Pattern.compile("^[0-9]*$");
    Matcher matcher = pattern.matcher(number);

    if (matcher.matches() && (number.length() == 10 || number.length() == 11)) {
      number = number.substring(0, 2);
      if (number.equals("01") || number.equals("02") || number.equals("08") || number.equals("09"))
        result = true;
    }

    return result;
  }

  public static boolean checkMobilePhoneNumberNew(String number) {
    if (isNullOrEmpty(number)) return false;

    Pattern pattern = Pattern.compile("^[0-9]*$");
    Matcher matcher = pattern.matcher(number);

    if (matcher.matches() && (number.length() == 10)) {
      return "09".equals(number.substring(0, 2))
          || Arrays.asList(
                  new String[] {
                    "032", "033", "034", "035", "036", "037", "038", "039", "052", "056", "058",
                    "059", "070", "076", "077", "078", "079", "081", "082", "083", "084", "085",
                    "086", "088", "089"
                  })
              .contains(number.substring(0, 3));
    }

    return false;
  }

  public static String generateMcCustomCode(String md5) {
    try {
      java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
      byte[] array = md.digest(md5.getBytes());
      StringBuilder sb = new StringBuilder();
      for (byte b : array) {
        sb.append(Integer.toHexString((b & 0xFF) | 0x100), 1, 3);
      }
      return sb.toString();
    } catch (java.security.NoSuchAlgorithmException ignored) {
    }
    return null;
  }

  public static String convertToLatin(String str) {
    str = str.trim();
    str = str.replaceAll("[àáạảãâầấậẩẫăằắặẳẵ]", "a");
    str = str.replaceAll("[èéẹẻẽêềếệểễ]", "e");
    str = str.replaceAll("[ìíịỉĩ]", "i");
    str = str.replaceAll("[òóọỏõôồốộổỗơờớợởỡ]", "o");
    str = str.replaceAll("[ùúụủũưừứựửữ]", "u");
    str = str.replaceAll("[ỳýỵỷỹ]", "y");
    str = str.replaceAll("đ", "d");

    str = str.replaceAll("[ÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴ]", "A");
    str = str.replaceAll("[ÈÉẸẺẼÊỀẾỆỂỄ]", "E");
    str = str.replaceAll("[ÌÍỊỈĨ]", "I");
    str = str.replaceAll("[ÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠ]", "O");
    str = str.replaceAll("[ÙÚỤỦŨƯỪỨỰỬỮ]", "U");
    str = str.replaceAll("[ỲÝỴỶỸ]", "Y");
    str = str.replaceAll("Đ", "D");
    return str;
  }

  public static String currentTimeInFormat(String pattern) {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
    LocalDateTime now = LocalDateTime.now();
    return (dtf.format(now));
  }

  public static String makeSlug(String input) {
    input = convertToLatin(input);
    String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
    String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
    String slug = NONLATIN.matcher(normalized).replaceAll("");
    slug = slug.toLowerCase(Locale.ENGLISH) + "-" + currentTimeInFormat("yyyyMMdd-hhmmss");
    return slug;
  }

  public static String makeSlug(String input, String identifier, boolean isConcatAtFirst) {
    input = convertToLatin(input);
    String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
    String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
    String slug = NONLATIN.matcher(normalized).replaceAll("");
    if (isConcatAtFirst) {
      return identifier + "-" + slug.toLowerCase(Locale.ENGLISH);
    }
    return slug.toLowerCase(Locale.ENGLISH) + "-" + identifier;
  }

  public static void main(String[] args) {
    System.out.println(makeSlug("Học ăn cơm chó để làm tỷ phú"));

    String input = "Học ăn cơm chó để làm tỷ phú";
    System.out.println("Input: " + input);
    String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
    System.out.println("Normalized: " + normalized);
    String accentRemoved = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    System.out.println("Result: " + accentRemoved);

    String sample = makeSlug("Test kiểm     tra trình độ TOEIC Reading, Listening", "1", false);
    System.out.println(sample);
  }
}
