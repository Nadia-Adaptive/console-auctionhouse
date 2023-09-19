package com.weareadaptive.auctionhouse.model;

import com.weareadaptive.auctionhouse.utils.StringUtil;

import java.util.*;
import java.util.stream.Stream;

public class OrganisationState {
    private final Map<String, OrganisationDetails> detailsMap;

    public OrganisationState() {
        this.detailsMap = new HashMap<>();
    }

    public Stream<OrganisationDetails> getAllDetails() {
        return detailsMap.values().stream();
    }

    public Stream<String> getAllOrganisations() {
        return detailsMap.keySet().stream();
    }

    public void addUserToOrg(User u) {
        if (u.isAdmin()) { // Assuming admins should not be added to an organisation
            return;
        }

        String orgName = u.getOrganisation();
//        var organisationDisplayStr = "Organisation %s".formatted(organisationName);
//        var displayUsers = users.stream()
//                .map(u -> "Username: %s %n".formatted(u.getUsername()).indent(2))
//                .reduce(organisationDisplayStr,
//                        (String acc, String val) -> String.join("\n", acc, val)
//                );
//        return StringUtil.isNullOrEmpty(displayUsers) ? organisationDisplayStr : displayUsers;

        // TODO: Should the organisation be created explicitly (i.e, dev checks outside of this method if the org exists and creates if not)
        //  or implicitly (i.e, org is created if it doesn't exist when this method is called)?
        //  I think explicit would be better (what happens if you want to create an organisation without any users?), but not sure how best achieved
        //  will go with implicit for now
        if (detailsMap.containsKey(orgName)) {
            detailsMap.get(orgName).users().add(u);
        } else {
            var users = new ArrayList<User>();
            users.add(u);
            detailsMap.put(orgName, new OrganisationDetails(orgName, users));
        }
    }
}
