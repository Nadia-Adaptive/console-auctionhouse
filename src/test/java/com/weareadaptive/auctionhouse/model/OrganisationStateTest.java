package com.weareadaptive.auctionhouse.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static com.weareadaptive.auctionhouse.TestData.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class OrganisationStateTest {
    private OrganisationState state;

    @BeforeEach
    public void initState() {
        state = new OrganisationState();
        state.addUserToOrg(USER1);
    }

    @Test
    @DisplayName("addUserToOrg should create new organisation details object if it didn't exist")
    public void shouldCreateNewOrganisationDetailIfItDoesntExist() {
        assert (state.getAllOrganisations().count() == 1);
        state.addUserToOrg(USER3);
        assert (state.getAllOrganisations().count() == 2);
    }

    @Test
    @DisplayName("addUserToOrg should create new organisation details object if it didn't exist")
    public void shouldNotCreateNewOrganisationDetailIfItExists() {
        assert (state.getAllOrganisations().count() == 1);
        state.addUserToOrg(USER2);
        assert (state.getAllOrganisations().count() == 1);
    }

    // TODO: Figure/find out if admins should only belong to the admin org (which isn't currently created)
    //  and adjust the functionality on answer
    @Test
    @DisplayName("addUserToOrg should not add an admin to an organisation")
    public void shouldNotAddAdminToOrganisation() {
        assert (state.getAllOrganisations().count() == 1);
        state.addUserToOrg(ADMIN);
        assert (state.getAllOrganisations().count() == 1);
    }
}
