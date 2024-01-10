package com.weareadaptive.auctionhouse.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.weareadaptive.auctionhouse.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

public class AuctionTest {
    private static Auction auction;

    @BeforeEach
    public void beforeEach() {
        auction = new Auction(0, USER1.getUsername(), "JPYUSD", 1.00, 10);
    }

    @Test
    @DisplayName("makeBid should place a bid for the auction")
    public void shouldCreateABid() {
        final var bid = new Bid(USER1, 1.2d, 5);
        auction.makeBid(bid);

        assertEquals(1, auction.getBids().count());
    }

    @Test
    @DisplayName("closeAuction closes the auction with the bid highest quantity & price")
    public void auctionClosesWithOneBid() {
        final var winningBid = new Bid(USER2, 10d, 5);
        auction.makeBid(winningBid);
        assertTrue(auction.getStatus() == AuctionStatus.OPEN);

        auction.close();
        assertEquals(auction.getStatus(), AuctionStatus.CLOSED);
        assertEquals(auction.getWinningBids().findFirst(), winningBid);
    }

    @Test
    @DisplayName("when there's multiple bids with the same price, closeAuction fills earliest offer first")
    public void auctionClosesWithEarliestBid() {
        final var bid01 = new Bid(USER1, 1.2d, 5);
        final var bid02 = new Bid(USER2, 1.2d, 5);
        auction.makeBid(bid01);
        auction.makeBid(bid02);

        assertTrue(auction.getStatus() == AuctionStatus.OPEN);

        auction.close();
        final var winningBids = auction.getWinningBids().toList();
        assertEquals(AuctionStatus.CLOSED, auction.getStatus());
        assertEquals(bid01, winningBids.get(0));
        assertEquals(bid02, winningBids.get(1));
    }

    @Test
    @DisplayName("closeAuction fills as many bids as possible before closing")
    public void auctionClosesWithMultipleBids() {
        final var bid01 = new Bid(USER1, 1.2d, 3);
        final var bid02 = new Bid(USER2, 1.2d, 7);
        final var bid03 = new Bid(USER3, 1.2d, 7);

        auction.makeBid(bid01);
        auction.makeBid(bid02);
        auction.makeBid(bid03);

        assertTrue(auction.getStatus() == AuctionStatus.OPEN);

        auction.close();
        assertEquals(AuctionStatus.CLOSED, auction.getStatus());
        assertEquals(2, auction.getWinningBids().count());
    }

    @Test
    @DisplayName("closeAuction fills as many bids as possible including bids it can only partially fill")
    public void auctionClosesWithMultipleFilledAndOnePartialBid() {
        final var bid01 = new Bid(USER1, 1.2d, 3);
        final var bid02 = new Bid(USER2, 1.2d, 3);
        final var bid03 = new Bid(USER3, 1.2d, 6);

        auction.makeBid(bid01);
        auction.makeBid(bid02);
        auction.makeBid(bid03);

        assertTrue(auction.getStatus() == AuctionStatus.OPEN);

        auction.close();
        assertEquals(AuctionStatus.CLOSED, auction.getStatus());
        assertEquals(3, auction.getWinningBids().count());
    }

    @Test
    @DisplayName("closeAuction fills as many bids as possible by highest price")
    public void auctionClosesWithMultipleBidsByHighestPrice() {
        final var bid03 = new Bid(USER3, 12d, 1);
        final var bid01 = new Bid(USER1, 1.2d, 3);
        final var bid02 = new Bid(USER2, 1.24d, 5);

        auction.makeBid(bid01);
        auction.makeBid(bid02);
        auction.makeBid(bid03);

        auction.close();

        final var winningBids = auction.getWinningBids().toList();

        assertEquals(AuctionStatus.CLOSED, auction.getStatus());
        assertEquals(bid03, winningBids.get(0));
        assertEquals(bid02, winningBids.get(1));
        assertEquals(bid01, winningBids.get(2));
    }

    @Test
    @DisplayName("closeAuction updates the bid status")
    public void auctionClosesBidsAndUpdatesTheirStatus() {
        final var bid01 = new Bid(USER1, 1.2d, 5);
        final var bid02 = new Bid(USER2, 1.24d, 7);

        auction.makeBid(bid01);
        auction.makeBid(bid02);

        auction.close();

        assertEquals(AuctionStatus.CLOSED, auction.getStatus());

        assertEquals(bid02.getStatus(), BidFillStatus.FILLED);
        assertEquals(bid01.getStatus(), BidFillStatus.PARTIALFILL);
    }

    @Test
    @DisplayName("makeBid throws an exception when the auction is closed")
    public void biddingOnClosedAuctionThrowsException() {
        final var bid01 = new Bid(USER1, 1.2d, 6);

        auction.close();

        assertThrows(BusinessException.class, () -> auction.makeBid(bid01));
    }
}
