package info.yangguo.demo.attch_api.test2;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Random;

public class TestExample {
    private Random random = new Random();

    @Point
    public void doSleep() {
        try {
            Thread.sleep(15000);
            System.out.println("*");
        } catch (InterruptedException e) {
        }
    }

    @Point
    private void doTask() {
        try {
            Thread.sleep(random.nextInt(1500));
            System.out.println(".");
        } catch (InterruptedException e) {
        }
    }

    @Point
    public void doWork() {
        for (int i = 0; i < random.nextInt(12); i++) {
            doTask();
        }
    }

    public static void main(String[] args) {
        /**
         * 为了减少设置classpath的做法
         */
        ExtClasspathLoader.loadClasspath("/Users/yangguo/.gradle/caches/modules-2/files-2.1/org.javassist/javassist/3.20.0-GA/a9cbcdfb7e9f86fbc74d3afae65f2248bfbf82a0");
        TestExample test = new TestExample();
        while (true) {
            test.doWork();
            test.doSleep();
        }
    }


    /**
     * 根据properties中配置的路径把jar和配置文件加载到classpath中。
     *
     * @author guo.yang
     */
    private static class ExtClasspathLoader {
        /**
         * URLClassLoader的addURL方法
         */
        private static Method addURL = initAddMethod();

        private static URLClassLoader classloader = (URLClassLoader) ClassLoader.getSystemClassLoader();

        /**
         * 初始化addUrl 方法.
         *
         * @return 可访问addUrl方法的Method对象
         */
        private static Method initAddMethod() {
            try {
                Method add = URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
                add.setAccessible(true);
                return add;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        private static void loadClasspath(String filepath) {
            File file = new File(filepath);
            loopFiles(file);
        }

        private static void loadResourceDir(String filepath) {
            File file = new File(filepath);
            loopDirs(file);
        }

        /**
         * 循环遍历目录，找出所有的资源路径。
         *
         * @param file 当前遍历文件
         */
        private static void loopDirs(File file) {
            // 资源文件只加载路径
            if (file.isDirectory()) {
                addURL(file);
                File[] tmps = file.listFiles();
                for (File tmp : tmps) {
                    loopDirs(tmp);
                }
            }
        }

        /**
         * 循环遍历目录，找出所有的jar包。
         *
         * @param file 当前遍历文件
         */
        private static void loopFiles(File file) {
            if (file.isDirectory()) {
                File[] tmps = file.listFiles();
                for (File tmp : tmps) {
                    loopFiles(tmp);
                }
            } else {
                if (file.getAbsolutePath().endsWith(".jar") || file.getAbsolutePath().endsWith(".zip")) {
                    addURL(file);
                }
            }
        }

        /**
         * 通过filepath加载文件到classpath。
         *
         * @param file 文件路径
         * @return URL
         * @throws Exception 异常
         */
        private static void addURL(File file) {
            try {
                addURL.invoke(classloader, new Object[]{file.toURI().toURL()});
            } catch (Exception e) {
            }
        }
    }
}
