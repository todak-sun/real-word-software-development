package io.todak.realworldsoftware.chapter05;

@FunctionalInterface
public interface Action {
    void execute(Facts facts);
}
