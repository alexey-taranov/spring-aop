package ru.taranov.aopdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.taranov.aopdemo.Account;

import java.util.List;
import java.util.logging.Logger;

@Aspect
@Component
@Order(2)
public class MyDemoLoggingAspect {

    private Logger myLogger = Logger.getLogger(getClass().getName());

    @Around("execution(* ru.taranov.aopdemo.service.*.getFortune(..))")
    public Object aroundGetFortune(
            ProceedingJoinPoint proceedingJoinPoint) throws Throwable{

        String method = proceedingJoinPoint.getSignature().toShortString();
        myLogger.info("\n==========> Executing @Around on method " + method);

        // get begin timestamp
        long begin = System.currentTimeMillis();

        // let's execute method
        Object result;

        try {
            result = proceedingJoinPoint.proceed();
        } catch (Exception e) {
            myLogger.warning(e.getMessage());
            result = "Major accident! But no worries, " +
                    "your private  AOP helicopter is on way";
        }

        // get end timestamp
        long end = System.currentTimeMillis();

        long duration = end - begin;
        myLogger.info("\n=====> Duration: " + duration/1000.0 + " seconds");

        return result;
    }

    @Before("ru.taranov.aopdemo.aspect.AopExpressions.forDaoPackageNoGetterSetter()")
    public void beforeAddAccountAdvice(JoinPoint theJoinPoint) {
        myLogger.info("\n=====>>> Executing @Before advice on addAccount()");

        // display the method signature
        MethodSignature methodSignature = (MethodSignature) theJoinPoint.getSignature();
        myLogger.info("Method: " + methodSignature);

        // display method arguments
        Object[] args = theJoinPoint.getArgs();
        for (Object arg : args) {
            myLogger.info(arg.toString());

            if (arg instanceof Account) {
                Account theAccount = (Account) arg;
                myLogger.info("account name: " + theAccount.getName());
                myLogger.info("account level: " + theAccount.getLevel());
            }
        }
    }

    // add a new advice for @AfterReturning
    @AfterReturning(
            pointcut = "execution(* ru.taranov.aopdemo.dao.AccountDAO.findAccounts(..))",
            returning = "result")
    public void afterReturningFindAccountsAdvice(
            JoinPoint joinPoint, List<Account> result) {

        String method = joinPoint.getSignature().toShortString();
        myLogger.info("\n==========> Executing @AfterRunning on method " + method);

        myLogger.info("\n==========> result is " + result);

        convertAccountNamesToUpperCase(result);

        myLogger.info("\n==========> result is " + result);
    }

    @AfterThrowing(
            pointcut = "execution(* ru.taranov.aopdemo.dao.AccountDAO.findAccounts(..))",
            throwing = "exc")
    public void afterThrowingFindAccountsAdvice(
            JoinPoint joinPoint, Throwable exc) {

        String method = joinPoint.getSignature().toShortString();
        myLogger.info("\n==========> Executing @AfterThrowing on method " + method);

        myLogger.info("\n==========> exception is " + exc);
    }

    @After("execution(* ru.taranov.aopdemo.dao.AccountDAO.findAccounts(..))")
    public void afterFinallyFindAccountsAdvice(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().toShortString();
        myLogger.info("\n==========> Executing @After (finally) on method " + method);
    }

    private void convertAccountNamesToUpperCase(List<Account> result) {
        for (Account tempAccount : result) {
            String upperName = tempAccount.getName().toUpperCase();
            tempAccount.setName(upperName);
        }
    }
}
