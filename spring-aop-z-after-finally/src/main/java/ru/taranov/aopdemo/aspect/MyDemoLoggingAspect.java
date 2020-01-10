package ru.taranov.aopdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.taranov.aopdemo.Account;

import java.util.List;

@Aspect
@Component
@Order(2)
public class MyDemoLoggingAspect {

    // let's start with an @Before advice

    @Before("ru.taranov.aopdemo.aspect.AopExpressions.forDaoPackageNoGetterSetter()")
    public void beforeAddAccountAdvice(JoinPoint theJoinPoint) {
        System.out.println("\n=====>>> Executing @Before advice on addAccount()");

        // display the method signature
        MethodSignature methodSignature = (MethodSignature) theJoinPoint.getSignature();
        System.out.println("Method: " + methodSignature);

        // display method arguments
        Object[] args = theJoinPoint.getArgs();
        for (Object arg : args) {
            System.out.println(arg);

            if (arg instanceof Account) {
                Account theAccount = (Account) arg;
                System.out.println("account name: " + theAccount.getName());
                System.out.println("account level: " + theAccount.getLevel());
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
        System.out.println("\n==========> Executing @AfterRunning on method " + method);

        System.out.println("\n==========> result is " + result);

        convertAccountNamesToUpperCase(result);

        System.out.println("\n==========> result is " + result);
    }

    @AfterThrowing(
            pointcut = "execution(* ru.taranov.aopdemo.dao.AccountDAO.findAccounts(..))",
            throwing = "exc")
    public void afterThrowingFindAccountsAdvice(
            JoinPoint joinPoint, Throwable exc) {

        String method = joinPoint.getSignature().toShortString();
        System.out.println("\n==========> Executing @AfterThrowing on method " + method);

        System.out.println("\n==========> exception is " + exc);
    }

    @After("execution(* ru.taranov.aopdemo.dao.AccountDAO.findAccounts(..))")
    public void afterFinallyFindAccountsAdvice(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().toShortString();
        System.out.println("\n==========> Executing @After (finally) on method " + method);
    }

    private void convertAccountNamesToUpperCase(List<Account> result) {
        for (Account tempAccount : result) {
            String upperName = tempAccount.getName().toUpperCase();
            tempAccount.setName(upperName);
        }
    }
}
