package ru.taranov.aopdemo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.taranov.aopdemo.dao.AccountDAO;

import java.util.List;

public class AfterFinallyDemoApp {

    public static void main(String[] args) {
        // read spring config java class
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(DemoConfig.class);

        // get the bean from spring container
        AccountDAO theAccountDAO = context.getBean("accountDAO", AccountDAO.class);

        List<Account> accounts = null;

        try {
            // add a boolean flag to simulate exception
            boolean tripWire = false;
            accounts = theAccountDAO.findAccounts(tripWire);
        } catch (Exception e) {
            System.out.println("\n\nMain program caught exception: " + e);
        }

        System.out.println("\n\nMain program: AfterThrowingDemoApp");
        System.out.println("-----");
        System.out.println(accounts);
        System.out.println("\n");
        
        // close the context
        context.close();
    }
}
