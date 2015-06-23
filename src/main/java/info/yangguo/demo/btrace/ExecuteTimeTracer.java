package info.yangguo.demo.btrace;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/6/4
 * Time:下午1:52
 * <p/>
 * Description:
 * 追踪某个方法的执行时间，实现原理同AOP一样。
 */

import static com.sun.btrace.BTraceUtils.*;

import com.sun.btrace.annotations.*;

@BTrace
public class ExecuteTimeTracer {
    @TLS
    static long beginTime;

    @OnMethod(clazz = "info.yangguo.demo.btrace.Adder", method = "execute")
    public static void traceExecuteBegin() {
        beginTime = timeMillis();
    }

    @OnMethod(
            clazz = "info.yangguo.demo.btrace.Adder",
            method = "execute",
            location = @Location(Kind.RETURN)
    )
    public static void traceExecute(int arg1, int arg2, @Return boolean result) {
        println(strcat(strcat("Adder.execute 耗时:", str(timeMillis() - beginTime)), "ms"));
        println(strcat("返回结果为:", str(result)));
    }
}
