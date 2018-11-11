package ru.annotation;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static ru.annotation.Shapes.*;

public class TestShapes {

    @Tag("simple")
    @Test
    @DisplayName("Тест площади треугольника с утверждением JUnit 5")
    public void areaTriangleTest() {
        double triangle = areaTriangle(8, 5, 10);
        triangle = Math.round((triangle * 100)) / 100.0d;
        Assertions.assertEquals(19.81, triangle, "Wrong value!");
    }

    @Tag("simple")
    @Test
    @DisplayName("Тест площади квадрата с матчером библиотеки Hamcrest")
    public void areaSquareTest() {
        double square = areaSquare(2.15);
        square = Math.round((square * 100)) / 100.0d;
        assertThat(square, equalTo(4.62));
    }

    @Test
    @Disabled("Не запускать!")
    @DisplayName("Тест площади круга с матчером библиотеки Hamcrest")
    public void areaCircleTest() {
        double circle = areaCircle(10.5);
        circle = Math.round((circle * 100)) / 100.0d;
        assertThat(circle, equalTo(346.36));
    }

    @ParameterizedTest()
    @DisplayName("Параметризированный тест площади круга с утверждением JUnit 5")
    @ValueSource(doubles = {3.1, 27.9})
    public void areaSquareParameterTest(double param) {
        double result = areaCircle(param);
        result = Math.round((result * 100)) / 100.0d;
        Assertions.assertNotNull(result, "Wrong value!");
    }

    @ParameterizedTest(name = "{index} => a={0}, b={1}, c={2}, res={3}")
    @DisplayName("Параметризированный тест площади треугольника с утверждением JUnit 5")
    @MethodSource("triangleArguments")
    public void areaTriangleParameterTest(double a, double b, double c, double res) {
        double result = areaTriangle(a, b, c);
        result = Math.round((result * 100)) / 100.0d;
        Assertions.assertTrue(res == result, "Wrong value!");
    }

    private static Stream<Arguments> triangleArguments() {
        return Stream.of(
                Arguments.of(2.5, 6.7, 4.56, 3.55),
                Arguments.of(6.5, 4.2, 10.0, 9.26));
    }

}
