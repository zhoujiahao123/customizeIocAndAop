package aop.advice;

import aop.invocation.MethodInvocation;

import java.lang.reflect.Method;

public class SimpleInterceptor implements MethodInterceptor {

    public Object invoke(MethodInvocation methodInvocation) {
        Object resultValue = null;
        System.out.println("通知的前置执行");
        resultValue = methodInvocation.proceed();
        System.out.println("通知的后置执行");
        return resultValue;
    }

}
