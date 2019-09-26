package simpleAop;

import java.lang.reflect.Proxy;

/**
 * 1.创建一个包含切面逻辑的对象methodInvocation，通俗点说就是在你的被代理对象bean相应方法前面或者后面执行的那些逻辑
 * 2.创建一个通知对象advice,其实现invocationHandler，其必须包含我们的被代理对象bean以及上面定义的包含切面的对象methodInvocation
 * 3.将bean对象以及我们的advice传给JDK的动态代理方法，即调用所谓的proxy.newInstance方法去获取我们的代理对象。
 */
public class SimpleAop{
    public static Object getProxy(Object bean,Advice advice){
        return Proxy.newProxyInstance(SimpleAop.class.getClassLoader(),bean.getClass().getInterfaces(),advice);
    }
}