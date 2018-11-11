package ru.lesson5;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.lesson5.Calculation.*;

public class TestCalculation {

    @Test
    public void testSquareRoot(){
        double result = squareRoot(100);
        assertEquals(10, result, "Wrong value of square root!");
    }

    @Test
    public void testSquareNumber(){
        double result = squareNumber(1);
        assertEquals(1, result, "Wrong value of square number!");
    }

    @Test
    public void testCosNumber(){
        double result = cosNumber(0);
        assertEquals(1, result, "Wrong value of cosine number!");
    }

    @Test
    public void testFactorialOne(){
        double result = factorialNoRecursion(3);
        assertEquals(6, result, "Wrong value of non-recursion factorial!");
    }

    @Test
    public void testFactorialTwo(){
        double result = factorialRecursion(1);
        assertEquals(1, result, "Wrong value of recursion factorial!");
    }
}
