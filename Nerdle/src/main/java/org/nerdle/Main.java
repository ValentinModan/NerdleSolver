package org.nerdle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");
        try {
            extracted();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void extracted() throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        // Reading data using readLine

        List<String> equationsList = EquationsGenerator.generateEquations();
        System.out.println(equationsList);
        System.out.println(equationsList.stream().limit(10).collect(Collectors.toList()));
        String equation = reader.readLine();

        System.out.println(equationsList.size());

        while (!equation.isEmpty()) {
            String filter = reader.readLine();
            equationsList = NerdleProcessor.filterList(equationsList, equation, filter);
            System.out.println(equationsList.size());
            System.out.println(equationsList.stream().limit(10).collect(Collectors.toList()));
            equation = reader.readLine();
        }
        System.out.println(equationsList);
        System.out.println(equationsList.size());
    }

}