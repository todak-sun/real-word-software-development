package io.todak.realworldsoftware.chapter02;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class SimpleValidator {

    private String description;
    private String date;
    private String amount;

    public SimpleValidator(String description, String date, String amount) {
        this.description = Objects.requireNonNull(description);
        this.date = Objects.requireNonNull(date);
        this.amount = Objects.requireNonNull(amount);
    }

    public Notification validate() {
        final Notification notification = new Notification();

        if (this.description.length() > 100) {
            notification.addError("100글자를 초과할 수 없습니다.");
        }

        final LocalDate parsedDate;
        try {
            parsedDate = LocalDate.parse(this.date);
            if (parsedDate.isAfter(LocalDate.now())) {
                notification.addError("현재보다 미래의 시간이 올 수 없습니다.");
            }
        } catch (DateTimeParseException e) {
            notification.addError("유효한 날짜 형식이 아닙니다.");
        }

        final double amount;
        try {
            amount = Double.parseDouble(this.amount);
        } catch (NumberFormatException e) {
            notification.addError("유효한 숫자 형식이 아닙니다.");
        }

        return notification;
    }
}
