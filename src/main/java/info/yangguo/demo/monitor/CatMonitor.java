package info.yangguo.demo.monitor;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Event;
import com.dianping.cat.message.Transaction;
import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/11/17
 * Time:上午10:56
 * <p/>
 * Description:
 */
public class CatMonitor {
    private static Logger logger = Logger.getLogger(CatMonitor.class);

    @Test
    public void transactionTest() throws Exception {
        for (int i = 0; i < 5; i++) {
            Cat.getManager().setTraceMode(true);
//            Transaction t = Cat.getProducer().newTransaction("SQL", "sql detail");
            Transaction t = Cat.getProducer().newTransaction("URL", "url detail");
            try {
                logger.debug("logger-debug");
                logger.info("logger-info");
                logger.error("logger-error", new RuntimeException());
//                Cat.getProducer().logEvent("SQL.METHOD", "select", Event.SUCCESS, "select * from user");
                Cat.getProducer().logEvent("URL.SERVICE", "findUser", Event.SUCCESS, "find user");
                Thread.sleep(1000 * 10);
                t.setStatus(Transaction.SUCCESS);
            } catch (Exception e) {
                Cat.getProducer().logError(e);//用log4j记录系统异常，以便在Logview中看到此信息
                t.setStatus(e);
                throw e;
            } finally {
                t.complete();
            }
        }


        try {
            Thread.sleep(1000 * 30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
