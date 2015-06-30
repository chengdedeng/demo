package info.yangguo.demo.attch_api.test1;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/6/29
 * Time:下午2:31
 * <p/>
 * Description:
 */
public class TestMain {
    public static void main(String[] args) throws InterruptedException {
        while(true){
            Thread.sleep(10000);
            new Thread(new WaitThread()).start();
        }
    }

    static class WaitThread implements Runnable {
        @Override
        public void run() {
            System.out.println("Hello");
        }
    }
}
