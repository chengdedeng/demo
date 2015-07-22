package info.yangguo.demo.attch_api.test1;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/6/30
 * Time:上午9:55
 * <p/>
 * Description:
 */
public class TestMainInJar {
    public static void main(String[] args) throws InterruptedException {
        while (true) {
            Thread.sleep(3000);
            int number = new TransClass().getNumber();
            System.out.println(number);
        }
    }
}
