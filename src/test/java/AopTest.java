import aop.AdvisedSupport;
import aop.TargetSource;
import aop.advice.MethodInterceptor;
import aop.advice.SimpleInterceptor;
import aop.matcher.AspectJExpressionPointcut;
import aop.proxy.JDKDynamicAopProxy;
import aop.service.HelloServiceImpl;
import aop.service.IHelloService;
import org.junit.Test;

public class AopTest {
    @Test
    public void test() {
        AdvisedSupport advisedSupport = new AdvisedSupport();
        IHelloService helloService = new HelloServiceImpl();
        TargetSource targetSource = new TargetSource(helloService,HelloServiceImpl.class,HelloServiceImpl.class.getInterfaces());
        advisedSupport.setTargetSource(targetSource);
        MethodInterceptor methodInterceptor = new SimpleInterceptor();
        advisedSupport.setMethodInterceptor(methodInterceptor);
        AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
        //为什么类HelloServiceImpl不行？因为getinterfaces得到的是接口的方法。
        String expression = "execution(* aop.service.IHelloService.*(..))";
        aspectJExpressionPointcut.setExpression(expression);
        advisedSupport.setMethodMatcher(aspectJExpressionPointcut);
        JDKDynamicAopProxy jdkDynamicAopProxy = new JDKDynamicAopProxy(advisedSupport);
        IHelloService proxy = (IHelloService) jdkDynamicAopProxy.getProxy();
        proxy.sayHello();
    }
}
