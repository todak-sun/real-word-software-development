package io.todak.realworldsoftware.chapter06;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public class User {

    private final String id;
    private final byte[] password;
    private final byte[] salt;
    private final Set<User> followers = new HashSet<>();
    private final Set<String> following = new HashSet<>();

    private Position lastSeenPosition;
    private ReceiverEndPoint receiverEndPoint;

    public User(final String id, final byte[] password, final byte[] salt, final Position lastSeenPosition) {

        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(password, "password");

        this.id = id;
        this.password = password;
        this.salt = salt;
        this.lastSeenPosition = lastSeenPosition;
    }

    public byte[] getPassword() {
        return password;
    }

    public byte[] getSalt() {
        return salt;
    }

    public String getId() {
        return id;
    }

    public Stream<User> followers() {
        return followers.stream();
    }

    boolean receiveTwoot(final Twoot twoot) {
        if (isLoggedOn()) {
            receiverEndPoint.onTwoot(twoot);
            lastSeenPosition = twoot.getPosition();
            return true;
        }
        return false;
    }

    boolean isLoggedOn() {
        return receiverEndPoint != null;
    }

    public FollowStatus addFollower(final User user) {
        if (followers.add(user)) {
            user.following.add(id);
            return FollowStatus.SUCCESS;
        } else {
            return FollowStatus.ALREADY_FOLLOWING;
        }
    }

    void onLogon(ReceiverEndPoint receiverEndPoint) {
        this.receiverEndPoint = receiverEndPoint;
    }

    void onLogoff() {
        receiverEndPoint = null;
    }


    public Position getLastSeenPosition() {
        return lastSeenPosition;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                '}';
    }


    public Set<String> getFollowing() {
        return following;
    }
}
