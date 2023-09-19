package com.weareadaptive.auctionhouse.utils;

import com.weareadaptive.auctionhouse.console.MenuContext;

import java.util.Optional;

public class PromptUtil {
    public  static final String invalidInputMessage = "Invalid input. Please try again.";
    public  static final String terminatedOperationText = "Operation cancelled. Returning to menu.";
    public static final String cancelOperationText = "Press Q to terminate this operation.";
    public static int getIntegerInput(final MenuContext context, final String prompt) {
        do {
            try {
                return Integer.parseInt(getUserInput(context, prompt).trim());
            } catch (NumberFormatException e) {
                context.getOut().println("Invalid int. Please try again.");
            }
        } while (true);
    }

    private static String getUserInput(final MenuContext context, final String prompt) {
        var out = context.getOut();
        var scanner = context.getScanner();
        out.println(prompt);

        do {
            var input = scanner.nextLine();
            if (!StringUtil.isNullOrEmpty(input)) {
                return input;
            }
        }
        while (true);
    }

    public static String getStringInput(final MenuContext context, final String s) {
        var input = getUserInput(context, s);
        return input;
    }

    public static Optional<String> getStringOrEmptyInput(final MenuContext context, final String prompt) {
        var input = getStringInput(context, prompt + "\nIf you wish to leave this field as is, please input Q.");
        if (input.toLowerCase().equals("q")) {
            return Optional.empty();
        }
        return Optional.of(input);
    }
}
