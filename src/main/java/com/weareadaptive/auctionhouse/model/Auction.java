package com.weareadaptive.auctionhouse.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static com.weareadaptive.auctionhouse.utils.StringUtil.isNullOrEmpty;

public class Auction implements Model {
    private final List<Bid> bids;
    private final int id;
    private final String symbol;
    private final double price;
    private final int quantity;
    private final String seller;
    private AuctionStatus auctionStatus;

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
        bids = new ArrayList<Bid>();
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
        return bids.stream();
    }

    public void makeBid(final String username, final double offer, final int quantity) {
        if (offer <= 0) {
            throw new BusinessException("Offer must be greater than zero.");
        }
        if (isNullOrEmpty(username)) {
            throw new BusinessException("Username cannot be empty or null.");
        }

        bids.add(new Bid(offer, quantity, new Date(), username));
    }
}
