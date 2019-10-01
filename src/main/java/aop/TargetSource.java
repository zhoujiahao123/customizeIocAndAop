package aop;

public class TargetSource {
    private Object bean;
    private Class<?> beanClass;
    private Class<?>[] interfaces;

    public TargetSource(Object bean, Class<?> beanClass, Class<?>[] interfaces) {
        this.bean = bean;
        this.beanClass = beanClass;
        this.interfaces = interfaces;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public Class<?>[] getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(Class<?>[] interfaces) {
        this.interfaces = interfaces;
    }
}
