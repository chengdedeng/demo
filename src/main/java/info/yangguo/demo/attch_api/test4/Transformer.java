package info.yangguo.demo.attch_api.test4;

import javassist.ByteArrayClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class Transformer implements ClassFileTransformer {
    private ClassPool classPool;

    public Transformer() {
        classPool = new ClassPool();
        classPool.appendSystemPath();
        try {
            classPool.appendPathList(System.getProperty("java.class.path"));
        } catch (Exception e) {
            System.out.println("异常:" + e);
            throw new RuntimeException(e);
        }
    }

    public byte[] transform(ClassLoader loader, String fullyQualifiedClassName, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classBytes) throws IllegalClassFormatException {
        String className = fullyQualifiedClassName.replace("/", ".");

        classPool.appendClassPath(new ByteArrayClassPath(className, classBytes));

        try {
            CtClass ctClass = classPool.get(className);
            if (ctClass.isFrozen()) {
                System.out.println("Skip class " + className + ": is frozen");
                return null;
            }

            if (ctClass.isPrimitive() || ctClass.isArray() || ctClass.isAnnotation()
                    || ctClass.isEnum() || ctClass.isInterface()) {
                System.out.println("Skip class " + className + ": not a class");
                return null;
            }

            boolean isClassModified = false;
            for (CtMethod method : ctClass.getDeclaredMethods()) {
                // if method is annotated, add the code to measure the time
                if (method.hasAnnotation(Point.class)) {
                    if (method.getMethodInfo().getCodeAttribute() == null) {
                        System.out.println("Skip method " + method.getLongName());
                        continue;
                    }
                    System.out.println("Instrumenting method " + method.getLongName());
                    //此处实现了一个AOP的环绕织入，在我们常规的需求中，可以在新增监控的report逻辑，
                    //如果需要加载第三方的jar，可以通过动态加载classpath来实现

                    method.addLocalVariable("__metricStartTime", CtClass.longType);
                    method.insertBefore("__metricStartTime = System.currentTimeMillis();");
                    method.insertAfter("System.out.println( System.currentTimeMillis() - __metricStartTime);");
                    isClassModified = true;
                }
            }
            if (!isClassModified) {
                return null;
            }
            return ctClass.toBytecode();
        } catch (Exception e) {
            System.out.println("Skip class : " + className + ",Error Message:" + e.getMessage());
            return null;
        }
    }
}