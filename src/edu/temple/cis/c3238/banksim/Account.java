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
    private Bank myBank;


    public Account(Bank mybank, int id, int initialBalance) {
        
        this.myBank = mybank;
        this.id = id;
        this.balance = initialBalance;
    }

    public int getBalance() {
        return balance;
    }

    public synchronized boolean withdraw(int amount) {
        if (amount <= balance) {
            int currentBalance = balance;
            Thread.yield(); // Try to force collision
            int newBalance = currentBalance - amount;
            balance = newBalance;
            return true;
            
        } else {
            return false;
        }
        
    }

    public synchronized void deposit(int amount) {
        int currentBalance = balance;
        Thread.yield();   // Try to force collision
        int newBalance = currentBalance + amount;
        balance = newBalance;
        notifyAll();
    }
    
    @Override
    public String toString() {
        return String.format("Account[%d] balance %d", id, balance);
    }
    
    
    public synchronized void waitForAvailableFunds(int amount, int to) throws InterruptedException {
       
        
        myBank.incNumBlocked2();
        
        while (myBank.isOpen() && amount >= balance) {
            
            
            //System.out.println("\n Trying to get " + amount + "from account " + id + " Balance is: " + balance);
            
            
            wait();
            
          
        }
        
        myBank.decNumBlocked2();
        
    }

}

