package aop.matcher;

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;
import org.aspectj.weaver.tools.ShadowMatch;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class AspectJExpressionPointcut implements PointCut, ClassFilter, MethodMatcher {
    private static final Set<PointcutPrimitive> DEFAULT_SUPPORTED_PRIMITIVES = new HashSet<PointcutPrimitive>();
    private PointcutParser pointcutParser;
    private String expression;
    private PointcutExpression pointcutExpression;

    static {
        DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
        DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.ARGS);
        DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.REFERENCE);
        DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.THIS);
        DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.TARGET);
        DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.WITHIN);
        DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_ANNOTATION);
        DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_WITHIN);
        DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_ARGS);
        DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_TARGET);
    }

    public AspectJExpressionPointcut() {
        this(DEFAULT_SUPPORTED_PRIMITIVES);
    }

    private void checkReadyToMatches() {
        if (expression == null) throw new IllegalStateException("Expression must not null");
        if (pointcutExpression == null) {
            pointcutExpression = pointcutParser.parsePointcutExpression(expression);
        }
    }

    //获得解析器
    public AspectJExpressionPointcut(Set<PointcutPrimitive> supportedPrimitives) {
        pointcutParser = PointcutParser
                .getPointcutParserSupportingSpecifiedPrimitivesAndUsingContextClassloaderForResolution(supportedPrimitives);
    }

    public boolean matchers(Class beanClass) {
        checkReadyToMatches();
        return pointcutExpression.couldMatchJoinPointsInType(beanClass);
    }

    public boolean matches(Method method, Class beanClass) {
        checkReadyToMatches();
        ShadowMatch shadowMatch = pointcutExpression.matchesMethodExecution(method);
        if(shadowMatch.alwaysMatches()) return true;
        else if (shadowMatch.neverMatches()){
            return false;
        }
        return false;
    }

    public ClassFilter getClassFilter() {
        return null;
    }

    public MethodMatcher getMethodMatcher() {
        return null;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}
