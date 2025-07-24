package ru.stqa.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SquareAndPerimeterTests {
    Triangle exampleTriangle = new Triangle(3, 4, 5);
    @Test
    void canCalculatePerimeter() {
        var result = exampleTriangle.perimeter();
        Assertions.assertEquals(12, result);
    }
    @Test
    void canCalculateSquare() {
        var result = exampleTriangle.square();
        Assertions.assertEquals(6, result);
    }
}
