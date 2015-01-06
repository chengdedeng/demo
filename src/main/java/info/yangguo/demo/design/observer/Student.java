package info.yangguo.demo.design.observer;

import java.util.Observable;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:14/12/1
 * Time:下午10:25
 * <p/>
 * Description:
 * <p/>
 * 被观察者
 */
public class Student extends Observable {
    public String name;
    private String course;
    public final static String ENGLISH = "english";
    public final static String HISTORY = "history";
    public final static String MATH = "math";

    public Student(String name) {
        this.name = name;
    }

    public String getState() {
        return course;
    }

    public void changeState(String course) {
        if (this.course != course) {
            this.course = course;
            this.setChanged();
            if (ENGLISH == course) {
                this.notifyObservers("英语课");
            } else if (HISTORY == course) {
                this.notifyObservers("历史课");
            } else if (MATH == course) {
                this.notifyObservers("数学课");
            }
        } else {
            this.notifyObservers("同上一节课");
        }
    }
}
