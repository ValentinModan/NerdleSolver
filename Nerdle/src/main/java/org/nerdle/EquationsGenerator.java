package org.nerdle;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.nerdle.NerdleProcessor.compute;
import static org.nerdle.NerdleProcessor.uniqueCharacters;

public class EquationsGenerator {
    private static final int EQUATION_LENGTH = 8;
    private static final String operatorsList = "+-*/";

    public static List<String> generateEquations() {
        List<String> resultList = Stream.of(generateTwoElementEquations(), generateThreeElementEquations())
                .flatMap(Collection::stream)
                .filter(equation -> equation.length() == EQUATION_LENGTH)
                .filter(NerdleProcessor::isExpressionValid)
                .sorted((first, second) -> uniqueCharacters(second).compareTo(uniqueCharacters(first)))
                .collect(Collectors.toList());

        Map<Character, Double> characterDoubleMap = generateFrequencyMap(resultList);
        System.out.println(characterDoubleMap);
        resultList = resultList.stream()
                .sorted(Comparator.comparing(s -> computeDistance(s, characterDoubleMap)))
                .collect(Collectors.toList());

        return resultList;
    }

    public static Double computeDistance(String word, Map<Character, Double> characterDoubleMap) {
        return word.codePoints()
                .distinct()
                .mapToDouble(c -> distance(countLetters(word, (char) c), characterDoubleMap.get((char) c)))
                .sum();
    }

    private static Double distance(Double count, Double average) {
        return Math.pow((count - average), 2);
    }

    public static Double countLetters(String word, char c) {
        int count = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == c) {
                count++;
            }
        }
        return (double) count;
    }

    public static Map<Character, Double> generateFrequencyMap(List<String> stringList) {
        Map<Character, Double> characterMap = new HashMap<>();

        for (String string : stringList) {
            for (char c : string.toCharArray()) {
                if (characterMap.containsKey(c)) {
                    characterMap.put(c, characterMap.get(c) + 1);
                } else {
                    characterMap.put(c, 1.0);
                }
            }
        }

        int length = stringList.size();
        for (char c : characterMap.keySet()) {
            if (characterMap.containsKey(c)) {
                characterMap.put(c, characterMap.get(c) / length);
            }
        }
        return characterMap;
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
                            } catch (Exception e) {

                            }
                        }
                    }
                }
            }
        }
        return equationList;
    }
}
