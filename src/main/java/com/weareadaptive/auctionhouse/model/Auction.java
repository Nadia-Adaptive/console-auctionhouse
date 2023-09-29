package com.weareadaptive.auctionhouse.model;

import java.util.*;
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

    public void makeBid(final String username, final double offer, final int quantity) {
        if (offer <= 0) {
            throw new BusinessException("Offer must be greater than zero.");
        }
        if (isNullOrEmpty(username)) {
            throw new BusinessException("Username cannot be empty or null.");
        }

        bids.put(username, new Bid(offer, quantity, new Date(), username));
    }

    public void close() {
        winningBid = bids.values().stream().reduce((acc, b) -> {
            final var totalValue = b.offer() * b.quantity();
            final var prevValue = acc.offer() * acc.quantity();
            // order bids by price offer descending
            // fill by highest price first and whatever quantity is left is filled for next highest
            if (totalValue >= prevValue && b.timestamp().before(acc.timestamp())) {
               return b;
            }
            return acc;
        }).orElse(null);

        this.auctionStatus = AuctionStatus.CLOSED;
    }
}
