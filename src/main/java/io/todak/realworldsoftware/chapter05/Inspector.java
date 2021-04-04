package io.todak.realworldsoftware.chapter05;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Inspector {

    private final List<ConditionalAction> conditionalActionList;

    public Inspector(final ConditionalAction... conditionalActions) {
        this.conditionalActionList = Arrays.asList(conditionalActions);
    }

    public List<Report> inspect(final Facts facts) {
        final List<Report> reports = new ArrayList<>();
        for (ConditionalAction conditionalAction : conditionalActionList) {
            final boolean conditionResult = conditionalAction.evaluate(facts);
            reports.add(new Report(facts, conditionalAction, conditionResult));
        }
        return reports;
    }

}
