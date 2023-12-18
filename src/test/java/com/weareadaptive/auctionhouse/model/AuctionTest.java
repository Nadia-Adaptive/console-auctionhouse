package com.weareadaptive.auctionhouse.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.weareadaptive.auctionhouse.TestData.USER1;
import static com.weareadaptive.auctionhouse.TestData.USER2;
import static org.junit.jupiter.api.Assertions.*;

public class AuctionTest {
    private static Auction auction;

    @BeforeAll
    public static void beforeAll() {
        auction = new Auction(0, USER1.getUsername(), "JPYUSD", 1.00, 10);

    }

    @Test
    @DisplayName("makeBid should create a bid for the auction")
    public void shouldCreateABid() {
        final var bid = new Bid(USER1.getUsername(), 1.2d, 5);
        auction.makeBid(bid);

        assertEquals(1, auction.getBids().count());
    }

    @Test
    @DisplayName("closeAuction closes the auction with the highest offer")
    public void auctionClosesWithHighestBid() {
        final var winningBid = new Bid(USER2.getUsername(),10d, 5);
        auction.makeBid(winningBid);
        assertTrue(auction.getStatus() == AuctionStatus.OPEN);

        auction.close();
        assertEquals(auction.getStatus(), AuctionStatus.CLOSED);
        assertEquals(auction.getWinningBid(), winningBid);
    }
}
