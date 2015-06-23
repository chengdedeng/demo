package info.yangguo.demo.net;

import java.net.InetAddress;
import java.util.Date;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/5/8
 * Time:上午10:42
 * <p/>
 * Description:
 */
public class Ip {
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 50; i++) {
            long begin = new Date().getTime();
            InetAddress inetAddress = InetAddress.getByName("www.baidu.com");
            System.out.println(inetAddress.getHostAddress());
            long end = new Date().getTime();
            System.out.println(end - begin);
            Thread.sleep(2000);
        }
    }
}
