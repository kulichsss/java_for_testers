package ru.stqa.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SquareAndPerimeterTests {
    @Test
    void canCalculatePerimeter() {
        Assertions.assertEquals(12, new Triangle(3, 4, 5).perimeter());
    }
    @Test
    void canCalculateSquare() {
        Assertions.assertEquals(6, new Triangle(3, 4, 5).square());
    }

    @Test
    void cannotCreateTriangleWithNegativeSide() {
        try {
            new Triangle(-1,2,3);
            Assertions.fail();
        } catch (IllegalArgumentException exception) {
            System.out.println("Rectangle side A - should be not-negative");
        }

        try {
            new Triangle(1, -1, 3);
            Assertions.fail();
        } catch (IllegalArgumentException exception) {
            System.out.println("Rectangle side B - should be not-negative");
        }

        try {
            new Triangle(2, 3, -4);
            Assertions.fail();
        } catch (IllegalArgumentException exception) {
            System.out.println("Rectangle side C - should be not-negative");
        }
    }


    @Test
    void triangleInequalityIsViolated() {
        try {
            new Triangle( 8, 3, 4);
            Assertions.fail();
        } catch (IllegalArgumentException exception) {
            System.out.println("The sum of the two sides should not exceed side A");
        }

        try {
            new Triangle( 2, 6, 3);
            Assertions.fail();
        } catch (IllegalArgumentException exception) {
            System.out.println("The sum of the two sides should not exceed side B");
        }

        try {
            new Triangle( 2, 3, 6);
            Assertions.fail();
        } catch (IllegalArgumentException exception) {
            System.out.println("The sum of the two sides should not exceed side C");
        }
    }
}
