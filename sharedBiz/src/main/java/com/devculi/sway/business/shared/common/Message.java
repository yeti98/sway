package com.devculi.sway.business.shared.common;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public final class Message {

  private static final String BUNDLE_NAME = "message/messages";
  private static ResourceBundle RESOURCE_BUNDLE =
      ResourceBundle.getBundle(BUNDLE_NAME, new Locale("vi", "VN"));
  private static Locale locale = new Locale("vi", "VN");

  private Message() {}

  public static void setLocale(Locale lo) {
    locale = lo;
    RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, locale);
  }

  public static String getString(String key) {
    try {
      return RESOURCE_BUNDLE.getString(key);
    } catch (MissingResourceException e) {
      return '!' + key + '!';
    }
  }

  public static String getString(String key, Object... params) {
    try {
      return MessageFormat.format(RESOURCE_BUNDLE.getString(key), params);
    } catch (MissingResourceException e) {
      return '!' + key + '!';
    }
  }
}
