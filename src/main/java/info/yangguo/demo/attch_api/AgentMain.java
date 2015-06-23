package info.yangguo.demo.attch_api;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/6/5
 * Time:下午2:07
 * <p/>
 * Description:
 */
public class AgentMain {
    public static void agentmain(String agentArgs, Instrumentation inst)
            throws ClassNotFoundException, UnmodifiableClassException,
            InterruptedException {
        inst.addTransformer(new Transformer (), true);
        inst.retransformClasses(TransClass.class);
        System.out.println("Agent Main Done");
    }
}
