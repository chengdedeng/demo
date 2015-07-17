package info.yangguo.demo.jvm.Jvmstat;

import sun.jvmstat.monitor.HostIdentifier;
import sun.jvmstat.monitor.MonitoredHost;
import sun.jvmstat.monitor.MonitoredVm;
import sun.jvmstat.monitor.VmIdentifier;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/7/15
 * Time:下午5:07
 * <p/>
 * Description:
 */
public class Test {
    public static void main(String[] args)  throws Exception {
        HostIdentifier hostId = new HostIdentifier((String) null);
        MonitoredHost monitoredHost = MonitoredHost.getMonitoredHost(hostId);
        String uriString = "//" + String.valueOf(8317) + "?mode=r"; // NOI18N
        VmIdentifier id = new VmIdentifier(uriString);
        MonitoredVm vm = monitoredHost.getMonitoredVm(id, 0);
        List list=vm.findByPattern("java");
        System.out.println("---");
    }
}
