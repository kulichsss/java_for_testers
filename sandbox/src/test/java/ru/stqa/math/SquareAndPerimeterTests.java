package ru.stqa.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SquareAndPerimeterTests {

    @Test
    void canCalculatePerimeter() {
        var result = Triangle.perimeter(3, 4, 5);
        Assertions.assertEquals(12, result);
    }
    @Test
    void canCalculateSquare() {
        var result = Triangle.square(3, 4, 5);
        Assertions.assertEquals(6, result);
    }
}
