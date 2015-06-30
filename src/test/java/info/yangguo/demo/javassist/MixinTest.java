package info.yangguo.demo.javassist;

import org.junit.Assert;
import org.junit.Test;

public class MixinTest {
    @Test
    public void testMain() throws Exception {
        Mixin mixin = Mixin.mixin(new Class[]{I1.class, I2.class, I3.class}, new Class[]{C1.class, C2.class});
        Object o = mixin.newInstance(new Object[]{new C1(), new C2()});
        Assert.assertEquals(o instanceof I1, true);
        Assert.assertEquals(o instanceof I2, true);
        Assert.assertEquals(o instanceof I3, true);
        ((I1) o).m1();
        ((I2) o).m2();
        ((I3) o).m3();
    }

    interface I1 {
        void m1();
    }

    interface I2 {
        void m2();
    }

    interface I3 {
        void m3();
    }

    class C1 implements Mixin.MixinAware {
        public void m1() {
            System.out.println("c1.m1();");
        }

        public void m2() {
            System.out.println("c1.m2();");
        }

        public void setMixinInstance(Object mi) {
            System.out.println("setMixinInstance:" + mi);
        }
    }

    class C2 implements Mixin.MixinAware {
        public void m3() {
            System.out.println("c2.m3();");
        }

        public void setMixinInstance(Object mi) {
            System.out.println("setMixinInstance:" + mi);
        }
    }
}