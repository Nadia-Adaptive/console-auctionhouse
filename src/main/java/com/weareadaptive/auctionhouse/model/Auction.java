package com.weareadaptive.auctionhouse.model;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static com.weareadaptive.auctionhouse.utils.StringUtil.isNullOrEmpty;

public class Auction implements Model {
    private final Map<String, Bid> bids;
    private final int id;
    private final String symbol;
    private final double price;
    private final int quantity;
    private final String seller;
    private AuctionStatus auctionStatus;

    private Bid winningBid;

    public Auction(final int id, final String seller, final String symbol, final double price, final int quantity) {
        if (isNullOrEmpty(symbol)) {
            throw new BusinessException("Symbol cannot be empty!");
        }
        if (price <= 0d) {
            throw new BusinessException("Price cannot be less than or equal to zero.");
        }
        if (quantity <= 0) {
            throw new BusinessException("Quantity cannot be less than or equal to zero.");
        }

        this.id = id;
        this.seller = seller;
        this.symbol = symbol;
        this.price = price;
        this.quantity = quantity;
        bids = new HashMap<>();
        this.auctionStatus = AuctionStatus.OPEN;
    }

    @Override
    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getSeller() {
        return seller;
    }

    public AuctionStatus getStatus() {
        return auctionStatus;
    }

    public Stream<Bid> getBids() {
        return bids.values().stream();
    }

    public Bid getWinningBid(){
        if(auctionStatus==AuctionStatus.OPEN){
            throw new BusinessException("Auction is still open."); // TODO: Write better error message
        }
        return winningBid;
    }

    public void makeBid(final Bid bid) {
        bids.put(bid.getBuyer(), bid);
    }

    public void close() {
        winningBid = bids.values().stream().reduce((acc, b) -> {
            final var currentOffer = b.getPrice() * b.getQuantity();
            final var prevOffer = acc.getPrice() * acc.getQuantity();
            // order bids by price offer descending
            // fill by highest price first and whatever quantity is left is filled for next highest
            if (currentOffer > prevOffer || b.getTimestamp().isBefore(acc.getTimestamp())) {
               return b;
            }
            return acc;
        }).orElse(null);

        this.auctionStatus = AuctionStatus.CLOSED;
    }
}
