package io.todak.realworldsoftware.chapter06.example;

import io.todak.realworldsoftware.chapter06.ReceiverEndPoint;
import io.todak.realworldsoftware.chapter06.Twoot;

public class PrintingEndPointSnippets {

    public static void main(String[] args) {

        final ReceiverEndPoint anonymousClass = new ReceiverEndPoint() {
            @Override
            public void onTwoot(Twoot twoot) {
                System.out.println(twoot.getSenderId() + ": " + twoot.getContent());
            }
        };

    }

    final ReceiverEndPoint lambda = twoot -> System.out.println(twoot.getSenderId() + ": " + twoot.getContent());

}
