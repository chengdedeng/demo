package info.yangguo.demo.btrace;

import com.sun.btrace.annotations.*;

import static com.sun.btrace.BTraceUtils.*;

import java.lang.management.MemoryUsage;

/**
 * This sample traces memory threshold exceeds.
 * You need to specify the memory pool to watch
 * out and the usage threshold. You can write
 * script that dumps heap by dumpHeap on crossing
 * the threshold instead of just printing a message.
 * Note that the name of the old gen is dependent on
 * GC algorithm. With ParallelGC, the name is "PS Old Gen".
 */
@BTrace
public class MemAlerter {
    @OnLowMemory(
            pool = "Tenured Gen",
            threshold = 6000000
    )
    public static void onLowMem(MemoryUsage mu) {
        println(mu);
    }
}
