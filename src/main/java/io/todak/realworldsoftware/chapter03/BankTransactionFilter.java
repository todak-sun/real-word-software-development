package io.todak.realworldsoftware.chapter03;

// 한 개의 추상 메서드를 포함하는 인터페이스를 함수형 인터페이스라 부르며
// @FunctionalInterface 애너테이션을 이용하면 이러한 인터페이스의 의도를 더 명확하게 표현할 수 있다.
@FunctionalInterface
public interface BankTransactionFilter {
    boolean test(BankTransaction bankTransaction);
}
