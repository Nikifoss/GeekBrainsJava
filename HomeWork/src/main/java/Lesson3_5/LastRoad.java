package Lesson3_5;

public class LastRoad extends Stage {
    public LastRoad(int length) {
        this.length = length;
        this.description = "Финальная прямая " + length + " метров";
    }

    @Override
    public void go(Car c) {
        try {
            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000);
            System.out.println(c.getName() + " закончил этап: " + description);
            MainClass.finalist.add(c.getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
