package com.devculi.sway.manager.service.threadpool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class MainExecutor {
  private static final int CORE_POOL_SIZE = 2;
  private static final int MAXIMUM_POOL_SIZE = 10;

  private static final AtomicLong COUNTER = new AtomicLong(0);
  private static final ThreadPoolExecutor ExePool =
      new ThreadPoolExecutor(
          CORE_POOL_SIZE,
          MAXIMUM_POOL_SIZE,
          0L,
          TimeUnit.MILLISECONDS,
          new LinkedBlockingQueue<Runnable>(),
          new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
              Thread t = new Thread(r);
              String threadName = "sway-" + COUNTER.getAndIncrement();
              t.setName(threadName);
              return t;
            }
          });

  public static ThreadPoolExecutor getInstance() {
    return ExePool;
  }
}
