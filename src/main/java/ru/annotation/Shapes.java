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

    public static void main(String[] args) {
        System.out.println(areaSquare(2.15));
        double d = areaTriangle(8, 5, 10);
        d = Math.round((d*100))/100.0d;
        System.out.println(Math.round((areaTriangle(2.5, 6.7, 4.56)*100))/100.0d);
        System.out.println(areaCircle(10.5));
    }
}
