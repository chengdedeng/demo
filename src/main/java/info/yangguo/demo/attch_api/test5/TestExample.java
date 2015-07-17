package info.yangguo.demo.attch_api.test5;

import java.util.Random;

public class TestExample {
    private Random random = new Random();

    @Point
    public void doSleep() {
        try {
            Thread.sleep(10000);
            System.out.println("*");
        } catch (InterruptedException e) {
        }
    }

    @Point
    private void doTask() {
        try {
            Thread.sleep(random.nextInt(1000));
            System.out.println(".");
        } catch (InterruptedException e) {
        }
    }

    @Point
    public void doWork() {
        for (int i = 0; i < random.nextInt(10); i++) {
            doTask();
        }
    }

    public static void main(String[] args) {
        TestExample test = new TestExample();
        while (true) {
            test.doWork();
            test.doSleep();
        }
    }
}
