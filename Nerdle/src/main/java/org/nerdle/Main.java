package org.nerdle;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        try {
            List<String> equationsList = EquationsGenerator.generateEquations();
            System.out.println(equationsList);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}