package com.weareadaptive.auctionhouse.model;

import static java.lang.String.format;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserState extends State<User> {

    private final Map<String, User> usernameIndex;

    public UserState() {
        usernameIndex = new HashMap<>();
    }


    public Optional<User> findUserByUsername(String username, String password) {
        return stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username))
                .filter(user -> user.validatePassword(password))
                .findFirst();
    }

    public void updateUser(int userId, User newUser) {
        var oldUser = get(userId);
        usernameIndex.remove(oldUser.getUsername());
        usernameIndex.put(newUser.getUsername(), newUser);
        update(userId, newUser);
    }

    @Override
    protected void onAdd(User model) {
        super.onAdd(model);
        usernameIndex.put(model.getUsername(), get(model.getId()));
    }
}
