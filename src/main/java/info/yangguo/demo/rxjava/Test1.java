package info.yangguo.demo.rxjava;

import java.io.File;
import java.util.concurrent.Executors;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.internal.schedulers.ExecutorScheduler;
import rx.schedulers.Schedulers;

/**
 * @author:杨果
 * @date:2017/1/1 下午4:03
 *
 * Description:
 *
 */
public class Test1 {
    public static void main(String[] args) {
        File file1 = new File("/Users/yangguo/Downloads");
        File file2 = new File("/Users/yangguo/Desktop");
        File[] folders = new File[]{file1, file2};
        Observable.from(folders)
                .flatMap(new Func1<File, Observable<File>>() {
                    @Override
                    public Observable<File> call(File file) {
                        return Observable.from(file.listFiles());
                    }
                })
                .filter(new Func1<File, Boolean>() {
                    @Override
                    public Boolean call(File file) {
                        return file.getName().endsWith(".png");
                    }
                })
                .map(new Func1<File, String>() {
                    @Override
                    public String call(File file) {
                        return String.valueOf(file.length());
                    }
                })
                .subscribeOn(Schedulers.io())
//                .observeOn(new ExecutorScheduler(Executors.newFixedThreadPool(2)))
                .observeOn(Schedulers.newThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String bitmap) {
                        System.out.println(bitmap);
                    }
                });
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
