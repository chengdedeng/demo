package info.yangguo.demo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:14/12/30
 * Time:下午2:15
 * <p/>
 * Description:
 */
public class TomcatUtil {
    private static final Logger LOG = LoggerFactory.getLogger(TomcatUtil.class);

    /**
     * 根据协议和scheme获取服务端口号
     *
     * @return 端口号
     */
    public static String getHttpPort(String pProtocol, String pScheme) {
        MBeanServer mBeanServer = null;
        if (MBeanServerFactory.findMBeanServer(null).size() > 0) {
            mBeanServer = (MBeanServer) MBeanServerFactory.findMBeanServer(null).get(0);
        }

        if (null == mBeanServer) {
            LOG.error("TomcatUtil: 未找到已注册的MBeanServer");
            return null;
        }

        Set<ObjectName> names = null;
        try {
            names = mBeanServer.queryNames(new ObjectName("Catalina:type=Connector,*"), null);
        } catch (Exception e) {
            LOG.error("TomcatUtil: 在MBeanServer中查找指定MBean失败", e);
            return null;
        }

        Iterator<ObjectName> it = names.iterator();
        ObjectName oname = null;
        while (it.hasNext()) {
            oname = (ObjectName) it.next();
            try {
                String protocol = (String) mBeanServer.getAttribute(oname, "protocol");
                String scheme = (String) mBeanServer.getAttribute(oname, "scheme");
                if (pProtocol.equalsIgnoreCase(protocol) && pScheme.equalsIgnoreCase(scheme)) {
                    return ((Integer)mBeanServer.getAttribute(oname, "port")).toString();
                }
            } catch (Exception e) {
                LOG.error("TomcatUtil: 在MBeanServer指定web server的MBean中查找端口信息失败", e);
            }
        }
        LOG.error("TomcatUtil: 未能在MBeanServer中指定web server的MBean中找到端口信息");
        return null;
    }

}
