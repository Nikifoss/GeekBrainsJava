package Lesson01.Obstacles;

import Lesson01.Object.Objects;

public class Wall implements Obstacles{
    private int height;

    public Wall(int height) {
        this.height = height;
    }

    public boolean canJump(Objects o){
        int objectsJumpLength = o.jump();
        System.out.println("Стена. Высота " + this.height);
        System.out.println();
        return objectsJumpLength >= this.height;
    }
}
