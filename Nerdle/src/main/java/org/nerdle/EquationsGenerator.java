package org.nerdle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.nerdle.NerdleProcessor.uniqueCharacters;

public class EquationsGenerator {

    private static final int EQUATION_LENGTH = 8;
    private static final String operatorsList = "+-*/";

    public static List<String> generateEquations() {
        return Stream.of(generateTwoElementEquations(), generateThreeElementEquations())
                .flatMap(Collection::stream)
                .filter(equation -> equation.length() == EQUATION_LENGTH)
                .filter(NerdleProcessor::isExpressionValid)
                .sorted((first,second)->  uniqueCharacters(second).compareTo(uniqueCharacters(first)))
                .collect(Collectors.toList());
    }

    private static List<String> generateTwoElementEquations() {
        List<String> equationList = new ArrayList<>();

        for (int i = 0; i <= 999; i++) {
            for (int j = 0; j <= 999; j++) {
                for (char operator : operatorsList.toCharArray()) {
                    try {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(i)
                                .append(operator)
                                .append(j);
                        String result = NerdleProcessor.computeEquation(stringBuilder.toString());
                        stringBuilder.append(NerdleProcessor.EQUALS)
                                .append(result);
                        equationList.add(stringBuilder.toString());
                    } catch (Exception e) {

                    }
                }
            }
        }
        return equationList;

    }

    private static List<String> generateThreeElementEquations() {
        List<String> equationList = new ArrayList<>();
        for (int i = 0; i <= 99; i++) {
            for (int j = 0; j <= 99; j++) {
                for (int k = 0; k <= 99; k++) {
                    for (char operator : operatorsList.toCharArray()) {
                        for (char secondOperator : operatorsList.toCharArray()) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(i)
                                    .append(operator)
                                    .append(j)
                                    .append(secondOperator)
                                    .append(k);
                            try {
                                String result = NerdleProcessor.computeEquation(stringBuilder.toString());


                                stringBuilder.append(NerdleProcessor.EQUALS)
                                        .append(result);
                                equationList.add(stringBuilder.toString());
                            }
                            catch (Exception e)
                            {

                        }
                        }
                    }
                }
            }
        }
        return equationList;
    }
}
