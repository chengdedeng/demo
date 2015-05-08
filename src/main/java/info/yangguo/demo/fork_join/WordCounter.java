package info.yangguo.demo.fork_join;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/3/11
 * Time:上午11:07
 * <p/>
 * Description:
 */
public class WordCounter {
    String[] wordsIn(String line) {
        return line.trim().split("(\\s|\\p{Punct})+");
    }

    /**
     * 多线程
     *
     * @param document
     * @param searchedWord
     * @return
     */
    public Long occurrencesCount(Document document, String searchedWord) {
        long count = 0;
        for (String line : document.getLines()) {
            for (String word : wordsIn(line)) {
                if (searchedWord.equals(word)) {
                    count = count + 1;
                }
            }
        }
        return count;
    }

    /**
     * 单线程
     *
     * @param folder
     * @param searchedWord
     * @return
     */
    public Long countOccurrencesOnSingleThread(Folder folder, String searchedWord) {
        long count = 0;
        for (Folder subFolder : folder.getSubFolders()) {
            count = count + countOccurrencesOnSingleThread(subFolder, searchedWord);
        }
        for (Document document : folder.getDocuments()) {
            count = count + occurrencesCount(document, searchedWord);
        }
        return count;
    }
}
