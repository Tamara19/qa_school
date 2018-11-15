package ru.annotation;

public abstract class Shapes {

    public static double areaTriangle(double a, double b, double c) {
        double p = (a + b + c) / 2;
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

    public static double areaSquare(double x){
        return Math.pow(x, 2);
    }

    public static double areaCircle(double radius){
        return Math.PI * Math.pow(radius, 2);
    }

}
