package org.nerdle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class NerdleProcessorTest {


    @ParameterizedTest
    @MethodSource("expressions")
    void isExpressionValid(String expression, boolean value) {
        Assertions.assertEquals(value, NerdleProcessor.isExpressionValid(expression));
    }

    private static Stream<Arguments> expressions() {
        return Stream.of(
                Arguments.of("9*8-7=65", true),
                Arguments.of("0+12/3=4", true),
                Arguments.of("47-5*9=2", true),
                Arguments.of("12/4+0=3", true),
                Arguments.of("17-3*4=5", true),
                Arguments.of("56/8+2=9", true),
                Arguments.of("9*8-765",false),
                Arguments.of("9*8-765=",false),
                Arguments.of("12+3=5",false),
                Arguments.of("13/3=4",false)
        );
    }
}