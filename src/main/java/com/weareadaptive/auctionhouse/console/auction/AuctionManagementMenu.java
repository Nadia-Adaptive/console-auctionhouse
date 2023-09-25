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

        final var newAuction = new Auction(auctionState.nextId(), user.getUsername(), symbol, price, quantity);

        auctionState.add(newAuction);
        out.println("Created new auction.");

        pressEnter(context);
    }

    private void listUserAuctions(MenuContext context) {
        final var out = context.getOut();
        final var allAuctions = context.getState().auctionState().getUserAuctions(context.getCurrentUser().getUsername());

        out.println("=> Your Auctions");
        out.println("======================");

        allAuctions.forEach(a -> out.println("Id: %d%nSymbol: %s%nStatus: %s%nAll Bids: %s%n".formatted(
                a.getId(),
                a.getSymbol(),
                a.getStatus().toString(),
                a.getBids().map(b -> b.toString()).reduce((String acc, String val) ->
                        String.join("%n", acc, val)
                ))));

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
