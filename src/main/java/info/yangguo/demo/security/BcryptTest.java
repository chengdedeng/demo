package info.yangguo.demo.security;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/**
 * @author:杨果
 * @date:16/4/19 上午10:04
 *
 * Description:
 *
 */
public class BcryptTest {
    private static String password = "yangguo";

    @Test
    public void testEncode() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        for (int i = 0; i < 10; i++) {
            String encodePassword=bCryptPasswordEncoder.encode(password);
            System.out.println(encodePassword);
            System.out.println(bCryptPasswordEncoder.matches(password,encodePassword));
        }
    }
}
