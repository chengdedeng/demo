package info.yangguo.demo.net;


import java.lang.reflect.Field;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/5/7
 * Time:下午5:10
 * <p/>
 * Description:
 * <p/>
 * dns缓存时间测试
 */
public class DNSCacheTime {
    public static void main(String[] args) throws Exception {
        System.out.println("start loop\n\n");
        for (int i = 0; i < 30; ++i) {
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("current time:" + sdf.format(d));
            InetAddress addr1 = InetAddress.getByName("www.baidu.com");
            String addressCache = "addressCache";
            System.out.println(addressCache);
            printDNSCache(addressCache);
            System.out.println("getHostAddress:" + addr1.getHostAddress());
            System.out.println("*******************************************");
            System.out.println("\n");
            java.lang.Thread.sleep(10000);
        }

        System.out.println("end loop");
    }


    private static void printDNSCache(String cacheName) throws Exception {
        Class<InetAddress> klass = InetAddress.class;
        Field acf = klass.getDeclaredField(cacheName);
        acf.setAccessible(true);
        Object addressCache = acf.get(null);
        Class cacheKlass = addressCache.getClass();
        Field cf = cacheKlass.getDeclaredField("cache");
        cf.setAccessible(true);
        Map<String, Object> cache = (Map<String, Object>) cf.get(addressCache);
        for (Map.Entry<String, Object> hi : cache.entrySet()) {
            Object cacheEntry = hi.getValue();
            Class cacheEntryKlass = cacheEntry.getClass();
            Field expf = cacheEntryKlass.getDeclaredField("expiration");
            expf.setAccessible(true);
            long expires = (Long) expf.get(cacheEntry);

            System.out.println(hi.getKey() + " " + new Date(expires) + " " + hi.getValue());
        }
    }
}
