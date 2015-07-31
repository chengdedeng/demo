package info.yangguo.demo.script

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.StreamGobbler;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import org.apache.commons.io.IOUtils;

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

                channel.connect();
                List<String> lists = IOUtils.readLines(channel.getInputStream());
                for (String line : lists) {
                    println(line)
                }
                channel.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.disconnect();
            println("session关闭成功");
        }
    }
}
