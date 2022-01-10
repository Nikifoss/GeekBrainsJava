package Lesson05;

import java.util.Arrays;

public class HomeWork05 {

    private static final int size = 10000000;
    private static final int h = size / 2;

    public static void main(String[] args) {

        createArrAndWork1();
        createArrAndWork2();

    }

    private static void createArrAndWork1(){
        float[] arr = new float[size];
        fillingTheArray(arr); // Заполнение массива единицами
        long a = System.currentTimeMillis();
        mathArr(arr); // Проведение расчета по формуле
        System.out.println("Решение в одном потоке заняло: " + (System.currentTimeMillis() - a) + " милисекунд.");
    }

    private static void createArrAndWork2(){
        float[] arr = new float[size];
        fillingTheArray(arr); // Заполнение массива единицами
        long a = System.currentTimeMillis();

        try {
            runMathWithThreads(arr); // Проведение расчета по формуле с разбиением массива в 2 потока
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Решение в двух потоках заняло: " + (System.currentTimeMillis() - a) + " милисекунд.");
    }

    private static void fillingTheArray(float[] arr){
        Arrays.fill(arr, 1.0f);
    }

    private synchronized static void mathArr(float[]arr){
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }

    private static void runMathWithThreads(float[]arr) throws InterruptedException {
        float[] a1 = new float[h];
        float[] a2 = new float[h];

        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);

        Thread thread1 = new Thread(() -> mathArr(a1));
        Thread thread2 = new Thread(() -> mathArr(a2));

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);
    }
}


