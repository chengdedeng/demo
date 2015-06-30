package info.yangguo.demo.javassist.others;

import java.lang.reflect.Method;

public class Test2 {

    public static void main(String[] args) throws Exception {
        CountService delegate = new CountServiceImpl();

        Method method = delegate.getClass().getMethod("count");
        method.invoke(delegate);
    }
}
