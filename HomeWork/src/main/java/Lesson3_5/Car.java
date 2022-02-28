package Lesson3_5;

import java.util.concurrent.CyclicBarrier;

public class Car {
    private static int CARS_COUNT;
    private int speed;
    private String name;
    private Race race;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    public Runnable preparation() {
        return () -> {
            try {
                System.out.println(this.name + " готовится");
                Thread.sleep(500 + (int) (Math.random() * 800));
                System.out.println(this.name + " готов");
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    public Runnable startRace() {
        return () -> {
            for (int i = 0; i < race.getStages().size(); i++) {
                race.getStages().get(i).go(this);
            }
        };
    }
}


