package aop.matcher;

public interface PointCut {
    ClassFilter getClassFilter();

    MethodMatcher getMethodMatcher();
}
