import aop.matcher.AspectJExpressionPointcut;
import aop.service.HelloServiceImpl;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MatcherTest {
    @Test
    public void testMethodMatcher(){
        String expression = "execution(* aop.service.HelloServiceImpl.*(..))";
        AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
        aspectJExpressionPointcut.setExpression(expression);
        try {
            boolean matches = aspectJExpressionPointcut.matches(HelloServiceImpl.class.getMethod("sayHello"),HelloServiceImpl.class);
            System.out.println(matches);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
