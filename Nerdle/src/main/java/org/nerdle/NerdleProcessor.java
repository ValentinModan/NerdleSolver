package org.nerdle;

import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class NerdleProcessor {
    public static final char EQUALS = '=';
    public static final char PURPLE = 'p';
    public static final char GREEN = 'g';

    public static boolean isExpressionValid(String s) {
        int equalIndex = s.indexOf(EQUALS);
        if (equalIndex == -1) {
            return false;
        }
        try {
            Double result = determineResult(s, equalIndex);
            Double expressionResult = computeLeftSideResult(s, equalIndex);

            if (expressionResult.equals(result)) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public static String computeEquation(String equation) {
        int result = Math.toIntExact(Math.round(computeLeftSideResult(equation, equation.length())));
        return Integer.toString(result);
    }

    public static Double computeLeftSideResult(String s, int equalIndex) {
        ExpressionBuilder expression = new ExpressionBuilder(s.substring(0, equalIndex));
        return expression.build().evaluate();
    }

    private static Double determineResult(String s, int equalIndex) {
        return Double.valueOf(s.substring(equalIndex + 1));
    }

    public static List<String> filterList(List<String> equationList, String lastString, String filter) {
        return equationList.stream()
                .filter(it -> isValid(it, lastString, filter))
                .collect(Collectors.toList());
    }

    private static boolean isValid(String newString, String lastString, String filter) {
        if (filter == null) {
            return true;
        }
        for (int i = 0; i < filter.length(); i++) {
            if (filter.charAt(i) == GREEN) {
                //must have same character at position
                if (lastString.charAt(i) != newString.charAt(i)) {
                    return false;
                }
            } else if (filter.charAt(i) == PURPLE) {
                //must have character at different position
                if (lastString.charAt(i) == newString.charAt(i)) {
                    return false;
                }
                if (newString.indexOf(lastString.charAt(i)) == -1) {
                    return false;
                }
            } else if (newString.indexOf(lastString.charAt(i)) != -1) {
                return false;
            }
        }
        return true;
    }

    public static Integer uniqueCharacters(String string) {
        return (int) string.chars().distinct().count();
    }

    private static List<Integer> allIndexes(String string, char c) {
        List<Integer> integerList = new ArrayList<>();
        int index = string.indexOf(c);
        while (index != -1) {
            integerList.add(index);

            string = string.substring(index + 1);
            index = string.indexOf(c);
        }
        return integerList;
    }
}
