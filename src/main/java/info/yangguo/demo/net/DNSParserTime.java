package info.yangguo.demo.net;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/6/18
 * Time:下午2:55
 * <p/>
 * Description:
 */
public class DNSParserTime {
    public static void main(String args[]) throws UnknownHostException, InterruptedException {
        java.lang.System.setProperty("java.net.preferIPv4Stack", "true");
        java.lang.System.setProperty("java.net.preferIPv6Addresses", "false");
        for (int i = 0; i < 10; i++) {
            String host = "agibe.travelsky.com";

            long begin1 = new Date().getTime();
            Inet4Address.getByName(host);
            long end1 = new Date().getTime();
            System.out.println(end1 - begin1);


            long begin2 = new Date().getTime();
            InetAddress.getAllByName(host);
            long end2 = new Date().getTime();
            System.out.println(end2 - begin2);

            System.out.println("----");
            Thread.sleep(1000 * 5);
        }
    }
}
