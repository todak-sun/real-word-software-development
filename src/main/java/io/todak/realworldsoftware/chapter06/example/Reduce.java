package io.todak.realworldsoftware.chapter06.example;

import io.todak.realworldsoftware.chapter06.Position;
import io.todak.realworldsoftware.chapter06.Twoot;

import java.util.Comparator;
import java.util.List;
import java.util.function.BinaryOperator;

import static io.todak.realworldsoftware.chapter06.Position.INITIAL_POSITION;

public class Reduce {

    private final BinaryOperator<Position> maxPosition = BinaryOperator.maxBy(Comparator.comparingInt(Position::getValue));

    Twoot combineTwootsBy(final List<Twoot> twoots, final String senderId, final String newId) {
        return twoots
                .stream()
                .reduce(new Twoot(newId, senderId, "", INITIAL_POSITION),
                        (acc, twoot) -> new Twoot(
                                newId,
                                senderId,
                                twoot.getContent() + acc.getContent(),
                                maxPosition.apply(acc.getPosition(), twoot.getPosition())
                        ));
    }

}
