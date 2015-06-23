package info.yangguo.demo.btrace;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.Profiler;
import com.sun.btrace.annotations.*;

/**
 * * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/6/17
 * Time:下午4:52
 * <p/>
 * Description:
 * <p/>
 * This script demonstrates new capabilities built into BTrace 1.2
 * <ol>
 * <li>Shortened syntax - when omitting "public" identifier in the class
 * definition one can safely omit all other modifiers when declaring methods
 * and variables</li>
 * <li>Extended syntax for <b>@ProbeMethodName</b> annotation - you can use
 * <b>fqn</b> parameter to request a fully qualified method name instead of
 * the short one</li>
 * <li>Profiling support - you can use {@linkplain Profiler} instance to gather
 * performance data with the smallest overhead possible
 * </ol>
 *
 * @since 1.2
 */
@BTrace
class Profiling {
    @Property
    Profiler profiler = BTraceUtils.Profiling.newProfiler();

    @OnMethod(clazz = "/info\\.yangguo\\..*/", method = "/.*/")
    void entry(@ProbeMethodName(fqn = true) String probeMethod) {
        BTraceUtils.Profiling.recordEntry(profiler, probeMethod);
    }

    @OnMethod(clazz = "/info\\.yangguo\\..*/", method = "/.*/", location = @Location(value = Kind.RETURN))
    void exit(@ProbeMethodName(fqn = true) String probeMethod, @Duration long duration) {
        BTraceUtils.Profiling.recordExit(profiler, probeMethod, duration);
    }

    @OnTimer(5000)
    void timer() {
        BTraceUtils.Profiling.printSnapshot("Performance profile", profiler);
    }
}
