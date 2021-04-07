package io.todak.realworldsoftware.chapter06;

import io.todak.realworldsoftware.chapter06.database.DatabaseUserRepository;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class Twootr {

    private final TwootRepository twootRepository;
    private final UserRepository userRepository;

    public Twootr(final UserRepository userRepository, final TwootRepository twootRepository) {
        this.userRepository = userRepository;
        this.twootRepository = twootRepository;
    }

    public Optional<SenderEndPoint> onLogon(final String userId, final String password, final ReceiverEndPoint receiverEndPoint) {

        Objects.requireNonNull(userId, "userId");
        Objects.requireNonNull(password, "password");

        var authenticateUser = userRepository.get(userId)
                .filter(userOfSameId -> {
                    var hashedPassword = KeyGenerator.hash(password, userOfSameId.getSalt());
                    return Arrays.equals(hashedPassword, userOfSameId.getPassword());
                });

        authenticateUser.ifPresent(user -> {
            user.onLogon(receiverEndPoint);

            twootRepository.query(
                    new TwootQuery()
                            .inUsers(user.getFollowing())
                            .lastSeenPosition(user.getLastSeenPosition()),
                    user::receiveTwoot);

            userRepository.update(user);
        });


        return authenticateUser.map(user -> new SenderEndPoint(user, this));
    }

    public RegistrationStatus onRegisterUser(final String userId, final String password) {
        var salt = KeyGenerator.newSalt();
        var hashedPassword = KeyGenerator.hash(password, salt);
        var user = new User(userId, hashedPassword, salt, Position.INITIAL_POSITION);
        return userRepository.add(user) ? RegistrationStatus.SUCCESS : RegistrationStatus.DUPLICATE;
    }

    FollowStatus onFollow(final User follow, final String userIdToFollow) {
        return userRepository.get(userIdToFollow)
                .map(userToFollow -> userRepository.follow(follow, userToFollow))
                .orElse(FollowStatus.INVALID_USER);
    }

    Position onSendTwoot(final String id, final User user, final String content) {
        var userId = user.getId();
        var twoot = twootRepository.add(id, userId, content);
        user.followers()
                .filter(User::isLoggedOn)
                .forEach(follower -> {
                    follower.receiveTwoot(twoot);
                    userRepository.update(follower);
                });

        return twoot.getPosition();
    }

    DeleteStatus onDeleteTwoot(final String userId, final String id) {
        return twootRepository
                .get(id)
                .map(twoot -> {
                    var canDeleteTwoot = twoot.getSenderId().equals(userId);
                    if (canDeleteTwoot) {
                        twootRepository.delete(twoot);
                    }
                    return canDeleteTwoot ? DeleteStatus.SUCCESS : DeleteStatus.NOT_YOUR_TWOOT;
                })
                .orElse(DeleteStatus.UNKNONW_TWOOT);
    }

}
