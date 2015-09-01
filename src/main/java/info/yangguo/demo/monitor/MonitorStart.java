package info.yangguo.demo.monitor;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/8/18
 * Time:下午5:35
 * <p/>
 * Description:
 */
public class MonitorStart {
    public static void main(String[] args) throws InterruptedException {
        ApplicationContext applicationContext = new FileSystemXmlApplicationContext("classpath:spring/metric.xml");
        MetricRegistry metricRegistry = (MetricRegistry) applicationContext.getBean("registry");
        gauges(metricRegistry);

        Thread.sleep(1000 * 60 * 60);
    }

    public static void gauges(MetricRegistry metricRegistry) {
        metricRegistry.register(MetricRegistry.name("test", "gauges", "size"), new Gauge<Integer>() {
            public Integer getValue() {
                double random = Math.random();
                int value = (int) (random * 100);
                System.out.println(value);
                return value;
            }
        });
    }
}
