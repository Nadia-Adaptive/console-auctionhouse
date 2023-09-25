package com.weareadaptive.auctionhouse.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.weareadaptive.auctionhouse.TestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class OrganisationStateTest {
    private OrganisationState state;

    @BeforeEach
    public void initState() {
        state = new OrganisationState();
        state.addUserToOrg(USER1);
    }

    @Test
    @DisplayName("addUserToOrg should create an organisation details object if it didn't already exist")
    public void shouldCreateNewOrganisationDetailIfItDoesntExist() {
        final var initialOrganisationCount = state.getAllOrganisations().count();
        state.addUserToOrg(USER3);
        assertNotEquals(2, initialOrganisationCount);
    }

    @Test
    @DisplayName("addUserToOrg should not create an organisation details object if it didn't already exist")
    public void shouldNotCreateNewOrganisationDetailIfItExists() {
        final var initialOrganisationCount = state.getAllOrganisations().count();
        state.addUserToOrg(USER2);
        assertEquals(1, initialOrganisationCount);
    }

    @Test
    @DisplayName("addUserToOrg should not create an organisation for admins")
    public void shouldNotCreateAdminOrganisation() {
        final var initialOrganisationCount = state.getAllOrganisations().count();
        state.addUserToOrg(ADMIN);
        assertEquals(1, initialOrganisationCount);
    }
}
