package com.weareadaptive.auctionhouse.console.auction;

import com.weareadaptive.auctionhouse.console.ConsoleMenu;
import com.weareadaptive.auctionhouse.console.MenuContext;

public class AuctionManagementMenu extends ConsoleMenu {
    @Override
    public void display(MenuContext context) {
        createMenu(context,
                option("Create an auction", this::createAuction),
                option("See your auctions", this::listUserAuctions),
                option("Close an auction", this::closeAuction),
                option("Bid", this::placeABid),
                option("Won bids", this::listWonBids),
                option("Lost bids)", this::listLostBids),
                leave("Go back"));
    }

    private void createAuction(MenuContext context) {

    }

    private void listUserAuctions(MenuContext context) {

    }

    private void closeAuction(MenuContext context) {

    }

    private void placeABid(MenuContext context) {

    }

    private void listWonBids(MenuContext context) {

    }

    private void listLostBids(MenuContext context) {

    }
}
