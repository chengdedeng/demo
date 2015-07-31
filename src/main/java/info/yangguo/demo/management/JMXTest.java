package info.yangguo.demo.management;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import org.junit.Test;

import javax.management.MBeanServer;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/7/23
 * Time:上午10:43
 * <p/>
 * Description:
 * <p/>
 * JMX测试
 */
public class JMXTest {
    /**
     * MBServer和JMX链接端位于同一个进程
     *
     * @throws IOException
     */
    @Test
    public void test1() throws IOException {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        RuntimeMXBean rmxb = ManagementFactory.newPlatformMXBeanProxy(server,
                "java.lang:type=Runtime", RuntimeMXBean.class);
    }

    /**
     * MBServer和JMX Client端不在统一进程甚至不在同一台服务器，可以通过如下方式打开MBServer的reomte功能，
     * <p/>
     * -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=127.0.0.1:8000 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false
     */
    @Test
    public void test2() throws IOException {
        JMXServiceURL url = new JMXServiceURL(
                "service:jmx:rmi:///jndi/rmi://127.0.0.1:8000/jmxrmi");
        JMXConnector connector = JMXConnectorFactory.connect(url);
        RuntimeMXBean rmxb = ManagementFactory.newPlatformMXBeanProxy(connector
                        .getMBeanServerConnection(), "java.lang:type=Runtime",
                RuntimeMXBean.class);
    }

    /**
     * 但是我们很多时候，在服务启动时并没有打开MBServer的agent功能，那我们可以使用attach操作来实现动态开启，作为test2的一个补充。
     *
     * @throws IOException
     * @throws AgentLoadException
     * @throws AgentInitializationException
     * @throws AttachNotSupportedException
     */
    public void test3() throws IOException, AgentLoadException, AgentInitializationException, AttachNotSupportedException {
        //Attach 到5656的JVM进程上，后续Attach API再讲解
        VirtualMachine virtualmachine = VirtualMachine.attach("5656");

        //让JVM加载jmx Agent，后续讲到Java Instrutment再讲解
        String javaHome = virtualmachine.getSystemProperties().getProperty("java.home");
        String jmxAgent = javaHome + File.separator + "lib" + File.separator + "management-agent.jar";
        virtualmachine.loadAgent(jmxAgent, "com.sun.management.jmxremote");

        //获得连接地址
        Properties properties = virtualmachine.getAgentProperties();
        String address = (String) properties.get("com.sun.management.jmxremote.localConnectorAddress");

        //Detach
        virtualmachine.detach();

        JMXServiceURL url = new JMXServiceURL(address);
        JMXConnector connector = JMXConnectorFactory.connect(url);
        RuntimeMXBean rmxb = ManagementFactory.newPlatformMXBeanProxy(connector
                .getMBeanServerConnection(), "java.lang:type=Runtime", RuntimeMXBean.class);
    }
}
