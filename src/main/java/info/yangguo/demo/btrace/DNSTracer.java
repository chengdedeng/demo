package info.yangguo.demo.btrace;

import com.sun.btrace.annotations.*;

import static com.sun.btrace.BTraceUtils.*;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/6/9
 * Time:下午5:53
 * <p/>
 * Description:
 */
@BTrace
public class DNSTracer {
    @TLS
    static long beginTime;
    @OnMethod(
            clazz = "java.net.InetAddress",
            method = "getByName"
    )
    public static void traceGetByNameBegin(){
        beginTime = timeMillis();
    }

    @OnMethod(
            clazz = "java.net.InetAddress",
            method = "getByName",
            location = @Location(Kind.RETURN)
    )
    public static void traceGetByNameEnd(){
        println(strcat(strcat("java.net.InetAddress.getByName 耗时:", str(timeMillis() - beginTime)), "ms"));
    }
}
