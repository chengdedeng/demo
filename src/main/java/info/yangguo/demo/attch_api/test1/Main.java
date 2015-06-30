package info.yangguo.demo.attch_api.test1;

import com.sun.tools.attach.VirtualMachine;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/6/29
 * Time:下午2:33
 * <p/>
 * Description:
 */
public class Main {
    public static void main(String[] args) throws Exception {
        VirtualMachine vm = null;
        String agentjarpath = "/Users/yangguo/work/code/demo/target/libs/demo-1.0-SNAPSHOT-fat.jar"; //agentjar路径
        vm = VirtualMachine.attach("19461");//目标JVM的进程ID（PID）
        vm.loadAgent(agentjarpath, "This is Args to the Agent.");
        vm.detach();

        Thread.sleep(1000*60*60);
    }
}
