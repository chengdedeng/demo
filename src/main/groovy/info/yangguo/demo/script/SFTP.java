package info.yangguo.demo.script;

import com.jcraft.jsch.*;
import java.io.*;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/7/24
 * Time:上午10:43
 * <p/>
 * Description:
 */
public class SFTP {
    public static void main(String[] args) {
        String username = "root";
        String host = "192.168.161.87";
        String pass = "op3tomcat";
        String khfile = "/Users/yangguo/.ssh/known_hosts";
        String identityfile = "/Users/yangguo/.ssh/id_rsa";

        JSch jsch = null;
        Session session = null;
        Channel channel = null;
        ChannelSftp c = null;
        try {
            jsch = new JSch();
            session = jsch.getSession(username, host, 22);
            session.setPassword(pass);
            jsch.setKnownHosts(khfile);
            jsch.addIdentity(identityfile);
            session.connect();

            channel = session.openChannel("sftp");
            channel.connect();
            c = (ChannelSftp) channel;

        } catch (Exception e) { 	e.printStackTrace();	}

        try {
            System.out.println("Starting File Upload:");
            String fsrc = "/Users/yangguo/work/code/flight-price/tops-eterm-interface/tz-eterm-interface-web/target/libs/tz-eterm-interface-web.war", fdest = "/root/tz-eterm-interface-web.war";
            c.put(fsrc, fdest);

//            c.get(fdest, "/tmp/testfile.bin");
        } catch (Exception e) {	e.printStackTrace();	}

        c.disconnect();
        session.disconnect();

    }
}
