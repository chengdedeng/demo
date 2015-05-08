package info.yangguo.demo.fsm.self;



/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/1/6
 * Time:下午2:59
 * <p/>
 * Description:
 */
public interface Context {
    CallInfo getCallInfo();

    void setCallInfo(CallInfo callInfo);

    State getState();

    void setState(State state);
}

