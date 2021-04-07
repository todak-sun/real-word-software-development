package io.todak.realworldsoftware.chapter06;

import java.util.Optional;

public interface UserRepository extends AutoCloseable {

    Optional<User> get(String userId);

    boolean add(User user);

    void update(User user);

    void clear();

    FollowStatus follow(User follower, User userToFollow);

}
