package info.yangguo.demo.btrace;

import com.sun.btrace.annotations.*;

import static com.sun.btrace.BTraceUtils.Threads.*;

/**
 * This BTrace program demonstrates deadlocks
 * built-in function. This example prints
 * deadlocks (if any) once every 4 seconds.
 */
@BTrace
public class Deadlock {
    @OnTimer(4000)
    public static void print() {
        deadlocks();
    }
}
