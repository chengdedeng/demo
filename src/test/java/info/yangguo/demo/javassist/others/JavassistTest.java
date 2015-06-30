package info.yangguo.demo.javassist.others;

import javassist.*;

import java.lang.reflect.Field;

/**
 * Created by yangguo on 14-4-25.
 */
public class JavassistTest {
    public static void main(String[] args) {

    }
    private static <T> T createJavassistBytecodeDynamicProxy(Class<T> t) throws Exception {
        ClassPool mPool = new ClassPool(true);
        CtClass mCtc = mPool.makeClass(t.getName() + "JavaassistProxy");
        mCtc.addInterface(mPool.get(t.getName()));
        mCtc.addConstructor(CtNewConstructor.defaultConstructor(mCtc));



        mCtc.addField(CtField.make("public " + CountService.class.getName() + " delegate;", mCtc));
        mCtc.addMethod(CtNewMethod.make("public int count() { return delegate.count(); }", mCtc));
        Class pc = mCtc.toClass();
        CountService bytecodeProxy = (CountService) pc.newInstance();
        Field filed = bytecodeProxy.getClass().getField("delegate");
//        filed.set(bytecodeProxy, delegate);
//        return bytecodeProxy;
        return null;
    }
}
