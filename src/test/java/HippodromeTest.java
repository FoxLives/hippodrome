import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class HippodromeTest {
    @Test
    void constructorTest() {
        try {
            new Hippodrome(null);
        } catch (IllegalArgumentException e) {
            assertEquals("Horses cannot be null.", e.getMessage());
        }
        try {
            new Hippodrome(new ArrayList<>());
        } catch (IllegalArgumentException e) {
            assertEquals("Horses cannot be empty.", e.getMessage());
        }
    }

    @Test
    void getHorses() {
        List<Horse> horseList = randomListOfHorses(30);
        assertArrayEquals(horseList.toArray(), new Hippodrome(horseList).getHorses().toArray());
    }

    @Test
    void move() {
        List<Horse> horseList = new ArrayList<>();
        for (int i = 0; i < 50; i++)
            horseList.add(Mockito.mock(Horse.class));
        Hippodrome hippodrome = new Hippodrome(horseList);

        hippodrome.move();

        for (Horse horse : horseList)
            Mockito.verify(horse, Mockito.times(1)).move();
    }

    @Test
    void getWinner() {
        List<Horse> horseList = randomListOfHorses(10);
        Hippodrome hippodrome = new Hippodrome(horseList);

        Horse expectedWinner = horseList.stream()
                .max(Comparator.comparingDouble(Horse::getDistance))
                .orElseThrow();

        assertEquals(expectedWinner, hippodrome.getWinner());
    }

    private List<Horse> randomListOfHorses(int count) {
        List<Horse> horseList = new ArrayList<>();
        for (int i = 0; i < count; i++)
            horseList.add(new Horse(RandomString.make(10), Math.random(), (Math.random() + 1) * 10));
        return Collections.unmodifiableList(horseList);
    }
}