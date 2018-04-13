package com.demo.gametask.statistics;

import com.demo.gametask.utils.TimeUtils;
import org.hibernate.EmptyInterceptor;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateStatisticsInterceptor extends EmptyInterceptor {

    private static final Logger log = LoggerFactory.getLogger(HibernateStatisticsInterceptor.class);

    private ThreadLocal<Integer> queryCount = new ThreadLocal<>();
    private ThreadLocal<Long> beginTimeHolder = new ThreadLocal<>();
    private ThreadLocal<Long> timeHolder = new ThreadLocal<>();

    public Integer getQueryCount() {
        final Integer count = queryCount.get();
        return count == null ? 0 : count;
    }

    public Long getTransactionTime() {
        final Long time = timeHolder.get();
        return time == null ? 0 : time;
    }

    public void clearCounters() {
        queryCount.remove();
        timeHolder.remove();
    }

    @Override
    public String onPrepareStatement(String sql) {
        Integer count = queryCount.get();
        if (count == null) {
            count = 0;
        }
        queryCount.set(count + 1);

        return sql;
    }

    @Override
    public void afterTransactionBegin(Transaction tx) {
        beginTimeHolder.set(TimeUtils.getCurrentTime());
    }

    @Override
    public void afterTransactionCompletion(Transaction tx) {
        final long currentTime = TimeUtils.getCurrentTime();

        final Long beginTime = beginTimeHolder.get();
        if (beginTime == null) {
            log.error("Incorrect hibernate transaction begin time calcs");
        } else {
            final Long time = timeHolder.get();
            if (time == null) {
                log.error("Incorrect hibernate transaction time calcs");
            } else {
                timeHolder.set(time + currentTime - beginTime);
            }
        }

        beginTimeHolder.remove();
    }
}
