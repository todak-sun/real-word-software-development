package io.todak.realworldsoftware.chapter01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BankTransactionAnalyzerSimple2 {

    private static final String RESOURCES = "src/main/resources/";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static void main(final String... args) throws IOException {
        final Path path = Paths.get(RESOURCES + "bank-data-simple.csv");
        final List<String> lines = Files.readAllLines(path);
        double total = 0d;
        for (final String line : lines) {
            final String[] columns = line.split(",");
            final double amount = Double.parseDouble(columns[1]);
            total += amount;
        }

        System.out.println("총 거래량 : " + total);

        total = 0d;
        for (final String line : lines) {

            final String[] columns = line.split(",");
            final LocalDate date = LocalDate.parse(columns[0], DATE_FORMATTER);
            if (date.getMonth() == Month.JANUARY) {
                final double amount = Double.parseDouble(columns[1]);
                total += amount;
            }

        }
        System.out.println("1월 총 거래량 : " + total);

    }
}
