package ru.taranov.aopdemo.dao;

import org.springframework.stereotype.Component;
import ru.taranov.aopdemo.Account;

@Component
public class AccountDAO {

    public void addAccount(Account account) {
        System.out.println(getClass() + ": DOING MY DB WORK: ADDING AN ACCOUNT");
    }

    public boolean goToSleep() {
        System.out.println(getClass() + ": GO TO SLEEP");
        return true;
    }
}
