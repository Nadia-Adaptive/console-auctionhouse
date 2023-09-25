package com.weareadaptive.auctionhouse.utils;

import com.weareadaptive.auctionhouse.console.MenuContext;

public class PromptUtil {
    public static final String invalidInputMessage = "Invalid input. Please try again.";
    public static final String terminatedOperationText = "Operation cancelled. Returning to menu.";
    public static final String cancelOperationText = "Press Q to terminate this operation at any time.";
    public static final String leaveFieldBlankText = "If you wish to leave this field as is, please input Q.";

    public static final String invalidNumberText = "Invalid number. Please try again.";

    public static int getIntegerInput(final MenuContext context, final String prompt) {
        do {
            try {
                return Integer.parseInt(getStringInput(context, prompt));
            } catch (NumberFormatException e) {
                context.getOut().println(invalidNumberText);
            }
        } while (true);
    }

    public static Double getDoubleInput(final MenuContext context, final String prompt) {
        do {
            final var input = getStringInput(context, prompt);
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                context.getOut().println(invalidNumberText);
            }
        } while (true);
    }

    public static String getStringInput(final MenuContext context, final String prompt) {
        var out = context.getOut();
        var scanner = context.getScanner();
        out.println(prompt);

        do {
            var input = scanner.nextLine();
            if (!StringUtil.isNullOrEmpty(input)) {
                return input.trim();
            }
        }
        while (true);
    }

    public static String getStringOrEmptyInput(final MenuContext context, final String prompt) {
        var input = getStringInput(context, prompt + "\n"+ leaveFieldBlankText);
        if (hasUserTerminatedOperation(input)) {
            return "";
        }
        return input;
    }

    public static boolean hasUserTerminatedOperation(final String input) {
        return input.equalsIgnoreCase("q");
    }
    public static boolean hasUserTerminatedOperation(final double input) {
        return input == -1d;
    }
    public static boolean hasUserTerminatedOperation(final int input) {
        return input == -1;
    }
}
