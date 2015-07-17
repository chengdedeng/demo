package info.yangguo.demo.attch_api.test4;


import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class Agent {
    public static void agentmain(String agentArguments, Instrumentation instrumentation) throws UnmodifiableClassException {
        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
        System.out.println("Runtime:" + runtimeMxBean.getName() + " : " + runtimeMxBean.getInputArguments());
        System.out.println("Starting agent with arguments " + agentArguments);

        instrumentation.addTransformer(new Transformer(),true);
        instrumentation.retransformClasses(TestExample.class);
    }
}