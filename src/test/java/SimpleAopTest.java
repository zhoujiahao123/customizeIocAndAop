import org.junit.Test;
import simpleAop.*;

public class SimpleAopTest {
    @Test
    public void getProxy(){
        IHelloService helloService = new HelloServiceImpl();

        Advice beforeAdvice = new BeforeAdvice(helloService, new MethodInvocation() {
            public void invoke() {
                System.out.println("我是一个前置通知");
            }
        });
        IHelloService proxy = (IHelloService) SimpleAop.getProxy(helloService,beforeAdvice);
        proxy.sayHello();
    }
}
