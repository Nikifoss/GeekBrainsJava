package Lesson02;

public class CustomExceptions {
    public static class MyArraySizeException extends RuntimeException{
        public MyArraySizeException() {
            super("Неверный размер массива! Требуется массив 4х4.");
        }
    }
    public static class MyArrayDataException extends NumberFormatException{
        public MyArrayDataException(int x, int y) {
            super("В ячейке X = " + x + " Y = " + y + " лежат неверные данные!");
        }
    }
}
