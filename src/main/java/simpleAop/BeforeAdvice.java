package simpleAop;

import java.lang.reflect.Method;

public class BeforeAdvice implements Advice {
    private MethodInvocation methodInvocation;
    private Object bean;

    public BeforeAdvice(Object bean,MethodInvocation methodInvocation) {
        this.methodInvocation = methodInvocation;
        this.bean = bean;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        methodInvocation.invoke();
        return method.invoke(bean,args);
    }
}
