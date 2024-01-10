package com.weareadaptive.auctionhouse.model;

import java.util.stream.Stream;

public class AuctionState extends State<Auction> {
    public Stream<Auction> getUserAuctions(final String username) {
        return stream().filter(a -> a.getSeller().equals(username));
    }

    public boolean hasAuction(int id) {
        return stream().anyMatch(a -> a.getId() == id);
    }

    public Stream<Auction> getAvailableAuctions(final String currentUser) {
        return stream().filter(a -> !a.getSeller().equalsIgnoreCase(currentUser) && a.getStatus() == AuctionStatus.OPEN);
    }

    public Stream<Auction> getAuctionsUserBidOn(final String username) {
        return stream().filter(a->!a.getSeller().equals(username) && a.getStatus().equals(AuctionStatus.CLOSED) && a.hasUserBid(username));
    }
}
