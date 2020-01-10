package ru.taranov.aopdemo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.taranov.aopdemo.dao.AccountDAO;
import ru.taranov.aopdemo.dao.MembershipDAO;

public class MainDemoApp {

    public static void main(String[] args) {
        // read spring config java class
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(DemoConfig.class);

        // get the bean from spring container
        AccountDAO theAccountDAO = context.getBean("accountDAO", AccountDAO.class);
        MembershipDAO membershipDAO = context.getBean("membershipDAO", MembershipDAO.class);

        // call the business method
        theAccountDAO.addAccount(new Account());
        theAccountDAO.goToSleep();

        membershipDAO.addMember();
        membershipDAO.doWork();

        // call the accountdao getter/setter methods
        theAccountDAO.setName("John");
        theAccountDAO.setServiceCode("silver");

        theAccountDAO.getName();
        theAccountDAO.getServiceCode();
        
        // close the context
        context.close();
    }
}
