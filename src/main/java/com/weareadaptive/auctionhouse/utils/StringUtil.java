package com.weareadaptive.auctionhouse.utils;

import com.weareadaptive.auctionhouse.model.AccessStatus;
import com.weareadaptive.auctionhouse.model.User;

public class StringUtil {
    private StringUtil() {
    }

    public static boolean isNullOrEmpty(String theString) {
        return theString == null || theString.isBlank();
    }

    public static String userToString(final User user) {
        return "Username: %s, First name: %s, Last name: %s, Organisation: %s, Has access: %s%n".formatted(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getOrganisation(),
                user.getAccessStatus() == AccessStatus.ALLOWED ? "Yes" : "No");
    }
}
