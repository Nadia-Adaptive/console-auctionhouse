package com.weareadaptive.auctionhouse.console;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import com.weareadaptive.auctionhouse.model.*;


public class ConsoleAuction {
    private final MenuContext menuContext;

    public ConsoleAuction() {
        var state = new ModelState(new UserState(), new OrganisationState());
        initData(state);
        var scanner = new Scanner(System.in);
        menuContext = new MenuContext(state, scanner, System.out);
    }

    // Creates the initial set of users
    private void initData(ModelState state) {
        Stream.of(
                        new User(state.userState().nextId(), "admin", "admin", "admin", "admin", "admin", true),
                        new User(state.userState().nextId(), "jf", "mypassword", "JF", "Legault", "Org 1"),
                        new User(state.userState().nextId(), "thedude", "biglebowski", "Walter", "Sobchak", "Org 2")
                )
                .forEach(u -> {
                    state.userState().add(u);
                    state.orgState().addUserToOrg(u);
                });
    }

    public void start() {
        LoginMenu loginMenu = new LoginMenu();
        loginMenu.display(menuContext);
    }

}
