package info.yangguo.demo.fsm.self;

import info.yangguo.demo.utils.JsonUtil;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/1/6
 * Time:下午3:01
 * <p/>
 * Description:
 * <p/>
 * 成功执行，需要经过转化的状态为：BEGIN->STATE1->STATE2->STATE3->SUCCESS
 */
public enum States implements State {

    BEGIN {
        public boolean process(Context context) {
            CallInfo callInfo = context.getCallInfo();
            if (callInfo.getOthers().containsKey("STATE1")) {
                context.setState(STATE1);
                System.out.println("STATE1");
            } else {
                context.setState(ERROR);
            }
            return true;
        }
    },
    STATE1 {
        public boolean process(Context context) {
            CallInfo callInfo = context.getCallInfo();
            if (callInfo.getOthers().containsKey("STATE2")) {
                context.setState(STATE2);
                System.out.println("STATE2");
            } else {
                context.setState(ERROR);
            }
            return true;
        }
    },
    STATE2 {
        public boolean process(Context context) {
            CallInfo callInfo = context.getCallInfo();
            if (callInfo.getOthers().containsKey("STATE3")) {
                context.setState(STATE3);
                System.out.println("STATE3");
            } else {
                context.setState(ERROR);
            }
            return true;
        }
    },
    STATE3 {
        public boolean process(Context context) {
            CallInfo callInfo = context.getCallInfo();
            if (callInfo.getOthers().containsKey("STATE4")) {
                context.setState(SUCCESS);
                System.out.println("STATE4");
            } else {
                context.setState(ERROR);
            }
            return true;
        }
    },
    ERROR {
        public boolean process(Context context) {
            CallInfo callInfo = context.getCallInfo();
            try {
                System.out.println("ERROR------" + JsonUtil.toJson(callInfo, true));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
    },
    SUCCESS {
        public boolean process(Context context) {
            CallInfo callInfo = context.getCallInfo();
            try {
                System.out.println("SUCCESS------" + JsonUtil.toJson(callInfo, true));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
    };

    public static void execute(Context context) {
        context.setState(BEGIN);
        while (context.getState().process(context)) ;
    }
}

