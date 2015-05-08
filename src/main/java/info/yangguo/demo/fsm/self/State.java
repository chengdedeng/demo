package info.yangguo.demo.fsm.self;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/1/6
 * Time:下午3:00
 * <p/>
 * Description:
 */
public interface State {
    /**
     * @return true to keep processing, false to read more data.
     */

    boolean process(Context context);
}
