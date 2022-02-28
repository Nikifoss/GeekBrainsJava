package Lesson3_6;

import java.util.ArrayList;
import java.util.List;

public class HomeWork3_6 {
    public static void main(String[] args) {

        int[] arr = new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7};

        try {
            workWithArray(arr);
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }

   protected static int[] workWithArray(int[] arr) throws RuntimeException {
        if (arr == null) {
            throw new RuntimeException("Массив пуст");
        } else {
            List<Integer> list = new ArrayList<>();
            for (int j : arr) {
                list.add(j);
            }
            if ((list.contains(4))) {
                List<Integer> subList = list.subList(list.lastIndexOf(4) + 1, list.size());
                int[] outArr = new int[subList.size()];
                for (int i = 0; i < subList.size(); i++) {
                    outArr[i] = subList.get(i);
                }
                return outArr;
            } else {
                throw new RuntimeException("Массив не содержит числа 4");
            }
        }
    }
    protected static boolean checkArr(int[] array) {
        int value1 = 1;
        int value2 = 4;
        boolean containsValue1 = false;
        boolean containsValue2 = false;

        for (int value : array) {
            if (value == value1)
                containsValue1 = true;
            else if (value == value2)
                containsValue2 = true;
            else
                return false;
        }

        return containsValue1 && containsValue2;
    }
}
