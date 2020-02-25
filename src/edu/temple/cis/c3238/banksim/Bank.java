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
    public static int num_blocked2 = 0;
    public static boolean waitFunds = false;
  

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

    public synchronized boolean isOpen() {
        return open;
    }
    
    public void incNumBlocked2(){
        num_blocked2++;
    }
    public void decNumBlocked2(){
        num_blocked2--;
    }

    public void closeBank() {
        synchronized (this) {
            open = false;
            this.notifyAll();
            //System.out.println("Bank closed");
        }
        for (Account account : accounts) {
            synchronized (account) {
                account.notifyAll();
            }
        }
    }

    public void transfer(int from, int to, int amount) throws InterruptedException {

        
        
        
        if(shouldBlock){
            //System.out.println(" \n Should test here");
        }
        
        if (!open) {
            return;
        }
        
        accounts[from].waitForAvailableFunds(amount, to);
        
        
        
        if (shouldBlock) {
            synchronized (this) {
                //System.out.println("\n Num blocked: " + (num_blocked + 1) + "Num Blocked 2: " + num_blocked2 );
                if (++num_blocked == numAccounts) {
                    test();
                    
                }
                this.wait();
                
            }
        }
        

        if (accounts[from].withdraw(amount)) {
            accounts[to].deposit(amount);
        }
        

        // Uncomment line when race condition in test() is fixed.
        shouldTest();
        //}
    }

    public void test() {
        //System.out.println("\n test() reached \n");
        Thread testThread = new TestingThread(this, this.accounts, this.numAccounts, this.initialBalance);
        testThread.start();

    }

    public int getNumAccounts() {
        return numAccounts;
    }

    public synchronized boolean shouldTest() {
        
        
        //System.out.println("\n shouldTest() reached");
        if (shouldBlock) {
            return true;
        }
        if (++numTransactions % NTEST == 0) {
            shouldBlock = true;
            return true;
        }
        return false;
    }
    
    public Account[] returnAccounts(){
        
        return this.accounts;
        
    }

}
