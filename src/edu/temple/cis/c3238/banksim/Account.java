package edu.temple.cis.c3238.banksim;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.ExecutorService; 
import java.util.concurrent.Executors; 
import java.util.*;

/**
 * @author Cay Horstmann
 * @author Modified by Paul Wolfgang
 * @author Modified by Charles Wang
 * @author Modified by Alexa Delacenserie
 * @author Modified by Tarek Elseify
 */
public class Account {

    private volatile int balance;
    private final int id;
    public final ReentrantLock lock = new ReentrantLock();

    public Account(int id, int initialBalance) {
        this.id = id;
        this.balance = initialBalance;
    }

    public int getBalance() {
        return balance;
    }

    public boolean withdraw(int amount) {
        if (amount <= balance) {
            int currentBalance = balance;
            // Thread.yield(); // Try to force collision
            int newBalance = currentBalance - amount;
            balance = newBalance;
            return true;
        } else {
            return false;
        }
    }

    public void deposit(int amount) {
        int currentBalance = balance;
        // Thread.yield();   // Try to force collision
        int newBalance = currentBalance + amount;
        balance = newBalance;
    }
    
    @Override
    public String toString() {
        return String.format("Account[%d] balance %d", id, balance);
    }
}


