package Lesson3_5;

import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.*;


public class MainClass {
    public static final int CARS_COUNT = 4;
    private static Race race;
    private static final Vector<Car> cars = new Vector<>();
    private static ExecutorService executorService = Executors.newFixedThreadPool(CARS_COUNT);
    public static ArrayList<String> finalist = new ArrayList<>();

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка началась!!!");
        createNewRace();
        createNewCars();
        startPreparation();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка закончилась!!!");
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        startRace();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        executorService.shutdown();
        System.out.println("В гонке побеждает :" + finalist.get(0));
    }

    private static void startRace() throws InterruptedException {
        Vector<Callable<Object>> tasks = new Vector<>();

        for (Car car : cars) {
            Runnable task = car.startRace();
            tasks.add(Executors.callable(task));
        }
        executorService.invokeAll(tasks);
    }

    private static void startPreparation() throws BrokenBarrierException, InterruptedException {
        Vector<Callable<Object>> tasks = new Vector<>();

        for (Car car : cars) {
            Runnable task = car.preparation();
            tasks.add(Executors.callable(task));

        }
        executorService.invokeAll(tasks);
    }

    private static void createNewCars() {
        for (int i = 0; i < CARS_COUNT; i++) {
            cars.add(new Car(race, 20 + (int) (Math.random() * 10)));
        }
    }

    private static void createNewRace() {
        race = new Race(new Road(60), new Tunnel(CARS_COUNT), new LastRoad(40));
    }
}



