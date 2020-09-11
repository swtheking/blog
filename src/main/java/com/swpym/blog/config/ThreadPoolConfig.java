package com.swpym.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description: 自定义线程池
 * @author: shaowei
 * @date: 2020-05-12 19:55:43
 */
@Configuration
public class ThreadPoolConfig {

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        //获取cpu核心数
        final int cpu = Runtime.getRuntime().availableProcessors();
        //设置核心线程池数量
        final int corePoolSize = cpu + 1;
        //设置线程池中最大线程数量
        final int maximumPoolSize = cpu * 2 + 1;
        //设置线程空闲时间，超时终止线程，线程池线程数量维持在核心线程池大小(corePoolSize)
        final long keepAliveTime = 1L;
        //设置空闲时间的时间单位
        final TimeUnit timeUnit = TimeUnit.SECONDS;
        //设置阻塞队列，用来存储等待执行的任务，如果当前线程数量已超过核心线程设定的数量，则把任务暂时存放在等待队列中
        final int maxQueueNum = 1 << 7;
        //new CustomThreadFactory()：线程工程，用来创建线程
        //new ThreadPoolExecutor.AbortPolicy()：如果线程池已满，新的任务的处理方式
        return new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                timeUnit,
                new LinkedBlockingQueue<>(maxQueueNum),
                new CustomThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    /**
     * 线程工厂
     */
    private static class CustomThreadFactory implements ThreadFactory {
        /**
         * 初始化线程名称时使用
         * 提供原子操作的Integer，线程安全的
         * AtomicInteger可以在并发情况下达到原子化更新，避免使用了synchronized，而且性能非常高。
         */
        private final AtomicInteger poolNumber = new AtomicInteger(1);
        /**
         * 指定当前线程的线程组，未指定时线程组为创建该线程所属的线程组
         */
        private final ThreadGroup group;
        /**
         * 构建线程实体时使用
         * 提供原子操作的Integer，线程安全的
         * AtomicInteger可以在并发情况下达到原子化更新，避免使用了synchronized，而且性能非常高。
         */
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        /**
         * 线程名称
         */
        private final String namePrefix;

        CustomThreadFactory() {
            //构建java安全管理器
            SecurityManager s = System.getSecurityManager();
            //判定使用java安全管理器的group还是默认当前线程的group
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            //初始化线程名称
            namePrefix = "pool-" + poolNumber.getAndIncrement() + "-thread-";
        }

        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(group, runnable, namePrefix + threadNumber.getAndIncrement(), 0);
            // 设置不是守护线程
            if (thread.isDaemon()) {
                thread.setDaemon(false);
            }
            // 设置优先级为默认的
            if (thread.getPriority() != Thread.NORM_PRIORITY) {
                thread.setPriority(Thread.NORM_PRIORITY);
            }
            return thread;
        }
    }
}
