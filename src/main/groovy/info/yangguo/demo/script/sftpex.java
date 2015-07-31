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
public class sftpex {
    public static void main(String[] args) {
        String username = "testuser";
        String host = "testserver.example.com";
        String pass = "testpass";
        String khfile = "/home/testuser/.ssh/known_hosts";
        String identityfile = "/home/testuser/.ssh/id_rsa";

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
            String fsrc = "/tmp/abc.txt", fdest = "/tmp/cde.txt";
            c.put(fsrc, fdest);

            c.get(fdest, "/tmp/testfile.bin");
        } catch (Exception e) {	e.printStackTrace();	}

        c.disconnect();
        session.disconnect();

    }
}
