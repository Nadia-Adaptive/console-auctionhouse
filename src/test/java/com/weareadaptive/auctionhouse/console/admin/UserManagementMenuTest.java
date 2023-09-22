package com.weareadaptive.auctionhouse.console.admin;

import com.weareadaptive.auctionhouse.console.MenuContext;
import com.weareadaptive.auctionhouse.model.ModelState;
import com.weareadaptive.auctionhouse.model.OrganisationState;
import com.weareadaptive.auctionhouse.model.UserState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static com.weareadaptive.auctionhouse.TestData.USER1;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserManagementMenuTest {

//    @BeforeAll
//    public static void setup() {
//        final UserManagementMenu menu = new UserManagementMenu();
//        Scanner scanner = new Scanner(System.in);
//        MenuContext context = new MenuContext(null, scanner, System.out);
//        menu.display(context);
//    }

    @Test
    @DisplayName("Create User option should add a new user with specified parameters to the app's state")
    public void createUserShouldAddANewUserToUserState() {
        final UserManagementMenu menu = new UserManagementMenu();
        final var password = "PASSWORD";
        Scanner scanner = new Scanner("%d\n%s\n%s\n%s\n%s\n%s\n%s\n\r\n7".formatted(
               1,
                USER1.getUsername(),
                password,
                password,
                USER1.getFirstName(),
                USER1.getLastName(),
                USER1.getOrganisation()
        ));
        MenuContext context = new MenuContext(new ModelState(new UserState(), new OrganisationState()), scanner, System.out);
        context.getState().userState().nextId();

        //Act
        menu.display(context);

        //Assert
        assertTrue(context.getState().userState().stream().findAny().get().equals(USER1));
    }
}
