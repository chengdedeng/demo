package info.yangguo.demo.btrace;

import static com.sun.btrace.BTraceUtils.*;

import com.sun.btrace.annotations.*;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/6/4
 * Time:下午1:53
 * <p/>
 * Description:
 * 谁调用了execute方法？
 */

@BTrace
public class MethodCallerTracer {
    @OnMethod(
            clazz = "info.yangguo.demo.btrace.Adder",
            method = "execute"
    )
    public static void traceExecute() {
        println("Adder.execute调用者为:");
        jstack();
    }
}
