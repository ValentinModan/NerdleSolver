package org.nerdle;

import net.objecthunter.exp4j.ExpressionBuilder;

public class NerdleProcessor {
    public static final char EQUALS = '=';

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
}
