package com.demo.gametask.statistic;

import org.hibernate.EmptyInterceptor;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateStatisticsInterceptor extends EmptyInterceptor {

    private static final Logger log = LoggerFactory.getLogger(HibernateStatisticsInterceptor.class);

    private ThreadLocal<Integer> queryCount = new ThreadLocal<>();
    private ThreadLocal<Long> beginTimeHolder = new ThreadLocal<>();
    private ThreadLocal<Long> timeHolder = new ThreadLocal<>();

    public void initCounters() {
        queryCount.set(0);
        timeHolder.set(0L);
    }

    public Integer getQueryCount() {
        return queryCount.get();
    }

    public Long getTransactionTime() {
        return timeHolder.get();
    }

    public void clearCounters() {
        queryCount.remove();
        timeHolder.remove();
    }

    @Override
    public String onPrepareStatement(String sql) {
        Integer count = queryCount.get();
        if (count != null) {
            queryCount.set(count + 1);
        }
        //log.info(sql);
        return sql;
    }

    @Override
    public void afterTransactionBegin(Transaction tx) {
        beginTimeHolder.set(System.currentTimeMillis());
    }

    @Override
    public void afterTransactionCompletion(Transaction tx) {
        final long currentTimeMillis = System.currentTimeMillis();

        final Long beginTime = beginTimeHolder.get();
        if (beginTime == null) {
            log.error("Incorrect hibernate transaction begin time calcs");
        } else {
            final Long time = timeHolder.get();
            if (time == null) {
                log.error("Incorrect hibernate transaction time calcs");
            } else {
                timeHolder.set(time + currentTimeMillis - beginTime);
            }
        }

        beginTimeHolder.remove();
    }
}
