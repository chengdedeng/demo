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
        System.out.println("TimedClassTransformer初始化开始");
        classPool = new ClassPool();
        classPool.appendSystemPath();
        try {
            classPool.appendPathList(System.getProperty("java.class.path"));
            classPool.appendPathList("/Users/yangguo/.gradle/caches/modules-2/files-2.1/org.javassist/javassist/3.20.0-GA/a9cbcdfb7e9f86fbc74d3afae65f2248bfbf82a0");
        } catch (Exception e) {
            System.out.println("异常:" + e);
            throw new RuntimeException(e);
        }
        System.out.println("TimedClassTransformer初始化完成");
    }

    public byte[] transform(ClassLoader loader, String fullyQualifiedClassName, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classBytes) throws IllegalClassFormatException {
        System.out.println("Transform开始");
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
                    method.addLocalVariable("__metricStartTime", CtClass.longType);
                    method.insertBefore("__metricStartTime = System.currentTimeMillis();");
                    String metricName = ctClass.getName() + "." + method.getName();
                    method.insertAfter("System.out.println( System.currentTimeMillis() - __metricStartTime);");
                    isClassModified = true;
                }
            }
            if (!isClassModified) {
                return null;
            }
            System.out.println("Transform结束");
            return ctClass.toBytecode();
        } catch (Exception e) {
            System.out.println("Skip class : " + className + ",Error Message:" + e.getMessage());
            return null;
        }
    }
}