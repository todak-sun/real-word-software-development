package io.todak.realworldsoftware.chapter06.in_memory;

import io.todak.realworldsoftware.chapter06.FollowStatus;
import io.todak.realworldsoftware.chapter06.User;
import io.todak.realworldsoftware.chapter06.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryUserRepository implements UserRepository {

    private final Map<String, User> userIdToUser = new HashMap<>();

    @Override
    public Optional<User> get(String userId) {
        return Optional.ofNullable(userIdToUser.get(userId));
    }

    @Override
    public boolean add(User user) {
        return userIdToUser.putIfAbsent(user.getId(), user) == null;
    }

    @Override
    public void update(User user) {

    }

    @Override
    public FollowStatus follow(User follower, User userToFollow) {
        return userToFollow.addFollower(follower);
    }

    @Override
    public void clear() {
        userIdToUser.clear();
    }

    @Override
    public void close() throws Exception {

    }
}
