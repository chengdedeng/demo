package info.yangguo.demo.SSH2;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.StreamGobbler;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/7/23
 * Time:下午2:45
 * <p/>
 * Description:
 * <p/>
 * SSH2测试代码
 */
public class SSH2Test {
    public static void main(String[] args) {
        ExtClasspathLoader.loadClasspath("/Users/yangguo/.gradle/caches/modules-2/files-2.1/com.jcraft/jsch/0.1.53/658b682d5c817b27ae795637dfec047c63d29935");
        ExtClasspathLoader.loadClasspath("/Users/yangguo/.gradle/caches/modules-2/files-2.1/ch.ethz.ganymed/ganymed-ssh2/262/7761dc665d0f6993dc846d914214fb93291e2bdf");
//        test1();
        test2();
    }

    public static void test1() {
        String hostname = "192.168.161.87";
        String username = "root";
        String password = "op3tomcat";
        //指明连接主机的IP地址
        Connection conn = new Connection(hostname);
        ch.ethz.ssh2.Session ssh = null;
        try {
            //连接到主机
            conn.connect();
            //使用用户名和密码校验
            boolean isconn = conn.authenticateWithPassword(username, password);
            ssh = conn.openSession();
            if (!isconn) {
                System.out.println("用户名或密码不正确");
            } else {
                System.out.println("登陆成功");
                while (true) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                    String command = bufferedReader.readLine();
                    ssh.execCommand(command);
                    //只允许使用一行命令，即ssh对象只能使用一次execCommand这个方法，多次使用则会出现异常，使用多个命令用分号隔开
                    //ssh.execCommand("cd /root; sh hello.sh");

                    //将Terminal屏幕上的文字全部打印出来
                    InputStream is = new StreamGobbler(ssh.getStdout());
                    BufferedReader brs = new BufferedReader(new InputStreamReader(is));
                    while (true) {
                        String line = brs.readLine();
                        if (line == null) {
                            break;
                        }
                        System.out.println(line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            ssh.close();
            conn.close();
        }
    }

    public static void test2() {
        com.jcraft.jsch.JSch jsch = new com.jcraft.jsch.JSch();

        String user = "root";
        String host = "192.168.161.87";
        String password = "op3tomcat";
        com.jcraft.jsch.Session session = null;
        try {
            session = jsch.getSession(user, host, 22);
            jsch.setKnownHosts("/Users/yangguo/.ssh/known_hosts");
            jsch.addIdentity("/Users/yangguo/.ssh/id_rsa");
            session.setPassword(password);
            session.connect();
            System.out.println("登陆成功");

            while (true) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                String command = bufferedReader.readLine();
                Channel channel = session.openChannel("exec");
                ((ChannelExec) channel).setCommand(command);

                InputStream in = channel.getInputStream();
                channel.connect();
                List<String> lists = IOUtils.readLines(in);
                for (String line : lists) {
                    System.out.println(line);
                }
                channel.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.disconnect();
            System.out.println("session关闭成功");
        }
    }

    /**
     * 根据properties中配置的路径把jar和配置文件加载到classpath中。
     *
     * @author guo.yang
     */
    private static class ExtClasspathLoader {
        /**
         * URLClassLoader的addURL方法
         */
        private static Method addURL = initAddMethod();

        private static URLClassLoader classloader = (URLClassLoader) ClassLoader.getSystemClassLoader();

        /**
         * 初始化addUrl 方法.
         *
         * @return 可访问addUrl方法的Method对象
         */
        private static Method initAddMethod() {
            try {
                Method add = URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
                add.setAccessible(true);
                return add;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        private static void loadClasspath(String filepath) {
            File file = new File(filepath);
            loopFiles(file);
        }

        private static void loadResourceDir(String filepath) {
            File file = new File(filepath);
            loopDirs(file);
        }

        /**
         * 循环遍历目录，找出所有的资源路径。
         *
         * @param file 当前遍历文件
         */
        private static void loopDirs(File file) {
            // 资源文件只加载路径
            if (file.isDirectory()) {
                addURL(file);
                File[] tmps = file.listFiles();
                for (File tmp : tmps) {
                    loopDirs(tmp);
                }
            }
        }

        /**
         * 循环遍历目录，找出所有的jar包。
         *
         * @param file 当前遍历文件
         */
        private static void loopFiles(File file) {
            if (file.isDirectory()) {
                File[] tmps = file.listFiles();
                for (File tmp : tmps) {
                    loopFiles(tmp);
                }
            } else {
                if (file.getAbsolutePath().endsWith(".jar") || file.getAbsolutePath().endsWith(".zip")) {
                    addURL(file);
                }
            }
        }

        /**
         * 通过filepath加载文件到classpath。
         *
         * @param file 文件路径
         * @return URL
         * @throws Exception 异常
         */
        private static void addURL(File file) {
            try {
                addURL.invoke(classloader, new Object[]{file.toURI().toURL()});
            } catch (Exception e) {
            }
        }
    }
}
