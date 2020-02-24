package edu.temple.cis.c3238.banksim;
import java.util.*;
/**
 * @author Cay Horstmann
 * @author Modified by Paul Wolfgang
 * @author Modified by Charles Wang
 * @author Modified by Alexa Delacenserie
 * @author Modified by Tarek Elseify
 */

public class Bank {

    public static final int NTEST = 10;
    private final Account[] accounts;
    private long numTransactions = 0;
    private final int initialBalance;
    private final int numAccounts;
    private boolean open = true;
    public static boolean shouldBlock = false;
    public static int num_blocked = 0;

    
    

    public Bank(int numAccounts, int initialBalance) {
        open = true;
        this.initialBalance = initialBalance;
        this.numAccounts = numAccounts;
        accounts = new Account[numAccounts];
        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = new Account(this, i, initialBalance);
        }
        numTransactions = 0;
    }

    
    public synchronized boolean isOpen() {return open;}
    
    
    public void closeBank() {
        synchronized (this) {
            open = false;
            this.notifyAll();
        }
        for (Account account : accounts) {
            synchronized (account) {account.notifyAll();}
        }
    }

    public void transfer(int from, int to, int amount) throws  InterruptedException{
        
        if(!open){
            return;
        }
        if(shouldBlock) {
            synchronized(this) {
                if(++num_blocked == numAccounts) {
                    test();
                }
                this.wait();
            }
        }
        //accounts[from].waitForAvailableFunds(amount);
        
      
       if (accounts[from].withdraw(amount)) {
        accounts[to].deposit(amount);
       }
       
      
        // Uncomment line when race condition in test() is fixed.
         shouldTest();
         //}
    }

    public void test() {
        Thread testThread = new TestingThread(this, this.accounts, this.numAccounts, this.initialBalance);
         testThread.start();
         
    }

    public int getNumAccounts() {
        return numAccounts;
    }
    
    
    public synchronized boolean shouldTest() {
        if(shouldBlock) return true;
        if(++numTransactions % NTEST == 0) {
            shouldBlock = true;
            return true;
        }
        return false;
    }
    
}