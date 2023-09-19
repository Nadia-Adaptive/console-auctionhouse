package com.weareadaptive.auctionhouse.model;

import com.weareadaptive.auctionhouse.utils.StringUtil;

import java.util.List;

public record OrganisationDetails(String organisationName, List<User> users) {

}
