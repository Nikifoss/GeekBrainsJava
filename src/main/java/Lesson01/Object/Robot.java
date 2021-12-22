package Lesson01.Object;

public class Robot implements Objects {
    private final String name;
    private final int maxDistance;
    private final int maxHeightJump;

    public Robot(String name, int maxDistance, int maxHeightJump) {
        this.name = name;
        this.maxDistance = maxDistance;
        this.maxHeightJump = maxHeightJump;
    }

    public String getName() {
        return name;
    }

    public int getMaxDistance() {
        return maxDistance;
    }

    public int getMaxHeightJump() {
        return maxHeightJump;
    }

    @Override
    public int run() {
        System.out.println(name + " бежит. Он может пробежать максимум " + maxDistance);
        return maxDistance;
    }

    @Override
    public int jump() {
        System.out.println(name + " прыгает. Он может перепрыгнуть максимум " + maxHeightJump);
        return maxHeightJump;
    }
}
