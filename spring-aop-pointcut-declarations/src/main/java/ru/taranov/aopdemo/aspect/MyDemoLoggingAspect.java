package ru.taranov.aopdemo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyDemoLoggingAspect {

    @Pointcut("execution(public * ru.taranov.aopdemo.dao.*.*(..))")
    private void forDaoPackage() {}

    // create pointcut for getter methods
    @Pointcut("execution(public * ru.taranov.aopdemo.dao.*.get*(..))")
    private void getter() {}

    // create pointcut for setter methods
    @Pointcut("execution(public * ru.taranov.aopdemo.dao.*.set*(..))")
    private void setter() {}

    // create pointcut: include package ... exclude getter/setter
    @Pointcut("forDaoPackage() && !(getter() || setter())")
    private void forDaoPackageNoGetterSetter() {}

    // this is where we add all of our related advices for logging
    // let's start with an @Before advice

    @Before("forDaoPackageNoGetterSetter()")
    public void beforeAddAccountAdvice() {
        System.out.println("\n=====>>> Executing @Before advice on addAccount()");
    }

    @Before("forDaoPackageNoGetterSetter()")
    public void performApiAnalytics() {
        System.out.println("\n=====>>> Perform API analytics");
    }
}
