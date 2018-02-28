package info.yangguo.demo.httpclient;

/**
 * Created with IntelliJ IDEA.
 * User: guo
 * Date: 2017/11/1
 * Time: 下午5:01
 * Description:
 */
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientTest {
    public static void main(String args[]) throws Exception {
        // 创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        // 依次是目标请求地址，端口号,协议类型
        HttpHost target = new HttpHost("www.baidu.com", 443,
                "https");
        // 依次是代理地址，代理端口号，协议类型
        HttpHost proxy = new HttpHost("45.76.37.108", 443, "https");
        RequestConfig config = RequestConfig.custom().setProxy(proxy).build();

        // 请求地址
        HttpPost httpPost = new HttpPost("https://www.baidu.com");
        httpPost.setConfig(config);
        // 创建参数队列
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        // 参数名为pid，值是2
        formparams.add(new BasicNameValuePair("pid", "2"));

        UrlEncodedFormEntity entity;
        try {
            entity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httpPost.setEntity(entity);
            CloseableHttpResponse response = closeableHttpClient.execute(
                    target, httpPost);
            // getEntity()
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null) {
                // 打印响应内容
                System.out.println("response:"
                        + EntityUtils.toString(httpEntity, "UTF-8"));
            }
            // 释放资源
            closeableHttpClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}