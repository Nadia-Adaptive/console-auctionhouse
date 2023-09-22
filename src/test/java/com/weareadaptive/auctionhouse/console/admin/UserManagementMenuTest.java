package com.weareadaptive.auctionhouse.console.admin;

import com.weareadaptive.auctionhouse.console.MenuContext;
import com.weareadaptive.auctionhouse.model.ModelState;
import com.weareadaptive.auctionhouse.model.OrganisationState;
import com.weareadaptive.auctionhouse.model.UserState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Scanner;
import java.util.stream.Stream;

import static com.weareadaptive.auctionhouse.TestData.USER1;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserManagementMenuTest {
    private static Stream<Arguments> testArguments() {
        return Stream.of(
                Arguments.of("1\n%s\n\r\n7".formatted("q")),
                Arguments.of("1\n%s\n%s\n\r\n7".formatted(
                        USER1.getUsername(),
                        "q")),
                Arguments.of("1\n%s\n%s\n%s\n%s\n\r\n7".formatted(
                        USER1.getUsername(),
                        "password",
                        "password",
                        "q")),
                Arguments.of("1\n%s\n%s\n%s\n%s\n%s\n\r\n7".formatted(
                        USER1.getUsername(),
                        "password",
                        "password",
                        USER1.getFirstName(),
                        "q")),
                Arguments.of("1\n%s\n%s\n%s\n%s\n%s\n%s\n\r\n7".formatted(
                        USER1.getUsername(),
                        "password",
                        "password",
                        USER1.getFirstName(),
                        USER1.getLastName(),
                        "q"))
        );
    }

    private MenuContext createUserContext(final String src) {
        final UserManagementMenu menu = new UserManagementMenu();
        Scanner scanner = new Scanner(src);
        MenuContext context = new MenuContext(new ModelState(new UserState(), new OrganisationState()), scanner, System.out);
        context.getState().userState().nextId();

        menu.display(context);

        return context;
    }

    @Test
    @DisplayName("Create User option should add a new user with valid parameters to the app's state")
    public void createUserShouldAddANewUserToUserState() {
        final var password = "PASSWORD";
        final MenuContext context = createUserContext("%d\n%s\n%s\n%s\n%s\n%s\n%s\n\r\n7".formatted(
                1,
                USER1.getUsername(),
                password,
                password,
                USER1.getFirstName(),
                USER1.getLastName(),
                USER1.getOrganisation()
        ));


        assertTrue(context.getState().userState().stream().findAny().get().equals(USER1));
    }

    @ParameterizedTest
    @DisplayName("Create User operation should terminate if user enters Q at any stage")
    @MethodSource("testArguments") // TODO: Look this up
    public void createUserShouldTerminatesOnUserRequest(final String src) {
        //Assert
        assertDoesNotThrow(() -> createUserContext(src));
    }
}
