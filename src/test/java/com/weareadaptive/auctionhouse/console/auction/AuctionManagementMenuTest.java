package com.weareadaptive.auctionhouse.console.auction;

import com.weareadaptive.auctionhouse.console.MenuContext;
import com.weareadaptive.auctionhouse.model.AuctionState;
import com.weareadaptive.auctionhouse.model.ModelState;
import com.weareadaptive.auctionhouse.model.OrganisationState;
import com.weareadaptive.auctionhouse.model.UserState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static com.weareadaptive.auctionhouse.TestData.USER1;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuctionManagementMenuTest {
    public static MenuContext createAuctionContext(final String src) {
        final AuctionManagementMenu menu = new AuctionManagementMenu();
        Scanner scanner = new Scanner(src);
        MenuContext context = new MenuContext(new ModelState(new UserState(), new OrganisationState(), new AuctionState()), scanner, System.out);
        context.setCurrentUser(USER1);

        menu.display(context);

        return context;
    }

    @Test
    @DisplayName("can create an auction through the menu")
    public void canCreateAuction() {
        final var context = createAuctionContext("1\nEURUSD\n1.55\n1000\n\r7");

        final var auctionState = context.getState().auctionState();
        final var auction = context.getState().auctionState().get(0);
        assertAll(
                () -> assertEquals(1, auctionState.stream().count()),
                () -> assertEquals("EURUSD", auction.getSymbol()),
                () -> assertEquals(1.55d, auction.getPrice()),
                () -> assertEquals(1000, auction.getQuantity()));
    }
}
