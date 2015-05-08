package info.yangguo.demo.fork_join;

import org.joda.time.DateTime;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/3/16
 * Time:下午9:56
 * <p/>
 * Description:
 */
public class Main {
    private final ForkJoinPool forkJoinPool = new ForkJoinPool();

    Long countOccurrencesInParallel(Folder folder, String searchedWord) {
        return forkJoinPool.invoke(new FolderSearchTask(folder, searchedWord));
    }

    public static void main(String[] args) throws IOException {
        Folder folder = Folder.fromDirectory(new File(args[0]));
        //并行
        DateTime begin1 = new DateTime();
        Main main = new Main();
        System.out.println(main.countOccurrencesInParallel(folder, args[1]));
        DateTime end1 = new DateTime();
        System.out.println(end1.toDate().getTime() - begin1.toDate().getTime());
        //单线程
        DateTime begin2 = new DateTime();
        WordCounter wordCounter = new WordCounter();
        System.out.println(wordCounter.countOccurrencesOnSingleThread(folder, args[1]));
        DateTime end2 = new DateTime();
        System.out.println(end2.toDate().getTime() - begin2.toDate().getTime());
    }

}
