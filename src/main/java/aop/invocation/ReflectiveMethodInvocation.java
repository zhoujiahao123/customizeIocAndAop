package aop.invocation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectiveMethodInvocation implements MethodInvocation {
    private Object bean;
    private Method method;
    private Object[] args;

    public ReflectiveMethodInvocation(Object bean, Method method, Object[] args) {
        this.bean = bean;
        this.method = method;
        this.args = args;
    }

    public Method getMethod() {
        return method;
    }

    public Object proceed() {
        Object resultValue = null;
        try {
            resultValue = method.invoke(bean,args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return resultValue;
    }
}
