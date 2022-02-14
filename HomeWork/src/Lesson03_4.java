import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Lesson03_4 {
    private static final Object mon = new Object();
    private static volatile char currentLetter = 'A';

    public static void main(String[] args) {

        ExecutorService fixedService = Executors.newFixedThreadPool(3);


        fixedService.execute(Lesson03_4::printA);
        fixedService.execute(Lesson03_4::printB);
        fixedService.execute(Lesson03_4::printC);
        /*Test*/

    }

    public static void printA() {
        synchronized (mon) {
            for (int i = 0; i < 5; i++) {
                while (currentLetter != 'A') {
                    try {
                        mon.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print("A");
                currentLetter = 'B';
                mon.notifyAll();
            }
        }
    }

    public static void printB() {
        synchronized (mon) {
            for (int i = 0; i < 5; i++) {
                while (currentLetter != 'B') {
                    try {
                        mon.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print("B");
                currentLetter = 'C';
                mon.notifyAll();
            }
        }
    }

    public static void printC() {
        synchronized (mon) {
            for (int i = 0; i < 5; i++) {
                while (currentLetter != 'C') {
                    try {
                        mon.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print("C");
                currentLetter = 'A';
                mon.notifyAll();
            }
        }
    }
}



