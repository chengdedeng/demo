package info.yangguo.demo.fsm.self;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/1/7
 * Time:上午11:20
 * <p/>
 * Description:
 */
public class CallInfo {
    /**
     * 调用链唯一标识符
     */
    private String rpid;
    /**
     * 调用链经过的服务器ip
     */
    private ArrayList<String> ips = new ArrayList<>();
    /**
     * 调用链上经过的项目
     */
    public ArrayList<String> products = new ArrayList<>();
    /**
     * 其它想在调用链上传递的参数，非公共参数
     */
    public HashMap<String, String> others = new HashMap<>();

    /**
     * 调用链上，每处调用的时间
     */
    public ArrayList<Long> times = new ArrayList<>();


    public String getRpid() {
        return rpid;
    }

    public void setRpid(String rpid) {
        this.rpid = rpid;
    }

    public ArrayList<String> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<String> products) {
        this.products = products;
    }

    /**
     * 设置当前项目的名称
     *
     * @param product
     */
    public void setProduct(String product) {
        products.add(product);
    }


    public HashMap<String, String> getOthers() {
        return others;
    }

    public void setOthers(HashMap<String, String> others) {
        this.others = others;
    }

    public void setOther(String key, String value) {
        others.put(key, value);
    }

    public ArrayList<String> getIps() {
        return ips;
    }

    public void setIps(ArrayList<String> ips) {
        this.ips = ips;
    }

    /**
     * 设置当前项目的ip
     *
     * @param ip
     */
    public void setIp(String ip) {
        ips.add(ip);
    }

    public ArrayList<Long> getTimes() {
        return times;
    }

    public void setTimes(ArrayList<Long> times) {
        this.times = times;
    }

    /**
     * 设置当前项目执行的时间
     *
     * @param time
     */
    public void setTime(long time) {
        times.add(time);
    }
}

