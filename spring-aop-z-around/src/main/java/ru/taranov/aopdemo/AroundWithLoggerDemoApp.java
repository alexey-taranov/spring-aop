package ru.taranov.aopdemo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.taranov.aopdemo.service.TrafficFortuneService;

import java.util.logging.Logger;

public class AroundWithLoggerDemoApp {

    private static Logger myLogger = Logger.getLogger(AroundWithLoggerDemoApp.class.getName());

    public static void main(String[] args) {
        // read spring config java class
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(DemoConfig.class);

        TrafficFortuneService theFortuneService = context.getBean("trafficFortuneService", TrafficFortuneService.class);

        String data = theFortuneService.getFortune();
        myLogger.info("My fortune is: " + data);

        myLogger.info("Finished");
        // close the context
        context.close();
    }
}
