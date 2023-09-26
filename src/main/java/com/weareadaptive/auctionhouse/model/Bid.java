package com.weareadaptive.auctionhouse.model;

import java.util.Date;

public record Bid(double offer, int quantity, Date timestamp, String buyer) {
}
