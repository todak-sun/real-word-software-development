package io.todak.realworldsoftware.chapter05;

public class Main {

    public static void main(String[] args) {
        var env = new Facts();
        env.addFact("name", "Bob");
        env.addFact("jobTitle", "CEO");

        final var businessRuleEngine = new BusinessRuleEngine(env);

        final Rule ruleSendEmailToSalesWhenCEO = RuleBuilder.when(facts -> "CEO".equals(facts.getFact("jobTitle")))
                .then(facts -> {
                    var name = facts.getFact("name");
                    System.out.println("Relevant customer!!!! : " + name);
                });

        businessRuleEngine.addRule(ruleSendEmailToSalesWhenCEO);
        businessRuleEngine.run();
    }

}
