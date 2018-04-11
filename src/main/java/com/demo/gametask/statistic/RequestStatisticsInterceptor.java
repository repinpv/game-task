package com.demo.gametask.statistic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestStatisticsInterceptor implements AsyncHandlerInterceptor {

    private ThreadLocal<Long> beginTime = new ThreadLocal<>();

    private static final Logger log = LoggerFactory.getLogger(RequestStatisticsInterceptor.class);

    @Autowired
    private HibernateStatisticsInterceptor hibernateStatisticsInterceptor;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // stupid filter
        final String requestURI = request.getRequestURI();
        if (requestURI.startsWith("\\view")) {
            return true;
        }
        if (requestURI.startsWith("\\error")) {
            return true;
        }

        beginTime.set(System.currentTimeMillis());
        hibernateStatisticsInterceptor.initCounters();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // stupid filter
        final String requestURI = request.getRequestURI();
        if (requestURI.startsWith("\\view")) {
            return;
        }
        if (requestURI.startsWith("\\error")) {
            return;
        }

        long duration = System.currentTimeMillis() - beginTime.get();
        // process redirect
        final String prevProcessTime = request.getParameter("_processTime");
        if (prevProcessTime != null) {
            duration += Long.parseLong(prevProcessTime);
        }
        modelAndView.addObject("_processTime", duration);

        int queryCount = hibernateStatisticsInterceptor.getQueryCount();
        // process redirect
        final String prevQueryCount = request.getParameter("_queryCount");
        if (prevQueryCount != null) {
            queryCount += Integer.parseInt(prevQueryCount);
        }
        modelAndView.addObject("_queryCount", queryCount);

        long transactionTime = hibernateStatisticsInterceptor.getTransactionTime();
        // process redirect
        final String prevQueryTime = request.getParameter("_queryTime");
        if (prevQueryTime != null) {
            transactionTime += Long.parseLong(prevQueryTime);
        }
        modelAndView.addObject("_queryTime", transactionTime);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // stupid filter
        final String requestURI = request.getRequestURI();
        if (requestURI.startsWith("\\view")) {
            return;
        }
        if (requestURI.startsWith("\\error")) {
            return;
        }

        long duration = System.currentTimeMillis() - beginTime.get();
        Integer queryCount = hibernateStatisticsInterceptor.getQueryCount();
        hibernateStatisticsInterceptor.clearCounters();
        beginTime.remove();
        log.info("[Time: {} ms] [Queries: {}] {} {}", duration, queryCount, request.getMethod(), request.getRequestURI());
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // stupid filter
        final String requestURI = request.getRequestURI();
        if (requestURI.startsWith("\\view")) {
            return;
        }
        if (requestURI.startsWith("\\error")) {
            return;
        }

        //concurrent handling cannot be supported here
        hibernateStatisticsInterceptor.clearCounters();
        beginTime.remove();
    }
}
