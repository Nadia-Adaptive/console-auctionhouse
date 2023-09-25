package com.weareadaptive.auctionhouse.model;

import java.util.Date;

public record Bid(double offer, Date timestamp, String buyer) {
}
