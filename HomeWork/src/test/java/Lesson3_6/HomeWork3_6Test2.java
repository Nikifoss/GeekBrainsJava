package Lesson3_6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

class HomeWork3_6Test2 {
    private HomeWork3_6_2 homeWork3_6_2;

    @BeforeEach
    void setUp() {
        homeWork3_6_2 = new HomeWork3_6_2();
    }

    @Test
    void testArr1() {
        int[] resultArr = new int[]{1,7};
        int[] testArr = new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7};

        Assertions.assertArrayEquals(resultArr, HomeWork3_6.workWithArray(testArr));
    }

    @ParameterizedTest
    @MethodSource ("dataForAddOperation")
    public void testArray(int[] arr, boolean result) {
        Assertions.assertEquals(HomeWork3_6.checkArr(arr), result);
    }

    public static Stream<Arguments> dataForAddOperation() {
        List<Arguments> out = new ArrayList<>();
        out.add(Arguments.arguments(new int[] {1,1,1,4,4,1,4,4}, true));
        out.add(Arguments.arguments(new int[] {1,1,1,1,1,1}, false));
        out.add(Arguments.arguments(new int[] {4,4,4,4}, false));
        out.add(Arguments.arguments(new int[] {1,4,4,1,1,4,3}, false));
        return out.stream();
    }
}