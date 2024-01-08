package com.weareadaptive.auctionhouse.model;

import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;

import static com.weareadaptive.auctionhouse.utils.StringUtil.isNullOrEmpty;

public class Auction implements Model {
    private final Map<String, Bid> bids;
    private final int id;
    private final String symbol;
    private final double minPrice;
    private final int quantity;
    private final String seller;
    private AuctionStatus auctionStatus;
    private Instant closingTime;
    private double totalRevenue;
    private double totalQuantitySold;
    private List<Bid> winningBids;

    public Auction(final int id, final String seller, final String symbol, final double minPrice, final int quantity) {
        if (isNullOrEmpty(symbol)) {
            throw new BusinessException("Symbol cannot be empty!");
        }
        if (isNullOrEmpty(seller)) {
            throw new BusinessException("Seller cannot be empty!");
        }
        if (minPrice <= 0d) {
            throw new BusinessException("Price cannot be less than or equal to zero.");
        }
        if (quantity <= 0) {
            throw new BusinessException("Quantity cannot be less than or equal to zero.");
        }

        this.id = id;
        this.seller = seller;
        this.symbol = symbol;
        this.minPrice = minPrice;
        this.quantity = quantity;
        bids = new HashMap<>();
        winningBids = new ArrayList<>();
        this.auctionStatus = AuctionStatus.OPEN;
    }

    @Override
    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getMinPrice() {
        return minPrice;
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

    public List<Bid> getWinningBids() {
        if (auctionStatus == AuctionStatus.OPEN) {
            throw new BusinessException("Cannot get winning bids while auction is still open."); // TODO: Write better error message
        }
        return winningBids;
    }

    public void makeBid(final Bid bid) {
        bids.put(bid.getBuyer(), bid);
    }

    public void close() {
        int quantityLeft = this.quantity;
        final var sortedBids = bids.values().stream().sorted(Comparator.reverseOrder()).toList();

        for (Bid bid : sortedBids) {
            if (quantityLeft > 0) {
                quantityLeft -= bid.getQuantity();
                winningBids.add(bid);
            }
        }

        this.auctionStatus = AuctionStatus.CLOSED;
    }
}
