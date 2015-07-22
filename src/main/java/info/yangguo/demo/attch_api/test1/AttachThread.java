package info.yangguo.demo.attch_api.test1;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/6/29
 * Time:下午10:06
 * <p/>
 * Description:
 */

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.util.List;

// 一个运行 Attach API 的线程子类
public class AttachThread extends Thread {
    private final List<VirtualMachineDescriptor> listBefore;

    private final String jar;

    AttachThread(String attachJar, List<VirtualMachineDescriptor> vms) {
        listBefore = vms;  // 记录程序启动时的 VM 集合
        jar = attachJar;
    }

    public void run() {
        VirtualMachine vm = null;
        List<VirtualMachineDescriptor> listAfter = null;
        try {
            int count = 0;
            while (true) {
                listAfter = VirtualMachine.list();
                for (VirtualMachineDescriptor vmd : listAfter) {
                    if (vmd.displayName().contains("TestMainInJar")) {
                        // 如果 VM 有增加，我们就认为是被监控的 VM 启动了
                        // 这时，我们开始监控这个 VM
                        vm = VirtualMachine.attach(vmd);
                        break;
                    }
                }
                Thread.sleep(5000);
                count++;
                if (null != vm || count >= 10) {
                    break;
                }
            }
            vm.loadAgent(jar);
            vm.detach();
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new AttachThread("/Users/yangguo/work/code/demo/target/libs/demo-1.0-SNAPSHOT-fat.jar", VirtualMachine.list()).start();
//        new AttachThread("/Users/yangguo/Downloads/demo/target/libs/demo-1.0-SNAPSHOT-fat.jar", VirtualMachine.list()).start();
        System.out.println("--------");
        Thread.sleep(10000000);
    }
}
