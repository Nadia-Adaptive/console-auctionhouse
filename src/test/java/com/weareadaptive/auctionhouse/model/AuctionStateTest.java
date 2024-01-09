package com.weareadaptive.auctionhouse.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.weareadaptive.auctionhouse.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

public class AuctionStateTest {
    private static AuctionState state;

    @BeforeEach
    public void beforeEach() {
        state = new AuctionState();
        state.add(AUCTION1);
    }

    @Test
    @DisplayName("getUserAuctions returns a list of all a user's auctions")
    public void returnAListOfAUsersAuctions() {
        final var allAuctions = state.getUserAuctions(USER4.getUsername());

        assertEquals(1, allAuctions.count());
    }

    @Test
    @DisplayName("hasAuction returns true if auction exists in state")
    public void returnsTrueIfStateHasAuction() {
        assertTrue(state.hasAuction(0));
    }

    @Test
    @DisplayName("hasAuction returns false if auction does not exist in state")
    public void returnsFalseIfStateHasAuction() {
        assertFalse(state.hasAuction(3));
    }

    @Test
    @DisplayName("getWinningBids returns all the user's winning bids across all auctions")
    public void getWinningBidsByUser() {
        final var bid01 = new Bid(USER1.getUsername(), 1.2, 10);
        final var losingBid01 = new Bid(USER2.getUsername(), 1, 10);
        final var bid02 = new Bid(USER1.getUsername(), 11, 11);

        state.add(AUCTION2);

        AUCTION1.makeBid(bid01);
        AUCTION1.makeBid(losingBid01);

        AUCTION2.makeBid(bid02);

        AUCTION1.close();
        AUCTION2.close();

        final var winningBids = state.getAllUserWinningBids(USER1.getUsername());

        assertEquals(2, winningBids.size());

        System.out.println(bid01.getStatus());

        assertEquals(bid01, winningBids.get(0));
        assertEquals(bid02, winningBids.get(1));
    }

    @Test
    @DisplayName("getLosingBids returns all the user's losing bids across all auctions")
    public void getLosingBidsByUser() {
        final var bid01 = new Bid(USER1.getUsername(), 1.2, 10);
        final var losingBid01 = new Bid(USER2.getUsername(), 1, 10);
        final var bid02 = new Bid(USER1.getUsername(), 11, 11);

        state.add(AUCTION2);

        AUCTION1.makeBid(bid01);
        AUCTION1.makeBid(losingBid01);

        AUCTION2.makeBid(bid02);

        AUCTION1.close();
        AUCTION2.close();

        final var losingBids = state.getAllUserLosingBids(USER2.getUsername());

        assertEquals(1, losingBids.size());

        assertEquals(losingBid01, losingBids.get(0));
    }
}
