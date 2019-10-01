package aop.advice;

import aop.invocation.MethodInvocation;

import java.lang.reflect.InvocationHandler;

public interface MethodInterceptor {
    Object invoke(MethodInvocation methodInvocation);
}
