package io.todak.realworldsoftware.chapter06;

/**
 * UI 어댑터가 구현해야 하는 인터페이스로 Twoot 객체를 UI로 푸시한다.
 */
public interface ReceiverEndPoint {
    void onTwoot(Twoot twoot);
}
