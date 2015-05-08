package info.yangguo.demo.fsm.self;


/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/1/6
 * Time:下午4:00
 * <p/>
 * Description:
 */
public class ContextImpl implements Context {
    private CallInfo callInfo;
    private State state;

    @Override
    public CallInfo getCallInfo() {
        return callInfo;
    }

    @Override
    public void setCallInfo(CallInfo callInfo) {
        this.callInfo = callInfo;
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public void setState(State state) {
        this.state = state;
    }
}
