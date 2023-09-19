package com.weareadaptive.auctionhouse;

import com.weareadaptive.auctionhouse.console.MenuContext;
import com.weareadaptive.auctionhouse.utils.PromptUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class PromptUtilTest {
    @Test
    @DisplayName("should parse user data into int")
    public void shouldParseUserDataIntoInt() {
        Scanner scanner = new Scanner("1");
        MenuContext context = new MenuContext(null, scanner, System.out);
        var userInt = PromptUtil.getIntegerInput(context, "Text");
        assertEquals(userInt, 1);
    }

    @Test
    @DisplayName("should run recursively until valid input is given")
    public void shouldRunRecursivelyUntilValid() {
        Scanner scanner = new Scanner("t\n e\n st\n 1");

        MenuContext context = new MenuContext(null, scanner, System.out);
        var userInt = PromptUtil.getIntegerInput(context, "Text");
        assertEquals(userInt, 1);
    }

    @Test
    @DisplayName("should return an empty optional when cancelled.")
    public void shouldReturnVoidWhenCancelled() {
        Scanner scanner = new Scanner("q");

        MenuContext context = new MenuContext(null, scanner, System.out);
        var userInput = PromptUtil.getStringOrEmptyInput(context, "Text");
        assertTrue(userInput.isEmpty());
    }
}
