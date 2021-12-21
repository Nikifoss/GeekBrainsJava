package Lesson01.Obstacles;

import Lesson01.Object.Objects;

public class Treadmill implements Obstacles{
    private int distance;

    public Treadmill(int distance) {
        this.distance = distance;
    }

    public boolean canRun(Objects o){
        int objectsRunLength = o.run();
        System.out.println("Беговая дорожка. Дистанция " + this.distance);
        System.out.println();
        return objectsRunLength >= this.distance;
    }


}
