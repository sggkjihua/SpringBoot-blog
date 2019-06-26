package com.xiaolin.blog.aspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@Aspect
@Component
public class LogAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Pointcut("execution(* com.xiaolin.blog.controller.*.*(..))")
    public void log(){
        this.logger.info("within the logger......");
    }
    @Before("log()")
    public void doBefore(JoinPoint joinpoint) {
        ServletRequestAttributes request = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String ip = request.getRequest().getRemoteAddr();
        String url = request.getRequest().getRequestURL().toString();
        String method = joinpoint.getSignature().getDeclaringTypeName()+ ":"+joinpoint.getSignature().getName();
        Object[] objects = joinpoint.getArgs();
        RequestLogger requestLogger = new RequestLogger(ip, url, method, objects);
        this.logger.info("Request: {}", requestLogger);
    }

    @After("log()")
    public void doAfter() {
        this.logger.info("-------do after-------");

    }

    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterReturning(Object result) {
        this.logger.info("-------do after returning-------");
    }



    private class RequestLogger {
        private String ip;
        private String url;
        private String method;
        private Object[] args;

        public RequestLogger(String ip, String url, String method, Object[] args) {
            this.ip = ip;
            this.url = url;
            this.method = method;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLogger{" +
                    "ip='" + ip + '\'' +
                    ", url='" + url + '\'' +
                    ", method='" + method + '\'' +
                    ", args=" + (args == null ? null : Arrays.asList(args)) +
                    '}';
        }
    }
}
