package io.todak.realworldsoftware.chapter06.in_memory;

import io.todak.realworldsoftware.chapter06.Position;
import io.todak.realworldsoftware.chapter06.Twoot;
import io.todak.realworldsoftware.chapter06.TwootQuery;
import io.todak.realworldsoftware.chapter06.TwootRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class InMemoryTwootRepository implements TwootRepository {

    private final List<Twoot> twoots = new ArrayList<>();

    private Position currentPosition = Position.INITIAL_POSITION;

    @Override
    public void query(TwootQuery twootQuery, Consumer<Twoot> callback) {
        if (!twootQuery.hasUsers()) {
            return;
        }

        var lastSeenPosition = twootQuery.getLastSeenPosition();
        var inUsers = twootQuery.getInUsers();

        twoots.stream()
                .filter(twoot -> inUsers.contains(twoot.getSenderId()))
                .filter(twoot -> twoot.isAfter(lastSeenPosition))
                .forEach(callback);
    }

    public void queryLoop(final TwootQuery twootQuery, final Consumer<Twoot> callback) {
        if (!twootQuery.hasUsers()) {
            return;
        }

        var lastSeenPosition = twootQuery.getLastSeenPosition();
        var inUsers = twootQuery.getInUsers();

        for (Twoot twoot : twoots) {
            if (inUsers.contains(twoot.getSenderId()) && twoot.isAfter(lastSeenPosition)) {
                callback.accept(twoot);
            }
        }
    }

    @Override
    public Optional<Twoot> get(final String id) {
        return twoots.stream().filter(twoot -> twoot.getId().equals(id))
                .findFirst();
    }

    @Override
    public void delete(final Twoot twwot) {
        twoots.remove(twwot);
    }

    @Override
    public Twoot add(final String id, final String userId, final String content) {
        currentPosition = currentPosition.next();
        var twootPosition = currentPosition;
        var twoot = new Twoot(id, userId, content, twootPosition);
        twoots.add(twoot);
        return twoot;
    }

    @Override
    public void clear() {
        twoots.clear();
    }


}
