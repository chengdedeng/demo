package info.yangguo.demo.btrace;

import java.util.Random;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/6/4
 * Time:上午9:57
 * <p/>
 * Description:
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Random random = new Random();
        Adder adder = new Adder();
        while (true) {
            adder.execute(random.nextInt(1000), random.nextInt(1000));
            Thread.sleep(2000);
        }
    }
}