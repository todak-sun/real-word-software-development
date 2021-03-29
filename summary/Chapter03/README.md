# Chapter 03.

## 도전 과제

이전 장에서 작성한 프로그램에 새로운 기능을 추가할 것.

## 목표

- 개방/폐쇄 원칙(OCP, open/closed principle)을 배우자.
- 언제 인터페이스를 사용하면 좋을지에 대한 일반적인 가이드 라인.
- 높은 결합도를 피하는 기법.
- 자바에서 언제 API에 예외를 포함하거나 포함하지 않을지를 결정
- 메이븐/그레이들 같은 빌드 도구를 이용해 자바 프로젝트를 시스템적으로 빌드하는 방법을 학습

## 요구 사항

1. 특정 입출금 내역을 검색할 수 있는 기능. 예를 들어 주어진 날짜 범위 또는 특정 범주의 입출금 내역 얻기.
2. 검색 결과의 요약 통계를 텍스트, HTML 등 다양한 형식으로 만들기.

## 개방/폐쇄 원칙

```java
public class BankStatementProcessor {

    public List<BankTransaction> findTransactionsGreaterThanEqual(final int amount) {
        final List<BankTransaction> result = new ArrayList<>();
        for (final BankTransaction bankTransaction : bankTransactions) {
            if (bankTransaction.getAmount() >= amount) {
                result.add(bankTransaction);
            }
        }
        return result;
    }

    public List<BankTransaction> findTransactionsInMonth(final Month month) {
        final List<BankTransaction> result = new ArrayList<>();
        for (final BankTransaction bankTransaction : bankTransactions) {
            if (bankTransaction.getDate().getMonth() == month) {
                result.add(bankTransaction);
            }
        }
        return result;
    }

    public List<BankTransaction> findTransactionsInMonthAndGreater(final Month month, final int amount) {
        final List<BankTransaction> result = new ArrayList<>();
        for (final BankTransaction bankTransaction : bankTransactions) {
            if (bankTransaction.getDate().getMonth() == month && bankTransaction.getAmount() >= amount) {
                result.add(bankTransaction);
            }
        }
        return result;
    }
}
```

위와 같이 요구사항이 추가될 때 마다, 메서드를 추가한다면 아래와 같은 문제가 있다.

- 거래 내역의 여러 속성을 조합할수록 코드가 점점 복잡해진다.
- 반복 로직과 비즈니스 로직이 결합되어 분리하기가 어려워진다.
- 코드를 반복한다.

개방/폐쇄 원칙을 적용하면 코드를 직접 바꾸지 않고 해당 메서드나 클래스의 동작을 바꿀 수 있다. 위 예제에 이 원칙을 적용하면 findTransactions() 메서드의 코드를 복사하거나   
새 파라미터를 추가하는 등 코드를 바꾸지 않고도 동작을 확장할 수 있다.

**인터페이스**를 활용하면 된다.

개방/폐쇄 원칙을 적용한 후에 장점

- 기존 코드를 바꾸지 않으므로 기존 코드가 잘못될 가능성이 줄어든다.
- 코드가 중복되지 않으므로 기존 코드의 재사용성이 높아진다.
- 결합도가 낮아지므로 코드 유지보수성이 좋아진다.

## 갓 인터페이스

아래와 같이 인터페이스를 만들면 많은 문제가 생긴다!

```java
interface BankTransactionProcessor {

    double calculateTotalAmount();

    double calculateTotalInMonth(Month month);

    double calculateTotalInJanuary();

    double calculateAverageAmount();

    double calculateAverageAmountForCategory(Category category);

    List<BankTransaction> findTransactions(BankTransactionFilter bankTransactionFilter);
}
```

- 모든 헬퍼 연산이 명시적인 API 정의에 포함되면서 인터페이스가 복잡해진다.
- 자바의 인터페이스는 모든 구현이 지켜야 할 규칙을 정의한다. 따라서, 인터페이스를 바꾸면 구현체의 코드를 바꿔야하며 잦은 변경은 문제가 발생할 수 있는 범위가 넓어짐을 의미한다.
- 월, 카테고리와 같은 BankTransaction 의 속성이 메서드의 이름으로 노출되면서 인터페이스가 도메인 객체의 특정 접근자에 종속되는 문제가 생겼다.
- 도메인 객체의 세부 내용이 변경되면, 인터페이스도 바뀌어야 하며 결과적으로 구현 코드가 바뀌게 된다.

## 명시적 API vs 암묵적 API

- 연산의 동작을 메서드(api)에 다 포함한다면, 가독성이 높아지고 쉽게 이해된다.
- 하지만 이 메서드의 용도가 특정 상황에 국한되어 각 상황에 맞는 새로운 메서드를 많이 만들어야 하는 상황이 벌어진다.
- 암묵적 API는 문서화를 잘 해두어야 하지만, 거래 내역을 검색하는데 필요한 모든 상황을 단순한 api로 처리할 수 있다.

## 도매인 객체

- 도메인 객체는 자신의 도메인과 관련된 클래스의 인스턴스다.
- 도메인 객체를 이용하면 결합을 깰 수 있다.

## 예외 처리

- 데이터를 적절하게 파싱하지 못 한다면?
- 입출금 내역을 포함하는 CSV 파일을 읽을 수 없다면?
- 응용프로그램을 실행하는 하드웨어에 램이나 저장 공간이 부족하다면?

## Validator 클래스의 필요성

- 검증 로직을 재사용해 코드를 중복하지 않는다.
- 시스템의 다른 부분도 같은 방법으로 검증할 수 있다.
- 로직을 독립적으로 유닛 테스트할 수 있다.
- 이 기법은 프로그램 유지보수와 이해하기 쉬운 SRP를 따른다.

## 예외 사용 가이드라인

- 예외를 무시하지 않는다.
    - 예외를 처리할 수 있는 방법이 명확하지 않더라도, 미확인 예외를 대신 던지자. 이렇게 하면 확인된 예외를 정말로 처리해야 할 때 런타임에서 어떤 문제가 발생하는지 먼저 확인한 다음, 이전 문제로 돌아와
      필요한 작업을 다시 시작할 수 있다.
- 일반적인 예외는 잡지 않는다.
    - 가능한 구체적으로 예외를 잡으면 가독성이 높아지고, 더 세밀하게 예외를 처리할 수 있다.
- 예외 문서화
    - API 수준에서 미확인 예외를 포함한 예외를 문서화하므로 API 사용자에게 문제 해결의 실마리를 제공한다.
- 특정 구현에 종속된 예외를 주의할 것
    - 특정 구현에 종속된 예외를 던지면 API의 캡슐화가 깨진다.
- 예외 vs 제어 흐름
    - 예외로 흐름을 제어하지 않는다.
    - 예외는 오류와 예외적인 시나리오를 처리하는 기능이다.
    - 예뢰를 정말 던져야 하는 상황이 아니라면 예외를 만들지 않아야 한다.

## 총 정리

- 개방/폐쇄 원칙을 이용하면 코드를 바꾸지 않고도 메서드나 클래스의 동작을 바꿀 수 있다.
- 개방/폐쇄 원칙을 이용하면 기존 코드를 바꾸지 않으므로 코드가 망가질 가능성이 줄어들며, 기존 코드 의 재사용성을 높이고, 결합도가 높아지므로 코드 유지보수성이 개선된다.
- 많은 메서드를 포함하는 갓 인터페이스는 복잡도와 결합도를 높인다.
- 너무 세밀한 메서드를 포함하는 인터페이스는 응집도를 낮춘다.
- API의 가독성을 높이고 쉽게 이해할 수 있도록 메서드 이름을 서술적으로 만들어야 한다.
- 연산 결과로 void를 반환하면 동작을 테스트하기가 어렵다.
- 자바의 예외는 문서화, 형식 안정성, 관심사 분리를 촉진한다.
- 확인된 예외는 불필요한 코드를 추가해야 하므로 되도록 사용하지 않는다.
- 너무 자세하게 예외를 적용하면 소프트웨어 개발의 생산성이 떨어진다.
- 노티피케이션 패턴을 이용하면 도메인 클래스로 오류를 수집할 수 있다.
- 예외를 무시하거나 일반적인 Exception을 잡으면 근본적인 문제를 파악하기가 어렵다.
- 빌드 도구를 사용하면 응용프로그램 빌드, 테스트, 배포 등 소프트웨어 개발 생명 주기 작업을 자동화할 수 있다.
- 요즘 자바 커뮤니티에서는 빌드 도구로 메이븐과 그레이들을 주로 사용한다
  