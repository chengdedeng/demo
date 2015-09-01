package info.yangguo.demo.dropwizard.metrics

import com.codahale.metrics.Histogram
import com.codahale.metrics.Meter
import com.codahale.metrics.MetricFilter
import com.codahale.metrics.MetricRegistry
import com.codahale.metrics.graphite.Graphite
import com.codahale.metrics.graphite.GraphiteReporter
import com.codahale.metrics.graphite.PickledGraphite

import java.util.concurrent.TimeUnit

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/8/13
 * Time:上午11:22 
 *
 * Description:
 */
class GetStarted {
    static final MetricRegistry metricRegistry = new MetricRegistry();

    public static void main(String[] args) {
       test1()
    }

    def static test1 = {
        final Graphite graphite = new Graphite(new InetSocketAddress("graphite.yangguo.info", 2003));
        final GraphiteReporter reporter = GraphiteReporter.forRegistry(metricRegistry)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .filter(MetricFilter.ALL)
                .build(graphite);
        reporter.start(1, TimeUnit.SECONDS);
        Histogram histogram = metricRegistry.histogram("test");
        while (true) {
            def random = Math.random();
            int value = random * 100;
            println value
            histogram.update(value)
            Thread.sleep(1000 * 2)
        }
    }

    def static test2 = {
        final PickledGraphite pickledGraphite = new PickledGraphite(new InetSocketAddress("graphite.yangguo.info", 2004));
        final GraphiteReporter reporter = GraphiteReporter.forRegistry(metricRegistry)
                .prefixedWith("test")
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .filter(MetricFilter.ALL)
                .build(pickledGraphite);
        reporter.start(1, TimeUnit.MINUTES);
        Meter requests = metricRegistry.meter("count");
        while (true) {
            def random = Math.random();
            int value = random * 100;
            println value
            requests.mark(value)
            Thread.sleep(1000 * 2)
        }
    }



}
