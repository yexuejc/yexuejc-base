package com.yexuejc.base.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.net.URL;
import java.util.concurrent.*;

/**
 * 系统工具类
 *
 * @PackageName: com.yexuejc.util.base
 * @Description:
 * @author: maxf
 * @date: 2017/12/28 16:12
 */
public class SysUtil {
    private static final String PROJECT_ROOT_PATH = "java.io.tmpdir";

    private SysUtil() {
    }

    /**
     * 获取临时缓存路径
     *
     * @return String
     */
    public static String getCachePath() {
        return System.getProperty(PROJECT_ROOT_PATH);
    }

    /**
     * 从根路径获取文件URL
     *
     * @param clazz
     * @return
     */
    public static URL getRootPath(Class clazz, String filePath) {
        return clazz.getClass().getResource(StrUtil.setStr(filePath, "/"));
    }

    /**
     * 开启线程执行<p>
     * 异步处理代码
     *
     * @param threadRun
     */
    public static void threadRun(ThreadRun threadRun) {
        threadRun(null, threadRun);
    }

    /**
     * 异步处理代码
     *
     * @param poolName  开启线程名称
     * @param threadRun
     */
    public static void threadRun(String poolName, ThreadRun threadRun) {
        if (StrUtil.isEmpty(poolName)) {
            poolName = "java-pool-%d";
        }
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat(poolName).build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        singleThreadPool.execute(() -> {
            threadRun.execute();
        });
        singleThreadPool.shutdown();
    }

    /**
     * 异步执行代码块
     */
    public interface ThreadRun {
        void execute();
    }
}
