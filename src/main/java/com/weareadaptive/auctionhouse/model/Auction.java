package com.weareadaptive.auctionhouse.model;

import java.util.ArrayList;
import java.util.List;

import static com.weareadaptive.auctionhouse.utils.StringUtil.isNullOrEmpty;

public class Auction implements Model {
    private final List<Bid> bids;
    private final int id;
    private final String symbol;
    private final double price;
    private final int quantity;

    public Auction(final int id, final String symbol, final double price, final int quantity) {
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
        this.symbol = symbol;
        this.price = price;
        this.quantity = quantity;
        bids = new ArrayList<Bid>();
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
}
