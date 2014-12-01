package info.yangguo.demo.design.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:14/12/1
 * Time:下午10:19
 * <p/>
 * Description:
 * <p/>
 * 观察者
 */
public class Teacher implements Observer {
    private String name;

    public Teacher(String name) {
        super();
        this.name = name;
    }

    public void update(Observable o, Object arg) {
        Student student = (Student) o;
        //获取被观察对象当前的状态
        System.out.println(name + "被通知：" + student.name + "正在上" + student.getState() + "课");
    }
}
