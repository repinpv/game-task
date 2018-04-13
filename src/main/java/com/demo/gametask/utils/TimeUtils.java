package com.demo.gametask.utils;

public final class TimeUtils {

    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public static long getLeftTime(long beginTime) {
        return beginTime - getCurrentTime();
    }

    private TimeUtils() {
    }
}
