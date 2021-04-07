package io.todak.realworldsoftware.chapter06.example;

import io.todak.realworldsoftware.chapter06.ReceiverEndPoint;
import io.todak.realworldsoftware.chapter06.Twoot;

public class PrintingEndPoint implements ReceiverEndPoint {
    @Override
    public void onTwoot(Twoot twoot) {
        System.out.println(twoot.getSenderId() + ": " + twoot.getContent());
    }
}
