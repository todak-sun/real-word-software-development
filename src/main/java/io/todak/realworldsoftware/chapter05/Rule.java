package io.todak.realworldsoftware.chapter05;

@FunctionalInterface
public interface Rule {
    void perform(Facts facts);
}
