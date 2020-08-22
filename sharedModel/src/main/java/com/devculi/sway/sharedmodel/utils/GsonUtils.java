package com.devculi.sway.sharedmodel.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDateTime;
import java.time.ZoneId;

public final class GsonUtils {

  public static final Gson gson =
      new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss.SSS").create();

  public static String toJson(Object object) {
    return gson.toJson(object);
  }

  public static void main(String[] args) {
    LocalDateTime localDateTime = LocalDateTime.now();
    System.out.println(
        GsonUtils.toJson(localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
  }
}
