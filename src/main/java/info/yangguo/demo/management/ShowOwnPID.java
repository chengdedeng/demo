package info.yangguo.demo.management;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/7/20
 * Time:下午10:19
 * <p/>
 * Description:
 *
 * 获取当前进程的pid
 */

import org.junit.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class ShowOwnPID {
    @Test
    public void getPid1() {
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        String name = runtime.getName();
        int pid = Integer.parseInt(name.substring(0, name.indexOf('@')));
        System.out.println("pid:" + pid);
    }
}
