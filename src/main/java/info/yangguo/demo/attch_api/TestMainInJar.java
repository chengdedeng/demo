package info.yangguo.demo.attch_api;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/6/5
 * Time:下午2:07
 * <p/>
 * Description:
 */
public class TestMainInJar {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(new TransClass().getNumber());
        int count = 0;
        while (true) {
            Thread.sleep(500);
            count++;
            int number = new TransClass().getNumber();
            System.out.println(number);
            if (3 == number || count >= 10) {
                break;
            }
        }
    }
}
