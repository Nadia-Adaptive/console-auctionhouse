package com.weareadaptive.auctionhouse.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.weareadaptive.auctionhouse.TestData.USER1;
import static org.junit.jupiter.api.Assertions.*;

public class AuctionTest {

    @Test
    @DisplayName("makeBid should create a bid for the auction")
    public void shouldCreateABid() {
        Auction auction = new Auction(0, USER1.getUsername(), "JYPUSD", 1.00d, 10);

        auction.makeBid(USER1.getUsername(), 1.2d, 5);

        final var bid = auction.getBids().findFirst();

        assertAll(
                () -> assertEquals(1, auction.getBids().count()),
                () -> assertTrue(bid.isPresent()),
                () -> assertEquals(1.2d, bid.get().offer()),
                () -> assertEquals(5, bid.get().quantity()));
    }
}
