package aop.proxy;

import aop.AdvisedSupport;

public abstract class AbstractAopProxy implements AopProxy {
    protected AdvisedSupport advisedSupport;
    public AbstractAopProxy(AdvisedSupport advisedSupport){
        this.advisedSupport = advisedSupport;
    }
}
