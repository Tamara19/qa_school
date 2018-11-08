package ru.lesson5;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static ru.lesson5.Calculation.*;

public class TestCalculation {

    @Test
    public void testSquareRoot(){
        double result = squareRoot(100);
        assertEquals("Wrong value of square root!", result, 10, 0);
    }

    @Test
    public void testSquareNumber(){
        double result = squareNumber(1);
        assertEquals("Wrong value of square number!", result, 1, 0);
    }

    @Test
    public void testCosNumber(){
        double result = cosNumber(0);
        assertEquals("Wrong value of cosine number!", result, 1, 0);
    }

    @Test
    public void testFactorialOne(){
        double result = factorialNoRecursion(3);
        assertEquals("Wrong value of non-recursion factorial!", result, 6, 0);
    }

    @Test
    public void testFactorialTwo(){
        double result = factorialRecursion(1);
        assertEquals("Wrong value of recursion factorial!", result, 1, 0);
    }
}
