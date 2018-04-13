package com.demo.gametask.statistics;

import com.demo.gametask.utils.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestStatisticsInterceptor extends HandlerInterceptorAdapter {

    private static final String BEGIN_PROCESS_TIME = "beginTime";
    private static final String PROCESS_TIME = "_processTime";
    private static final String QUERY_COUNT = "_queryCount";
    private static final String QUERY_TIME = "_queryTime";
    private static final Logger log = LoggerFactory.getLogger(RequestStatisticsInterceptor.class);

    @Autowired
    private HibernateStatisticsInterceptor hibernateStatisticsInterceptor;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (filter(request)) {
            return true;
        }

        request.setAttribute(BEGIN_PROCESS_TIME, TimeUtils.getCurrentTime());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (filter(request) || modelAndView == null) {
            return;
        }

        final Long beginProcessTime = (Long) request.getAttribute(BEGIN_PROCESS_TIME);
        long duration = TimeUtils.getCurrentTime() - beginProcessTime;
        // process redirect
        final String prevProcessTime = request.getParameter(PROCESS_TIME);
        if (prevProcessTime != null) {
            duration += Long.parseLong(prevProcessTime);
        }
        modelAndView.addObject(PROCESS_TIME, duration);

        int queryCount = hibernateStatisticsInterceptor.getQueryCount();
        // process redirect
        final String prevQueryCount = request.getParameter(QUERY_COUNT);
        if (prevQueryCount != null) {
            queryCount += Integer.parseInt(prevQueryCount);
        }
        modelAndView.addObject(QUERY_COUNT, queryCount);

        long transactionTime = hibernateStatisticsInterceptor.getTransactionTime();
        // process redirect
        final String prevQueryTime = request.getParameter(QUERY_TIME);
        if (prevQueryTime != null) {
            transactionTime += Long.parseLong(prevQueryTime);
        }
        modelAndView.addObject(QUERY_TIME, transactionTime);

        hibernateStatisticsInterceptor.clearCounters();
    }

    private boolean filter(HttpServletRequest request) {
        // stupid filter
        final String requestURI = request.getRequestURI();
        return requestURI.startsWith("/view") || requestURI.startsWith("/error");
    }
}
