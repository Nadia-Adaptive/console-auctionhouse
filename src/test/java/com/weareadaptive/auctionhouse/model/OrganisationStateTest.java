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
        assertNotEquals(initialOrganisationCount, 2);
    }

    @Test
    @DisplayName("addUserToOrg should not create an organisation details object if it didn't already exist")
    public void shouldNotCreateNewOrganisationDetailIfItExists() {
        final var initialOrganisationCount = state.getAllOrganisations().count();
        state.addUserToOrg(USER2);
        assertEquals(initialOrganisationCount, 1);
    }

    @Test
    @DisplayName("addUserToOrg should not create an organisation for admins")
    public void shouldNotAddAdminToOrganisation() {
        final var initialOrganisationCount = state.getAllOrganisations().count();
        state.addUserToOrg(ADMIN);
        assertEquals(initialOrganisationCount, 1);
    }
}
