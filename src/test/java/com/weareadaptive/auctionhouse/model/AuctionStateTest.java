package com.weareadaptive.auctionhouse.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.weareadaptive.auctionhouse.TestData.USER1;
import static org.junit.jupiter.api.Assertions.*;

public class AuctionStateTest {
    private static AuctionState state;
    @BeforeAll
    public static void beforeAll(){
        state = new AuctionState();
        state.add(new Auction(0, USER1.getUsername(), "JPYUSD", 1.00, 10));
    }
    @Test
    @DisplayName("getUserAuctions returns a list of all a user's auctions")
    public void returnAListOfAUsersAuctions() {
        final var allAuctions = state.getUserAuctions(USER1.getUsername());

        assertEquals(1, allAuctions.count());
    }

    @Test
    @DisplayName("hasAuction returns true if auction exists in state")
    public void returnsTrueIfStateHasAuction(){
        assertTrue(state.hasAuction(0));
    }

    @Test
    @DisplayName("hasAuction returns false if auction does not exist in state")
    public void returnsFalseIfStateHasAuction(){
        assertFalse(state.hasAuction(1));
    }
}
