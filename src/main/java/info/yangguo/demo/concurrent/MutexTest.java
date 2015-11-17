package info.yangguo.demo.concurrent;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/9/18
 * Time:下午4:05
 * <p/>
 * Description:
 */
public class MutexTest {
    final static Mutex mutex = new Mutex();

    public static class Worker implements Runnable {
        @Override
        public void run() {
            mutex.lock();
            try {
                Thread.sleep(1000 * 5);
                System.out.println(this.toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                mutex.unlock();
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new Worker()).start();
        new Thread(new Worker()).start();
    }
}
