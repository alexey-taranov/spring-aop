package ru.taranov.aopdemo.dao;

import org.springframework.stereotype.Component;

@Component
public class MembershipDAO {

    public void addMember() {
        System.out.println(getClass() + ": ADDING A MEMBERSHIP ACCOUNT");
    }

    public boolean doWork() {
        System.out.println(getClass() + ": DO WORK");
        return true;
    }
}
