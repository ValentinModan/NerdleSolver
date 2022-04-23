package org.nerdle;

import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.*;
import java.util.stream.Collectors;

public class NerdleProcessor {
    public static final char EQUALS = '=';
    public static final char PURPLE = 'p';
    public static final char ANY_CHAR = '.';

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
        List<String> resultList = equationList.stream()
                .filter(it -> isValidNew(it, lastString, filter))
                .collect(Collectors.toList());

        Map<Character,Double> frequencyMap = EquationsGenerator.generateFrequencyMap(resultList);
        return equationList.stream()
                .filter(it -> isValidNew(it, lastString, filter))
                .sorted(Comparator.comparing(s -> EquationsGenerator.computeDistance(s, frequencyMap)))
                .collect(Collectors.toList());
    }

    private static boolean isValidNew(String newString, String lastString, String filter) {
        if (!areGreensMarket(newString, lastString, filter)) {
            return false;
        }
        newString = filterGreen(newString, filter, GREEN);
        lastString = filterGreen(lastString, filter, GREEN);
        filter = filterGreen(filter, filter, GREEN);

        if (!arePurpleMarket(newString, lastString, filter)) {
            return false;
        }

        String purpleNewString = newString;
        String purpleLastString = lastString;
        String purpleFilter = filter;
        for (int i = 0; i < purpleFilter.length() && i < purpleNewString.length(); i++) {
            if (purpleFilter.charAt(i) == PURPLE) {
                char c = purpleLastString.charAt(i);
                if (purpleNewString.indexOf(c) == -1) {
                    return false;
                }
                purpleLastString = purpleLastString.replaceFirst(String.valueOf(c), "");
                purpleNewString = purpleNewString.replaceFirst(String.valueOf(c), "");
                purpleFilter = purpleFilter.replaceFirst("p", "");
                i--;
            }
        }

        for (int i = 0; i < purpleFilter.length() && i < purpleNewString.length(); i++) {
            if (purpleFilter.charAt(i) == ANY_CHAR) {
                char c = purpleLastString.charAt(i);
                if (purpleNewString.indexOf(c) != -1) {
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean areGreensMarket(String newString, String lastString, String filter) {
        for (int i = 0; i < newString.length(); i++) {
            if (filter.charAt(i) == GREEN) {
                if (newString.charAt(i) != lastString.charAt(i)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean arePurpleMarket(String newString, String lastString, String filter) {
        for (int i = 0; i < newString.length(); i++) {
            if (filter.charAt(i) == PURPLE) {
                if (newString.charAt(i) == lastString.charAt(i)) {
                    return false;
                }
                if (newString.indexOf(lastString.charAt(i)) == -1) {
                    return false;
                }
            }
        }
        return true;
    }

    private static String filterGreen(String input, String filter, char c) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            if (filter.charAt(i) != c) {
                stringBuilder.append(input.charAt(i));
            }
        }
        return stringBuilder.toString();
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

    public static String compute(String response, String input) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<Character, Integer> charMap = generateFrequencyHashMap(input);

        for (int i = 0; i < response.length(); i++) {
            char c = input.charAt(i);
            if (input.charAt(i) == response.charAt(i)) {
                stringBuilder.append(GREEN);
                if (charMap.containsKey(c) && charMap.get(c) != 0) {
                    charMap.put(c, charMap.get(c) - 1);
                }
                continue;
            }
            int index = response.indexOf(input.charAt(i));
            if (index == -1) {
                stringBuilder.append('.');
                continue;
            }
        }
        return stringBuilder.toString();
    }

    private static Map<Character, Integer> generateFrequencyHashMap(String input) {
        Map<Character, Integer> charMap = new HashMap<>();
        for (char c : input.toCharArray()) {
            if (charMap.containsKey(c)) {
                charMap.put(c, charMap.get(c) + 1);
            } else {
                charMap.put(c, 1);
            }
        }
        return charMap;
    }
}
