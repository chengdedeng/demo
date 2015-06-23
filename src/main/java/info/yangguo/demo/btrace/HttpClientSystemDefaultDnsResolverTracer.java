package info.yangguo.demo.btrace;

import com.sun.btrace.annotations.*;

import static com.sun.btrace.BTraceUtils.*;
import static com.sun.btrace.BTraceUtils.timeMillis;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/6/18
 * Time:下午2:32
 * <p/>
 * Description:
 */
@BTrace
public class HttpClientSystemDefaultDnsResolverTracer {
    @TLS
    static long beginTime;

    @OnMethod(
            clazz = "org.apache.http.impl.conn.SystemDefaultDnsResolver",
            method = "resolve"
    )
    public static void traceGetByNameBegin() {
        beginTime = timeMillis();
    }

    @OnMethod(
            clazz = "org.apache.http.impl.conn.SystemDefaultDnsResolver",
            method = "resolve",
            location = @Location(Kind.RETURN)
    )
    public static void traceGetByNameEnd() {
        println(strcat(strcat("org.apache.http.impl.conn.SystemDefaultDnsResolver.resolve 耗时:", str(timeMillis() - beginTime)), "ms"));
    }
}
