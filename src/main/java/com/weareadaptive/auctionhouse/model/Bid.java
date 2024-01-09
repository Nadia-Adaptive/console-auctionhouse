package com.weareadaptive.auctionhouse.model;

import java.time.Instant;

import static com.weareadaptive.auctionhouse.utils.StringUtil.isNullOrEmpty;

public class Bid implements Comparable<Bid> {
    private final Instant timestamp = Instant.now();
    private final double price;
    private final int quantity;
    private final String buyer;
    private BidFillStatus status;

    public Bid(String buyer, double price, int quantity) {
        if (isNullOrEmpty(buyer)) {
            throw new BusinessException("Buyer cannot be empty or null.");
        }
        if (price <= 0) {
            throw new BusinessException("Price must be greater than zero.");
        }
        if (quantity <= 0) {
            throw new BusinessException("Quantity must be greater than zero.");
        }

        this.price = price;
        this.quantity = quantity;
        this.buyer = buyer;
        this.status = BidFillStatus.PENDING;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getBuyer() {
        return buyer;
    }

    @Override
    public int compareTo(Bid o) {
        if (price == o.price && timestamp.equals(o.timestamp)) {
            return 0;
        }

        if (this.price > o.price) {
            return 1;
        }

        if (this.price == o.price && o.timestamp.isAfter(this.timestamp)) {
            return 1;
        }
        return -1;
    }

    public BidFillStatus getStatus() {
        return status;
    }

    public void updateStatus(BidFillStatus fillStatus) {
        this.status = fillStatus;
    }
}
