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
}
