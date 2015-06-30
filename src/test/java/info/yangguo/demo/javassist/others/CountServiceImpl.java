package info.yangguo.demo.javassist.others;

/**
 * Created by yangguo on 14-4-24.
 */
public class CountServiceImpl implements CountService {

    private int count = 0;

    public int count() {
        return count ++;
    }
}
