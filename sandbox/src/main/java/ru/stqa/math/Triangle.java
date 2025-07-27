package ru.stqa.math;

public class Triangle {
    private double a;
    private double b;
    private double c;

    public Triangle(double a, double b, double c){
        this.a = a;
        this.b = b;
        this.c = c;
        
        if (a < 0 || b < 0 || c < 0) {
            throw new IllegalArgumentException("Rectangle side should be not-negative");
        }

        if (a > b + c || b > a + c || c > a + b) {
            throw new IllegalArgumentException("The sum of any two sides must be at least as long as the third side");
        }
    }

    public double perimeter() {
        return a + b + c;
    }

    public double square() {
        double p = perimeter() / 2;
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }
}
