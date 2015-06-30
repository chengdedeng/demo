package info.yangguo.demo.attch_api.test1;

import java.lang.instrument.Instrumentation;
/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/6/29
 * Time:下午2:32
 * <p/>
 * Description:
 */
public class TestAgent {
    public static void agentmain(String args, Instrumentation inst)
            throws Exception {
        System.out.println("Args:" + args);
    }
}
