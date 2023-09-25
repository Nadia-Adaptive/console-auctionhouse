package com.weareadaptive.auctionhouse.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static com.weareadaptive.auctionhouse.TestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserStateTest {
    private UserState state;

    @BeforeEach
    public void initState() {
        state = new UserState();
        Stream.of(
                ADMIN,
                USER1,
                USER2,
                USER3,
                USER4
        ).forEach(u -> state.add(u));
        state.setNextId(USER4.getId());
    }

    @Test
    @DisplayName("Should find a user when passed valid credentials")
    public void shouldFindUserWhenPassedValidCredentials() {
        final var user = state.findUserByUsername(ADMIN.getUsername(), "admin");

        assertEquals(ADMIN, user.get());
    }

}
