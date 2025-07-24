package ru.stqa.math;

public class Triangle {
    private static double a;
    private static double b;
    private static double c;

    public Triangle(double a, double b, double c){
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public static double perimeter() {
        return a + b + c;
    }

    public static double square() {
        double p = perimeter() / 2;
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }
}
