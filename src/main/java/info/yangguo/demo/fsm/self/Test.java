package info.yangguo.demo.fsm.self;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/1/6
 * Time:下午3:30
 * <p/>
 * Description:
 */
public class Test {
    public static void main(String[] args) {
        CallInfo callInfo=new CallInfo();
        callInfo.setRpid("rpid");
        callInfo.setIp("192.168.1.1");
        callInfo.setIp("192.168.1.2");
        callInfo.setIp("192.168.1.3");
        callInfo.setIp("192.168.1.4");
        HashMap map=new HashMap();
        map.put("STATE1","STATE1");
        map.put("STATE2","STATE2");
        map.put("STATE3","STATE3");
        map.put("STATE4","STATE4");
        callInfo.setOthers(map);
        Context context=new ContextImpl();
        context.setCallInfo(callInfo);
        States.execute(context);
    }
}
