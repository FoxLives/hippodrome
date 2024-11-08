import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import org.mockito.MockedStatic;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;


class HorseTest {
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "          "})
    void constructorTest(String horseName) {
        try {
            new Horse(null, 0.0);
        } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be null.", e.getMessage());
        }
        try {
            new Horse(horseName, 0.0);
        } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be blank.", e.getMessage());
        }
        try {
            new Horse("Migera", -1d);
        } catch (IllegalArgumentException e) {
            assertEquals("Speed cannot be negative.", e.getMessage());
        }
        try {
            new Horse("Migera", 1, -1d);
        } catch (IllegalArgumentException e) {
            assertEquals("Distance cannot be negative.", e.getMessage());
        }
    }

    @Test
    void getName() {
        assertEquals("Migera", new Horse("Migera", 1, 1).getName());
    }

    @Test
    void getSpeed() {
        assertEquals(123, new Horse("Migera", 123, 1).getSpeed());
    }

    @Test
    void getDistance() {
        assertEquals(123, new Horse("Migera", 1, 123).getDistance());
    }

    @ParameterizedTest
    @CsvSource({
            "10.0, 5.0, 0.5, 12.5",
            "15.0, 4.0, 0.8, 18.2"
    })
    void testMove(double initialDistance, double speed, double randomValue, double expectedDistance) {
        Horse horse = new Horse("TestHorse", speed, initialDistance);

        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomValue);

            horse.move();

            // Проверка расчёта дистанции
            assertEquals(expectedDistance, horse.getDistance());

            // Проверка вызова метода getRandomDouble
            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }
}