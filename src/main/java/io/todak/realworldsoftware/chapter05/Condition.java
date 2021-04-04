package io.todak.realworldsoftware.chapter05;

@FunctionalInterface
public interface Condition {
    boolean evaluate(Facts facts);
}
