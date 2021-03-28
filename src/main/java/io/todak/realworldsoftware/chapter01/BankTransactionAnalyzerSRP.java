package io.todak.realworldsoftware.chapter01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

public class BankTransactionAnalyzerSRP {

    private static final String RESOURCES = "src/main/resources/";

    public static void main(String[] args) throws IOException {
        final BankStatementCSVParser bankStatementCSVParser = new BankStatementCSVParser();

        final Path path = Paths.get(RESOURCES + "bank-data-simple.csv");
        final List<String> lines = Files.readAllLines(path);

        final List<BankTransaction> bankTransactions = bankStatementCSVParser.parseLinesFrom(lines);

        System.out.println("총 거래량 : " + calculateTotalAmount(bankTransactions));
        System.out.println("1월 총 거래량 : " + selectInMonth(bankTransactions, Month.JANUARY));
    }

    private static double calculateTotalAmount(final List<BankTransaction> bankTransactions) {
        return bankTransactions.stream().mapToDouble(BankTransaction::getAmount).sum();
    }

    private static List<BankTransaction> selectInMonth(final List<BankTransaction> bankTransactions, final Month month) {
        return bankTransactions.stream()
                .filter(bankStatement -> month.equals(bankStatement.getDate().getMonth()))
                .collect(Collectors.toList());
    }
}
