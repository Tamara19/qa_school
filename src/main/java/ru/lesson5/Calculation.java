package ru.lesson5;

public abstract class Calculation {

    public static double squareRoot(double num){
        return Math.sqrt(num);
    }

    public static double squareNumber(double count){
        return Math.pow(count, 2);
    }

    public static double cosNumber(double number){
        return Math.cos(number);
    }

    //нерекурсивный метод
    public static int factorialNoRecursion(int i){
        int fact = 1;
        for ( ; i > 0; fact *= i--);
        return fact;
    }

    //рекурсивный метод
    public static int factorialRecursion(int n){
        int fact;
        if (n == 1) return 1;
        fact = factorialRecursion(n - 1) * n;
        return fact;
    }

}
