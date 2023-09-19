package com.weareadaptive.auctionhouse.console.admin;

import com.weareadaptive.auctionhouse.console.ConsoleMenu;
import com.weareadaptive.auctionhouse.console.MenuContext;
import com.weareadaptive.auctionhouse.model.OrganisationDetails;
import com.weareadaptive.auctionhouse.model.User;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

import static com.weareadaptive.auctionhouse.utils.PromptUtil.*;

public class UserManagementMenu extends ConsoleMenu {

    @Override
    public void display(final MenuContext context) {
        createMenu(context,
                option("Create user", this::createNewUser),
                option("Show users", this::listAllUsers),
                option("Update user details", this::updateExistingUser),
                option("Block/Unblock a user", this::changeUserAccess),
                option("Show organisations", this::listOrganisations),
                option("Show organisations details", this::listOrganisationsDetails),
                leave("Go back")
        );
    }

    private void changeUserAccess(final MenuContext context) {
        var out = context.getOut();
        listUsers(context);

        var user = getUser(context);
        if (user.isEmpty()) {
            out.println(terminatedOperationText);
            return;
        }

        if (user.get().hasAccess()) {
            out.println("This user has access.");
        } else {
            out.println("This user does not have access");
        }

        do {
            var input = getStringInput(context, "Please input allow or block to change the user's access.\n%s".formatted(cancelOperationText));
            if (input.equalsIgnoreCase("q")) {
                out.println(terminatedOperationText);
                return;
            }

            switch (input.toLowerCase()) {
                case "block" -> user.get().setHasAccess(false);
                case "allow" -> user.get().setHasAccess(true);
                default -> out.println(invalidInputMessage);
            }
            break;
        } while (true);
    }

    private User createNewUser(final MenuContext context, final int id) {
        String fName = getStringInput(context, "What is the user's first name?");
        String lName = getStringInput(context, "What is the user's last name?");
        String uName = promptForUsername(context);
        String organisation = getStringInput(context, "Where does the user work?");
        String password = getPassword(context);

        return new User(id, uName, password, fName, lName, organisation);
    }

    private void updateExistingUser(final MenuContext context) {
        final var out = context.getOut();
        final var userState = context.getState().userState();
        final var user = getUser(context);
        if (user.isEmpty()) {
            out.println(terminatedOperationText);
            return;
        }

        final var id = user.get().getId();
        final String fName = getStringOrEmptyInput(context, "What is new first name?").orElse(user.get().getFirstName());
        final String lName = getStringOrEmptyInput(context, "What is new last name?").orElse(user.get().getLastName());
        final String uName = promptForUsername(context);
        final String organisation = getStringOrEmptyInput(context, "What is the user's new organisation?").orElse(user.get().getOrganisation());
        final String password = getPassword(context);

        userState.updateUser(id, new User(id, uName, password, fName, lName, organisation));
        out.println("User updated.");
        pressEnter(context);
    }

    private Optional<User> getUser(final MenuContext context) {
        var state = context.getState().userState();
        var out = context.getOut();
        do {
            var userId = getIntegerInput(context, "Please input the user's id.");
            var user = state.get(userId);
            if (user == null) {
                out.println("User does not exist. Please try again or input -1 to quit.");
                continue;
            } else if (userId == -1) {
                return Optional.empty();
            }
            return Optional.of(user);
        } while (true);
    }

    private void createNewUser(final MenuContext context) {
        var userState = context.getState().userState();
        var orgState = context.getState().orgState();
        var out = context.getOut();

        var newUser = createNewUser(context, userState.nextId());

        out.printf("Created new user %s%n", newUser.getUsername());
        orgState.addUserToOrg(newUser);
        userState.add(newUser);

        pressEnter(context);
    }

    private String getPassword(final MenuContext context) {
        var out = context.getOut();
        var scanner = context.getScanner();

        do {
            out.println("Input the user's password: ");
            var password = readPassword(scanner);

            out.println("Confirm the user's password:");
            var confirmPassword = readPassword(scanner);

            if (confirmPassword.equals(password)) {
                return password;
            } else {
                out.println("The passwords don't match. Please try again.");
            }
        } while (true);
    }

    private String promptForUsername(final MenuContext context) {
        var out = context.getOut();

        do {
            String username = getStringInput(context, "What is the user's username?");

            if (username.matches("^[a-zA-Z0-9]*$")) {
                return username;
            }
            out.println("Invalid username. Please try again.");
        } while (true);
    }

    private void listOrganisationsDetails(final MenuContext context) {
        var out = context.getOut();
        var orgState = context.getState().orgState();

        out.println("== Organisation details");
        orgState.getAllDetails()
                .sorted(Comparator.comparing(OrganisationDetails::organisationName))
                .forEach(out::println);

        pressEnter(context);
    }

    private void listOrganisations(final MenuContext context) {
        var out = context.getOut();
        var orgState = context.getState().orgState();

        out.println("== All organisations");
        orgState.getAllOrganisations().forEach(out::println);

        pressEnter(context);
    }

    private void listAllUsers(final MenuContext context) {
        var out = context.getOut();

        out.println("== All Users");
        printAllUsers(context);
        out.println();

        pressEnter(context);
    }

    private void printAllUsers(MenuContext context) {
        final var userState = context.getState().userState();
        userState.stream().forEach(u -> printUser(context, u));
    }

    private void printUser(final MenuContext context, final User user) {
        context.getOut().println("Id: %d, Username: %s, First name: %s, Last name: %s, Organisation: %s".formatted(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getOrganisation()));
    }
}