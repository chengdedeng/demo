package info.yangguo.demo.btrace;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/6/7
 * Time:下午1:13
 * <p/>
 * Description:
 * 探测某个包路径下的方法执行时间是否超过某个阈值的程序，如果超过了该阀值，则打印当前线程的栈信息。
 */

import static com.sun.btrace.BTraceUtils.*;

import com.sun.btrace.annotations.*;

@BTrace
public class DurationTracer {
    @OnMethod(
            clazz = "/info\\.yangguo\\.demo\\..*/",
            method = "/.*/",
            location = @Location(Kind.RETURN)
    )
    public static void trace(@ProbeClassName String pcn,
                             @ProbeMethodName String pmn,
                             @Duration long duration) {
        //duration的单位是纳秒
        if (duration > 1000 * 1000 * 3) {
            print(Strings.strcat(Strings.strcat(pcn, "."), pmn));
            print(" 耗时:");
            print(duration);
            println("纳秒,堆栈信息如下");
            jstack();
        }
    }
}

