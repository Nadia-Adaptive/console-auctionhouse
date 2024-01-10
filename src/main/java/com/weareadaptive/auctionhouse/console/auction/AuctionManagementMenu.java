package com.weareadaptive.auctionhouse.console.auction;

import com.weareadaptive.auctionhouse.console.ConsoleMenu;
import com.weareadaptive.auctionhouse.console.MenuContext;
import com.weareadaptive.auctionhouse.model.Auction;
import com.weareadaptive.auctionhouse.model.AuctionStatus;
import com.weareadaptive.auctionhouse.model.Bid;

import java.util.Optional;

import static com.weareadaptive.auctionhouse.utils.PromptUtil.*;

public class AuctionManagementMenu extends ConsoleMenu {
    @Override
    public void display(MenuContext context) {
        createMenu(context,
                option("Create an auction", this::createAuction),
                option("See your auctions", this::listUserAuctions),
                option("Close an auction", this::closeAuction),
                option("Make a bid", this::placeABid),
                option("See won bids", this::listWonBids),
                option("See lost bids", this::listLostBids),
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

        out.println("=====> Your Auctions");
        out.println("===================================");

        allAuctions.forEach(a -> out.printf(
                "Id: %d%nSymbol: %s%nStatus: %s%nAll Bids: %s%n%n", a.getId(),
                a.getSymbol(),
                a.getStatus().toString(),
                a.getBids()
                        .map(b -> "Bid{User: %s, Offer: %.2f, Quantity: %d}".formatted(
                                b.getBuyer(), b.getPrice(),
                                b.getQuantity()))
                        .reduce((String acc, String val) -> String.join("%n", acc, val)
                        ).orElse("")));

        pressEnter(context);
    }

    private void closeAuction(final MenuContext context) {
        final var out = context.getOut();

        out.println("=====> Close an Auction");
        out.println("Here are all your open auctions");

        out.println("===============================\n");
        context.getState().auctionState().stream()
                .filter(a -> a.getSeller().equals(context.getCurrentUser().getUsername()) && a.getStatus() != AuctionStatus.CLOSED)
                .forEach(a -> out.println(formatAuctionEntry(a)));
        out.println("===============================\n");

        final var auction = getAuction(context);

        out.println("Here is a summary of the auction.");

        out.println("Do you wish to close it?");

        do {
            final var isClosed = getStringInput(context, "Enter yes to close the auction or no to cancel the operation.");

            switch (isClosed.toLowerCase()) {
                case "yes": {
                    auction.get().close();
                    out.println("Closed the auction.");
                }
                case "no": {
                    out.println(cancelOperationText);
                    return;
                }
                default:
                    out.println(invalidInputMessage);
            }
        } while (true);
    }

    private void placeABid(MenuContext context) {
        final var out = context.getOut();
        final var auctionState = context.getState().auctionState();
        final var user = context.getCurrentUser();

        if (auctionState.stream().findAny().isEmpty()) {
            out.println("There are no open auctions right now. Please try again later.");
            return;
        }

        out.println("Here's the list of available auctions.");
        auctionState.getAvailableAuctions(user.getUsername())
                .forEach(a -> out.println(formatAuctionEntry(a)));

        out.println(cancelOperationText);

        final var auction = getAuction(context);

        if (auction.isEmpty()) {
            out.println(terminatedOperationText);
            return;
        }

        final var quantity = getIntegerInput(context, "Enter the quantity you're bidding for:");
        if (hasUserTerminatedOperation(quantity)) {
            out.println(terminatedOperationText);
            return;
        }

        final var price = getOfferPrice(context, auction.get());

        if (hasUserTerminatedOperation(price)) {
            out.println(terminatedOperationText);
            return;
        }

        final var bid = new Bid(user, price, quantity);
        auction.get().makeBid(bid);
        out.println("Bid created");
        pressEnter(context);
    }

    private void listWonBids(final MenuContext context) {
        final var out = context.getOut();
        final var username = context.getCurrentUser().getUsername();
        final var auctions = context
                .getState().auctionState()
                .getAuctionsUserBidOn(username);

        out.println("Here are the auctions you have bid on.");

        auctions.forEach(a ->
                a.getWinningBids().forEach(b -> out.println(formatBidEntry(a, b))
                ));
    }

    private void listLostBids(final MenuContext context) {

    }

    private double getOfferPrice(final MenuContext context, final Auction auction) {
        do {
            double offer = getDoubleInput(context, "Enter your offer (min amount is %.2f):".formatted(auction.getMinPrice()));

            if (offer > auction.getMinPrice()) {
                return offer;
            }
        } while (true);
    }

    private String formatAuctionEntry(final Auction a) {
        return "Auction Id: %d | Title: %s".formatted(a.getId(), a.getSymbol());
    }

    private String formatBidEntry(final Auction a, final Bid b) {
        return "Made on %s's %s | Offer price: %d | Quantity: %d | Status: %s%n".formatted(
                a.getSeller(), a.getSymbol(),
                b.getPrice(), b.getQuantity(),
                b.getStatus().toString());
    }

    private Optional<Auction> getAuction(final MenuContext context) {
        final var state = context.getState().auctionState();
        final var out = context.getOut();
        do {
            final var auctionId = getIntegerInput(context, "Enter the auction id:", true);
            if (hasUserTerminatedOperation(auctionId)) {
                return Optional.empty();
            }

            if (state.hasAuction(auctionId)) {
                final var auction = Optional.of(state.get(auctionId));

                if (auction.isPresent() && auction.get().getSeller().equals(context.getCurrentUser().getUsername())) {
                    return auction;
                }
            }
            out.println("Could not find auction. Please try again.");
        } while (true);
    }
}
