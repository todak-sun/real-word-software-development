package io.todak.realworldsoftware.chapter03;

public class MainApplication {

    public static void main(final String[] args) throws Exception {
        final BankStatementAnalyzer bankStatementAnalyzer = new BankStatementAnalyzer();

        final BankStatementParser statementParser = new BankStatementCSVParser();

        final Exporter exporter = new HtmlExporter();

        bankStatementAnalyzer.analyze("bank-data-simple.csv", statementParser, exporter);
    }

}
