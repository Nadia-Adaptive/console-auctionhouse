package com.weareadaptive.auctionhouse.model;

import java.util.stream.Stream;

public class AuctionState extends State<Auction> {
    public Stream<Auction> getUserAuctions(final String username) {
        return stream().filter(a->a.getSeller().equals(username));
    }
}
