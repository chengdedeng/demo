package info.yangguo.demo.attch_api.test4;


import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/7/8
 * Time:上午10:26
 * <p/>
 * Description:
 */
public class JavacCompileAndJar {
    String javaSourcePath;
    String javaClassPath;
    String targetPath;
    String jarClassPath;
    HashMap<Object, Object> attributes;

    public JavacCompileAndJar(String javaSourcePath, String javaClassPath, String jarClassPath, String targetPath, HashMap attributes) {
        this.javaSourcePath = javaSourcePath;
        this.javaClassPath = javaClassPath;
        this.targetPath = targetPath;
        this.jarClassPath = jarClassPath;
        this.attributes = attributes;
    }

    public void complier() throws IOException {
        System.out.println("*** --> 开始编译java源代码...");

        File javaclassDir = new File(javaClassPath);
        if (!javaclassDir.exists()) {
            javaclassDir.mkdirs();
        }

        List<String> javaSourceList = new ArrayList<>();
        getFileList(new File(javaSourcePath), javaSourceList);

        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        int result = -1;
        for (int i = 0; i < javaSourceList.size(); i++) {
            if (javaSourceList.get(i).endsWith("java")) {
                result = javaCompiler.run(null, null, null, "-d", javaClassPath, javaSourceList.get(i));
                System.out.println(result == 0 ? "*** 编译成功 : " + javaSourceList.get(i) : "### 编译失败 : " + javaSourceList.get(i));
            }
        }
        System.out.println("*** --> java源代码编译完成。");
    }

    private void getFileList(File file, List<String> fileList) throws IOException {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    getFileList(files[i], fileList);
                } else {
                    fileList.add(files[i].getPath());
                }
            }
        }
    }

    public void generateJar() throws IOException {
        System.out.println("*** --> 开始生成jar包...");
        String targetDirPath = targetPath.substring(0, targetPath.lastIndexOf("/"));
        File targetDir = new File(targetDirPath);
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }

        Manifest manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        for (Map.Entry<Object, Object> attribute : attributes.entrySet()) {
            manifest.getMainAttributes().put(attribute.getKey(), attribute.getValue());
        }


        JarOutputStream target = new JarOutputStream(new FileOutputStream(targetPath), manifest);
        writeClassFile(new File(jarClassPath), target);
        target.close();
        System.out.println("*** --> jar包生成完毕。");
    }

    private void writeClassFile(File source, JarOutputStream target) throws IOException {
        BufferedInputStream in = null;
        try {
            if (source.isDirectory()) {
                String name = source.getPath().replace("\\", "/");
                if (!name.isEmpty()) {
                    if (!name.endsWith("/")) {
                        name += "/";
                    }
                    name = name.substring(jarClassPath.length());
                    JarEntry entry = new JarEntry(name);
                    entry.setTime(source.lastModified());
                    target.putNextEntry(entry);
                    target.closeEntry();
                }
                for (File nestedFile : source.listFiles())
                    writeClassFile(nestedFile, target);
                return;
            }

            String middleName = source.getPath().replace("\\", "/").substring(javaClassPath.length());
            JarEntry entry = new JarEntry(middleName);
            entry.setTime(source.lastModified());
            target.putNextEntry(entry);
            in = new BufferedInputStream(new FileInputStream(source));

            byte[] buffer = new byte[1024];
            while (true) {
                int count = in.read(buffer);
                if (count == -1)
                    break;
                target.write(buffer, 0, count);
            }
            target.closeEntry();
        } finally {
            if (in != null)
                in.close();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        String javaSourcePath = "/Users/yangguo/work/code/demo/src/main/java/info/yangguo/demo/attach_api/test4";
        String javaClassPath = "/Users/yangguo/Downloads/demo/classes/info/yangguo/demo/attach_api/test4";
        String jarClassPath = "/Users/yangguo/Downloads/demo/classes";
        String targetPath = "/Users/yangguo/Downloads/demo/target/libs/demo-1.0-SNAPSHOT-fat.jar";

        HashMap<Object, Object> attributes = new HashMap<>();
        Attributes.Name agentClass = new Attributes.Name("Agent-Class");
        attributes.put(agentClass, "info.yangguo.demo.attch_api.test4.Agent");
        Attributes.Name canRedineClasses = new Attributes.Name("Can-Redefine-Classes");
        attributes.put(canRedineClasses, "true");
        Attributes.Name canRetransformClasses = new Attributes.Name("Can-Retransform-Classes");
        attributes.put(canRetransformClasses, "true");

        JavacCompileAndJar javacCompileAndJar = new JavacCompileAndJar(javaSourcePath, javaClassPath, jarClassPath, targetPath, attributes);
        javacCompileAndJar.complier();
        javacCompileAndJar.generateJar();
    }

}
