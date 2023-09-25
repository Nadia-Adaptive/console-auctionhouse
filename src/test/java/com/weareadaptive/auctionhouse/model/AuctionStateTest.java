package com.weareadaptive.auctionhouse.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.weareadaptive.auctionhouse.TestData.USER1;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuctionStateTest {
    @Test
    @DisplayName("getUserAuctions returns a list of all a user's auctions")
    public void returnAListOfAUsersAuctions() {
        AuctionState state = new AuctionState();

        state.add(new Auction(0, USER1.getUsername(), "JPYUSD", 1.00, 10));
        final var allAuctions = state.getUserAuctions(USER1.getUsername());

        assertEquals(1, allAuctions.count());
    }

}
