package info.yangguo.demo.btrace;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/6/4
 * Time:上午9:57
 * <p/>
 * Description:
 */
public class Adder {
    private static int maxResult = 0;

    public int execute(int arg1, int arg2) throws Exception {
        System.out.println("arg1: " + arg1 + ",arg2: " + arg2);
        int result = arg1 + arg2;
        if (result > maxResult)
            maxResult = result;
        System.out.println("result: " + result + ",maxResult: " + maxResult);
        Thread.sleep(result);
        return result;
    }
}
