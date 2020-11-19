package com.common.sequence;

import com.common.sequence.sequence.Sequence;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class AppTest {
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            50, 51, 10000, TimeUnit.SECONDS, new LinkedBlockingQueue(), new ThreadPoolExecutor.AbortPolicy());

    @Autowired
    @Qualifier("sequenceForTest")
    Sequence sequenceForTest;

    @Autowired
    @Qualifier("sequenceForFXS")
    Sequence sequenceForFXS;


    @Test
    void contextLoads() throws InterruptedException {
        /**
         * 单线程测试没问题
         * 程序正常执行
         */
        //for (int i = 0; i < 1000; i++) {
        //          long l = sequence.nextValue();
        //          System.out.println(l);
        //      }
        CountDownLatch countDownLatch = new CountDownLatch(100);
        for (int i = 0; i < 100; i++) {
            threadPoolExecutor.execute(() -> {
                for (int j = 0; j < 100; j++) {
                    System.out.println(sequenceForFXS.nextValue());
                    System.out.println(sequenceForTest.nextValue());
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        Thread.sleep(100 * 1000);
        if (!threadPoolExecutor.isShutdown()) {
            threadPoolExecutor.shutdown();
        }
        if (!threadPoolExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
            threadPoolExecutor.shutdownNow();
            if (!threadPoolExecutor.awaitTermination(60, TimeUnit.SECONDS))
                System.err.println("线程池任务未正常执行结束");
        }
    }

}
