package Lesson01;

import Lesson01.Object.Objects;
import Lesson01.Object.Cat;
import Lesson01.Object.Human;
import Lesson01.Object.Robot;
import Lesson01.Obstacles.Obstacles;
import Lesson01.Obstacles.Treadmill;
import Lesson01.Obstacles.Wall;

public class HomeWork01 {
    public static void main(String[] args) {

        Obstacles[] obstacle = new Obstacles[4];
        obstacle[0] = new Wall(1);
        obstacle[1] = new Treadmill(1000);
        obstacle[2] = new Wall(2);
        obstacle[3] = new Treadmill(2000);

        Objects[] object = new Objects[3];
        object[0] = new Robot("WALL·E",10000,1);
        object[1] = new Human("Oleg", 1000, 2);
        object[2] = new Cat("Barsik", 500, 1);

        for (int i = 0; i < object.length; i++) {
            System.out.println("=======================");
            System.out.println("Участник " + object[i].getName());
            for (int j = 0; j < obstacle.length; j++) {

                if (obstacle[j].canJump(object[i]) == false || obstacle[j].canRun(object[i]) == false){
                    System.out.println("Препятствие не пройдено");
                    break;
                }
            }

        }
    }

}

