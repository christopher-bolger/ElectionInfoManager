package utility;

import electionInfoManager.model.linkedlist.LinkedList;

import electionInfoManager.utility.Sort;
import org.junit.jupiter.api.*;
import java.util.Comparator;
import static org.junit.jupiter.api.Assertions.*;

class SortTest {
    LinkedList<String> strings;
    @BeforeEach
    void setUp() {
        strings = new LinkedList<>();
        strings.add("B");
        strings.add("A");
        strings.add("C");
        strings.add("F");
        strings.add("D");
    }

    @AfterEach
    void tearDown() {
        strings = null;
    }

    @Nested
    class mergeSort {
        @Test
        void sortAscending() {
            int size = strings.size();
            Sort.mergeSort(strings, String::compareTo);
            assertEquals(size, strings.size());
            assertEquals("A", strings.getFirst());
            assertEquals("F", strings.getLast());
        }

        @Test
        void sortDescending() {
            int size = strings.size();
            Sort.mergeSort(strings, Comparator.reverseOrder());
            assertEquals(size, strings.size());
            assertEquals("F", strings.getFirst());
            assertEquals("A", strings.getLast());
        }
    }
}