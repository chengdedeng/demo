package info.yangguo.demo.security;

import org.owasp.esapi.ESAPI;

/**
 * @author:杨果
 * @date:2017/3/29 下午4:34
 *
 * Description:
 *
 */
public class EncodeTest {
    public static void main(String[] args) {
        String dangerousText="<script type=\"text/javascript\">alert(\"ok\")</script>ok";
        String safetyText=ESAPI.encoder().encodeForHTML(dangerousText);
        System.out.println(safetyText);
    }
}
