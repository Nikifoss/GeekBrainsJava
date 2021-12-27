package Lesson02;

public class HomeWork02 {

    public static void main(String[] args) {
        String arr[][] = {{"1", "1", "1", "1"},
                {"1", "1", "1", "1"},
                {"1", "1", "1", "1"},
                {"1", "1", "1", "1"}};

        try {
            System.out.println("Сумма чисел в массиве = " + arrayCheckAndSumUp(arr));
        } catch (CustomExceptions.MyArraySizeException e){
            System.out.println(e.getMessage());
        } catch (CustomExceptions.MyArrayDataException e){
            System.out.println(e.getMessage());
        }
    }

    private static int arrayCheckAndSumUp(String[][] array4x4) throws CustomExceptions.MyArrayDataException {
        int x = 0;
        int y = 0;
        int sum = 0;
        if (array4x4.length == 4 || array4x4[0].length == 4){
            try {
                for (int i = 0; i < array4x4.length; i++) {
                    for (int j = 0; j < array4x4[i].length; j++) {
                    x = i + 1;
                    y = j + 1;
                    int a = Integer.parseInt(array4x4[i][j]);
                    sum += a;
                    }
                }
            } catch (NumberFormatException e){
                throw new CustomExceptions.MyArrayDataException(x,y);
            }
            return sum;
        } else throw new CustomExceptions.MyArraySizeException();
    }

}
