package info.yangguo.demo.fork_join;

import java.util.concurrent.RecursiveTask;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/3/11
 * Time:上午11:07
 * <p/>
 * Description:
 */
public class DocumentSearchTask extends RecursiveTask<Long> {
    private final Document document;
    private final String searchedWord;

    DocumentSearchTask(Document document, String searchedWord) {
        super();
        this.document = document;
        this.searchedWord = searchedWord;
    }

    @Override
    protected Long compute() {
        WordCounter wordCounter = new WordCounter();
        return wordCounter.occurrencesCount(document, searchedWord);
    }
}