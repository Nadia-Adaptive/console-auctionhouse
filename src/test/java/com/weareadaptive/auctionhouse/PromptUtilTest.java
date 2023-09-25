package com.weareadaptive.auctionhouse;

import com.weareadaptive.auctionhouse.console.MenuContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static com.weareadaptive.auctionhouse.utils.PromptUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PromptUtilTest {
    // TODO: Refactor scanner + context creation into before each
    @Test
    @DisplayName("getIntegerInput should parse user data into int")
    public void shouldParseUserDataIntoInt() {
        Scanner scanner = new Scanner("1");
        MenuContext context = new MenuContext(null, scanner, System.out);
        var userInt = getIntegerInput(context, "Text");
        assertEquals(userInt, 1);
    }

    @Test
    @DisplayName("getIntegerInput should run recursively until valid input is given")
    public void shouldRunRecursivelyUntilValid() {
        Scanner scanner = new Scanner("t\n e\n st\n 1");
        MenuContext context = new MenuContext(null, scanner, System.out);

        final var userInt = getIntegerInput(context, "Text");
        assertEquals(1, userInt);
    }

    @Test
    @DisplayName("getStringOrEmptyInput should return an empty optional when cancelled.")
    public void shouldReturnVoidWhenCancelled() {
        Scanner scanner = new Scanner("q");
        MenuContext context = new MenuContext(null, scanner, System.out);

        var userInput = getStringOrEmptyInput(context, "Text");
        assertTrue(userInput.isEmpty());
    }

    @Test
    @DisplayName("getDoubleInput should parse user data into int")
    public void shouldParseUserInputIntoDouble() {
        Scanner scanner = new Scanner("0.1");
        MenuContext context = new MenuContext(null, scanner, System.out);

        final var userDouble = getDoubleInput(context, "Text");
        assertEquals(0.1d, userDouble);
    }

    @Test
    @DisplayName("getDoubleInput should return false when user inputs q instead of a double")
    public void shouldReturnFalseWhenUserInputsQInsteadOfDouble() {
        Scanner scanner = new Scanner("q");
        MenuContext context = new MenuContext(null, scanner, System.out);

        final var userDouble = getDoubleInput(context, "Text");
        assertTrue(hasUserTerminatedOperation(userDouble));
    }

    @Test
    @DisplayName("getIntegerInput should return false when user inputs q instead of an int")
    public void shouldReturnFalseWhenUserInputsQInsteadOfInt() {
        Scanner scanner = new Scanner("q");
        MenuContext context = new MenuContext(null, scanner, System.out);

        final var userInt = getIntegerInput(context, "Text");
        assertTrue(hasUserTerminatedOperation(userInt));
    }

    @Test
    @DisplayName("getIntegerInput should loop if negative values are entered")
    public void shouldLoopIfNegativeIntValuesEntered() {
        Scanner scanner = new Scanner("-10\n-1\n-9\n1");
        MenuContext context = new MenuContext(null, scanner, System.out);

        final var userInt = getIntegerInput(context, "Text");
        assertEquals(1, userInt);
    }
}
