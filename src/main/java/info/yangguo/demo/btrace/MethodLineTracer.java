package info.yangguo.demo.btrace;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/6/4
 * Time:下午1:54
 * <p/>
 * Description:
 * 有时候富有争议的一行代码不知道被谁调用，可以使用下面的代码来定位。
 */

import static com.sun.btrace.BTraceUtils.*;

import com.sun.btrace.annotations.*;

@BTrace
public class MethodLineTracer {
    @OnMethod(
            clazz = "info.yangguo.demo.btrace.Adder",
            location = @Location(value = Kind.LINE, line = 15)
    )
    public static void traceExecute(@ProbeClassName String pcn, @ProbeMethodName String pmn, int line) {
        println(strcat(strcat(strcat("调用者为: ", pcn), "."), pmn));
    }
}
