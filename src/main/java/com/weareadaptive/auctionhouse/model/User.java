package com.weareadaptive.auctionhouse.model;

import static com.weareadaptive.auctionhouse.utils.StringUtil.isNullOrEmpty;

public class User implements Model {
    private final int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String organisation;
    private final boolean isAdmin;
    private AccessStatus accessStatus;

    public User(int id, String username, String password, String firstName, String lastName,
                String organisation) {
        this(id, username, password, firstName, lastName, organisation, false);
    }

    public User(int id, String username, String password, String firstName, String lastName,
                String organisation, boolean isAdmin) {
        if (isNullOrEmpty(username)) {
            throw new BusinessException("username cannot be null or empty");
        }
        if (isNullOrEmpty(password)) {
            throw new BusinessException("password cannot be null or empty");
        }
        if (isNullOrEmpty(firstName)) {
            throw new BusinessException("firstName cannot be null or empty");
        }
        if (isNullOrEmpty(lastName)) {
            throw new BusinessException("lastName cannot be null or empty");
        }
        if (isNullOrEmpty(organisation)) {
            throw new BusinessException("organisation cannot be null or empty");
        }
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.organisation = organisation;
        this.isAdmin = isAdmin;
        this.accessStatus = AccessStatus.ALLOWED;
    }

    public String getUsername() {
        return username;
    }

    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getId() {
        return id;
    }

    public String getOrganisation() {
        return organisation;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public AccessStatus getAccessStatus() {
        return accessStatus;
    }

    public void setAccessStatus(AccessStatus accessStatus) {
        this.accessStatus = accessStatus;
    }

    public void update(String username, String password, String firstName, String lastName, String organisation) {
        this.username = isNullOrEmpty(username) ? this.username : username;
        this.password = isNullOrEmpty(password) ? this.password : password;
        this.firstName = isNullOrEmpty(firstName) ? this.firstName : firstName;
        this.lastName = isNullOrEmpty(lastName) ? this.lastName : lastName;
        this.organisation = isNullOrEmpty(organisation) ? this.organisation : organisation;
    }
}
