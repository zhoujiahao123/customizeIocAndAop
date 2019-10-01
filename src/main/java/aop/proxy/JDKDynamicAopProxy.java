package aop.proxy;

import aop.AdvisedSupport;
import aop.advice.MethodInterceptor;
import aop.invocation.ReflectiveMethodInvocation;
import aop.matcher.MethodMatcher;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKDynamicAopProxy extends AbstractAopProxy implements InvocationHandler {


    public JDKDynamicAopProxy(AdvisedSupport advisedSupport) {
        super(advisedSupport);
    }

    public Object getProxy(){
        return Proxy.newProxyInstance(JDKDynamicAopProxy.class.getClassLoader(),advisedSupport.getTargetSource().getInterfaces(),this);
    }
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MethodMatcher methodMatcher = advisedSupport.getMethodMatcher();
        if(methodMatcher.matches(method,advisedSupport.getTargetSource().getBeanClass())){
            MethodInterceptor methodInterceptor = advisedSupport.getMethodInterceptor();
            return methodInterceptor.invoke(new ReflectiveMethodInvocation(advisedSupport.getTargetSource().getBean(),method,args));
        }
        return method.invoke(advisedSupport.getTargetSource().getBean(),args);

    }
}
