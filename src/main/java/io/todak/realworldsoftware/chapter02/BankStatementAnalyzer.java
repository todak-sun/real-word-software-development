package io.todak.realworldsoftware.chapter02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Month;
import java.util.List;

public class BankStatementAnalyzer {

    private static final String RESOURCES = "src/main/resources/";

    public void analyze(final String fileName, final BankStatementParser bankStatementParser) throws IOException {
        final Path path = Paths.get(RESOURCES + fileName);
        final List<String> lines = Files.readAllLines(path);

        final List<BankTransaction> bankTransactions = bankStatementParser.parseLinesFrom(lines);
        final BankStatementProcessor bankStatementProcessor = new BankStatementProcessor(bankTransactions);
        collectSummary(bankStatementProcessor);
    }

    private static void collectSummary(final BankStatementProcessor bankStatementProcessor) {
        System.out.println("총 거래량 : " + bankStatementProcessor.calculateTotalAmount());
        System.out.println("1월 거래량 : " + bankStatementProcessor.calculateTotalInMonth(Month.JANUARY));
        System.out.println("2월 거래량 : " + bankStatementProcessor.calculateTotalInMonth(Month.FEBRUARY));
        System.out.println("월급 거래량 : " + bankStatementProcessor.calculateTotalForCategory("Salary"));
    }

}
