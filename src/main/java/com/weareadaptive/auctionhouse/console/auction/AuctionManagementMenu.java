package com.weareadaptive.auctionhouse.console.auction;

import com.weareadaptive.auctionhouse.console.ConsoleMenu;
import com.weareadaptive.auctionhouse.console.MenuContext;
import com.weareadaptive.auctionhouse.model.Auction;

import static com.weareadaptive.auctionhouse.utils.PromptUtil.*;

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
        final var user = context.getCurrentUser();
        final var auctionState = context.getState().auctionState();
        final var out = context.getOut();

        out.println(cancelOperationText);

        final String symbol = getStringInput(context, "What is the product's symbol?");
        if (hasUserTerminatedOperation(symbol)) {
            out.println(terminatedOperationText);
            return;
        }

        final double price = getDoubleInput(context, "Enter the minimum asking price.");
        if (hasUserTerminatedOperation(price)) {
            out.println(terminatedOperationText);
            return;
        }

        final int quantity = getIntegerInput(context, "How much %s are you selling?".formatted(symbol));
        if (hasUserTerminatedOperation(quantity)) {
            out.println(terminatedOperationText);
            return;
        }

        final var newAuction = new Auction(auctionState.nextId(), symbol, price, quantity);

        out.printf("Created new auction.");
        auctionState.add(newAuction);

        pressEnter(context);
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
