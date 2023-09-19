package com.weareadaptive.auctionhouse.utils;

public class StringUtil {
  private StringUtil() {
  }

  public static boolean isNullOrEmpty(String theString) {
    return theString == null || theString.isBlank();
  }
}
