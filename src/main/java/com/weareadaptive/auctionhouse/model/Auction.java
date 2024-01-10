package com.weareadaptive.auctionhouse.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;

import static com.weareadaptive.auctionhouse.utils.StringUtil.isNullOrEmpty;

public class Auction implements Model {
    private final List<Bid> bids;
    private final int id;
    private final String symbol;
    private final double minPrice;
    private final int quantity;
    private final User seller;
    private AuctionStatus auctionStatus;
    private Instant closingTime;
    private BigDecimal totalRevenue;


    private int totalQuantitySold;
    final private List<Bid> winningBids;
    final private List<Bid> losingBids;

    public Auction(final int id, final User seller, final String symbol, final double minPrice, final int quantity) {
        if (isNullOrEmpty(symbol)) {
            throw new BusinessException("Symbol cannot be empty!");
        }
        if (seller == null) {
            throw new BusinessException("Seller cannot be null!");
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
        bids = new ArrayList<>();
        winningBids = new ArrayList<>();
        losingBids = new ArrayList<>();
        this.auctionStatus = AuctionStatus.OPEN;
        totalRevenue = new BigDecimal(0);
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

    public User getSeller() {
        return seller;
    }

    public AuctionStatus getStatus() {
        return auctionStatus;
    }

    public double getTotalRevenue() {
        return totalRevenue.doubleValue();
    }

    public int getTotalQuantitySold() {
        return totalQuantitySold;
    }

    public Stream<Bid> getBids() {
        return bids.stream();
    }

    public Stream<Bid> getWinningBids() {
        if (auctionStatus == AuctionStatus.OPEN) {
            throw new BusinessException("Cannot get winning bids while auction is open.");
        }
        return winningBids.stream();
    }

    public void placeABid(final Bid bid) {
        if (auctionStatus == AuctionStatus.CLOSED) {
            throw new BusinessException("Can't bid on closed auction.");
        }

        if (bid.getPrice() < minPrice) {
            throw new BusinessException("Bid doesn't meet minimum price.");
        }
        bids.add(bid);
    }

    public void close() {
        bids.stream().sorted(Comparator.reverseOrder()).forEach(bid -> {
            if (totalQuantitySold < quantity) {
                double fillQuantity = bid.getQuantity();
                totalQuantitySold += fillQuantity;

                if (totalQuantitySold > quantity) {
                    fillQuantity = bid.getQuantity() - (totalQuantitySold - quantity);
                    totalQuantitySold = quantity;
                }
                bid.fillBid(fillQuantity);

                totalRevenue = totalRevenue.add(BigDecimal.valueOf(bid.getPrice()).multiply(BigDecimal.valueOf(fillQuantity)));
                winningBids.add(bid);
            } else {
                bid.fillBid(0);
                losingBids.add(bid);
            }
        });
        // TODO: Search for, and possibly eliminate, any toList
        this.auctionStatus = AuctionStatus.CLOSED;
    }

    public Boolean hasUserBid(String username) {
        return bids.stream().anyMatch(b -> b.getBuyer().getUsername().equals(username));
    }

    @Override
    public String toString() {
        return "Auction{" +
                "id=" + id +
                ", symbol='" + symbol + '\'' +
                ", seller='" + seller + '\'' +
                '}';
    }

    public Stream<Bid> getLosingBids() {
        return losingBids.stream();
    }
}
