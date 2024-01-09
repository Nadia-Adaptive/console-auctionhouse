package com.weareadaptive.auctionhouse;

import com.weareadaptive.auctionhouse.model.Auction;
import com.weareadaptive.auctionhouse.model.OrganisationDetails;
import com.weareadaptive.auctionhouse.model.User;

import java.util.Arrays;

public class TestData {
  public static final String ORG_1 = "Org 1";
  public static final String ORG_2 = "Org 2";
  public static final String AAPL = "AAPL";
  public static final String EBAY = "EBAY";
  public static final String FB = "FB";

  public static final User ADMIN = new User(0, "admin", "admin", "admin", "admin", "admin", true);
  public static final User USER1 = new User(1, "testuser1", "password", "john", "doe", ORG_1);
  public static final User USER2 = new User(2, "testuser2", "password", "john", "smith", ORG_1);
  public static final User USER3 = new User(3, "testuser3", "password", "jane", "doe", ORG_2);
  public static final User USER4 = new User(4, "testuser4", "password", "naomie", "legault", ORG_2);

  public static final OrganisationDetails ORGANISATION1 = new OrganisationDetails(ORG_1, Arrays.asList(USER1));
  public static final Auction AUCTION1 = new Auction(0, USER4.getUsername(), "TEST", 1.0, 10);
  public static final Auction AUCTION2 = new Auction(1, USER3.getUsername(), "TEST2", 2.0, 11);


  private TestData() {
  }
}
