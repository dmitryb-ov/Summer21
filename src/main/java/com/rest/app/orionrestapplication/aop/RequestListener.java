package com.rest.app.orionrestapplication.aop;

import com.rest.app.orionrestapplication.annotation.Monitor;
import com.rest.app.orionrestapplication.model.History;
import com.rest.app.orionrestapplication.service.HistoryService;
import com.rest.app.orionrestapplication.util.HttpRequestResponseUtil;
import lombok.extern.log4j.Log4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j
public class RequestListener {

    private final HistoryService historyService;

    @Autowired
    public RequestListener(HistoryService historyService) {
        this.historyService = historyService;
    }

    //    @Pointcut("execution(public com.rest.app.orionrestapplication.service.impl.*(..))")
    @Pointcut(value = "@annotation(com.rest.app.orionrestapplication.annotation.Monitor)")
    public void selectAllMethodsAvailable() {
        //This is pointcut
    }

    @After("selectAllMethodsAvailable()")
    public void afterMethodExecution(JoinPoint joinPoint) {
        var history = new History();
        try {
            history.setIp(HttpRequestResponseUtil.getClientIpAddressIfServletRequestExist());
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            var userDetails = (UserDetails) authentication.getPrincipal();
            history.setUsername(userDetails.getUsername());
        } catch (ClassCastException e) {
            history.setUsername(null);
            log.error("User is not login.", e.getCause());
        } finally {
            history.setRequestType(getMonitorValue(joinPoint));
            history.setClassName(joinPoint.getTarget().getClass().getCanonicalName());
            history.setMethodName(joinPoint.getSignature().getName());
            var result = historyService.addHistory(history);
            log.info("History add. " + result.toString());
        }
    }

    private String getMonitorValue(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        var method = signature.getMethod();

        var monitor = method.getAnnotation(Monitor.class);
        return monitor.requestName().name();
    }
}
