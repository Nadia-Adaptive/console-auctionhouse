package com.weareadaptive.auctionhouse.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AuctionState extends State<Auction> {
    public Stream<Auction> getUserAuctions(final String username) {
        return stream().filter(a -> a.getSeller().equals(username));
    }

    public boolean hasAuction(int id) {
        return stream().anyMatch(a -> a.getId() == id);
    }

    public long numOfAvailableAuctions(final String currentUser) {
        return stream().filter(a -> !a.getSeller().equalsIgnoreCase(currentUser) && a.getStatus() == AuctionStatus.OPEN).count();
    }

    public List<Bid> getAllUserWinningBids(final String username) {
        final var bids = new ArrayList<Bid>();
        stream().forEach(a ->
                bids.addAll(a.getBids().filter(b -> b.getBuyer().equals(username) && (b.getStatus()== BidFillStatus.FILLED || b.getStatus()== BidFillStatus.PARTIALFILL)).toList())
        );
        return bids;
    }

    public List<Bid> getAllUserLosingBids(final String username) {
        final var bids = new ArrayList<Bid>();
        stream().forEach(a ->
                bids.addAll(a.getBids().filter(b -> b.getBuyer().equals(username) && b.getStatus()== BidFillStatus.UNFILLED).toList())
        );
        return bids;
    }
}
