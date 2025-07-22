package ru.stqa.math;

public class Triangle {
    private double a;
    private double b;
    private double c;

    public Triangle(double a, double b, double c){
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public static double perimeter(double a, double b, double c) {
        return a + b + c;
    }

    public static double square(double a, double b, double c) {
        double p = perimeter(a, b, c) / 2;
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

    public static void main(String[] args) {
        System.out.println(square(3,4,5));
    }
}
