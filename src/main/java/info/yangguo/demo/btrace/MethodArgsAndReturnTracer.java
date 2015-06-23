package info.yangguo.demo.btrace;

import static com.sun.btrace.BTraceUtils.*;

import com.sun.btrace.annotations.*;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/6/4
 * Time:下午1:44
 * <p/>
 * Description:
 * 调用此方法时传入的是什么参数，返回的是什么值，当时sleepTotalTime是多少？
 */


@BTrace
public class MethodArgsAndReturnTracer {
    @OnMethod(
            clazz = "info.yangguo.demo.btrace.Adder",
            method = "execute",
            location = @Location(Kind.RETURN)
    )
    public static void traceExecute(@Self Adder instance, int arg1,int arg2, @Return int result) {
        println("Adder.execute");
        println(strcat("arg1 is:", str(arg1)));
        println(strcat("arg2 is:", str(arg2)));
        println(strcat("maxResult is:", str(get(field("info.yangguo.demo.btrace.Adder", "maxResult"), instance))));
        println(strcat("return value is:", str(result)));
    }
}
