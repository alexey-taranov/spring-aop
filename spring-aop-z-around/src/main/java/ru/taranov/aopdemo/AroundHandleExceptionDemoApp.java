package ru.taranov.aopdemo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.taranov.aopdemo.service.TrafficFortuneService;

import java.util.logging.Logger;

public class AroundHandleExceptionDemoApp {

    private static Logger myLogger = Logger.getLogger(AroundHandleExceptionDemoApp.class.getName());

    public static void main(String[] args) {
        // read spring config java class
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(DemoConfig.class);

        TrafficFortuneService theFortuneService = context.getBean("trafficFortuneService", TrafficFortuneService.class);

        boolean tripwire = true;
        String data = theFortuneService.getFortune(tripwire);

        myLogger.info("My fortune is: " + data);
        myLogger.info("Finished");

        // close the context
        context.close();
    }
}
