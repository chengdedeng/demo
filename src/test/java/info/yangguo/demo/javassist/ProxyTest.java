package info.yangguo.demo.javassist;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyTest{
    @Test
    public void testMain() throws Exception {
        Proxy proxy = Proxy.getProxy(ITest.class, ITest.class);
        ITest instance = (ITest) proxy.newInstance(new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if ("getName".equals(method.getName())) {
                    Assert.assertEquals(args.length, 0);
                } else if ("setName".equals(method.getName())) {
                    Assert.assertEquals(args.length, 2);
                    Assert.assertEquals(args[0], "yangguo");
                    Assert.assertEquals(args[1], "hello");
                }
                return null;
            }
        });

        Assert.assertNull(instance.getName());
        instance.setName("yangguo", "hello");
    }

    public static interface ITest {
        String getName();

        void setName(String name, String name2);
    }
}