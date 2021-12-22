package Lesson01.Obstacles;

import Lesson01.Object.Objects;

public interface Obstacles {
    default boolean canJump(Objects objects){
        return true;
    }
    default boolean canRun(Objects objects){
        return true;
    }

}
