package com.weareadaptive.auctionhouse.console.auction;

import com.weareadaptive.auctionhouse.console.MenuContext;
import com.weareadaptive.auctionhouse.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static com.weareadaptive.auctionhouse.TestData.USER1;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuctionManagementMenuTest {
    public static MenuContext createAuctionContext(final String src) {
        final var auctionState = new AuctionState();
        auctionState.add(new Auction(auctionState.nextId(), USER1.getUsername(), "TESLA", 1.02d, 100));

        final AuctionManagementMenu menu = new AuctionManagementMenu();

        Scanner scanner = new Scanner(src);
        MenuContext context = new MenuContext(new ModelState(new UserState(), new OrganisationState(), auctionState), scanner, System.out);
        context.setCurrentUser(USER1);


        menu.display(context);

        return context;
    }

    @Test
    @DisplayName("auctionManagementMenu can create an auction through the menu")
    public void canCreateAuction() {
        final var context = createAuctionContext("1\nEURUSD\n1.55\n1000\n\r7");

        final var auctionState = context.getState().auctionState();
        final var auction = auctionState.get(1);

        assertEquals(2, auctionState.stream().count());
        assertEquals("EURUSD", auction.getSymbol());
        assertEquals(1.55d, auction.getMinPrice());
        assertEquals(1000, auction.getQuantity());
    }

    @Test
    @DisplayName("auctionManagementMenu can place a bid through the menu")
    public void canPlaceABid() {
        final var context = createAuctionContext("4\n0\n2\n100\n\r7");

        final var auction = context.getState().auctionState().get(0);

        assertEquals(1, auction.getBids().count());
    }

    @Test
    @DisplayName("auctionManagementMenu closes the current user's auction through the menu")
    public void canCloseAuction() {
        final var context = createAuctionContext("3\n0\nyes\n\r7");


        final var auction = context.getState().auctionState().get(0);

        assertEquals(AuctionStatus.CLOSED, auction.getStatus());
    }

    @Test
    @DisplayName("auctionManagementMenu does not close an auction if the current user does not own it")
    public void cantCloseAuction() {
        final var context = createAuctionContext("3\n0\n-1\n\r7");


        final var auction = context.getState().auctionState().get(0);

        assertEquals(AuctionStatus.CLOSED, auction.getStatus());
    }
}
