package info.yangguo.demo.script

import java.lang.reflect.Method

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/7/24
 * Time:上午11:49 
 *
 * Description:
 */
class SSH2Main {
    public static void main(String[] args) {
        ExtClasspathLoader.loadClasspath("/Users/yangguo/.gradle/caches/modules-2/files-2.1/com.jcraft/jsch/0.1.53/658b682d5c817b27ae795637dfec047c63d29935");
        ExtClasspathLoader.loadClasspath("/Users/yangguo/.gradle/caches/modules-2/files-2.1/ch.ethz.ganymed/ganymed-ssh2/262/7761dc665d0f6993dc846d914214fb93291e2bdf");
        SSH2Test.test2();
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
                Method add = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
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
                addURL.invoke(classloader, [file.toURI().toURL()]);
            } catch (Exception e) {
            }
        }
    }

}
