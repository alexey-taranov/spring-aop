package ru.taranov.aopdemo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.taranov.aopdemo.service.TrafficFortuneService;

public class AroundDemoApp {

    public static void main(String[] args) {
        // read spring config java class
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(DemoConfig.class);

        TrafficFortuneService theFortuneService = context.getBean("trafficFortuneService", TrafficFortuneService.class);

        String data = theFortuneService.getFortune();
        System.out.println(data);

        // close the context
        context.close();
    }
}
