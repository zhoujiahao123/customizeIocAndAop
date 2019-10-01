package aop.invocation;

import java.lang.reflect.Method;

public interface MethodInvocation extends JoinPoint{
    Method getMethod();
}
