package aop.matcher;

import java.lang.reflect.Method;

public interface MethodMatcher {
    boolean matches(Method method,Class beanClass);
}
