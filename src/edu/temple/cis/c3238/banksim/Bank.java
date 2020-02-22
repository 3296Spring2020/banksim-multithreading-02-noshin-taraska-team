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
    
    

    public Bank(int numAccounts, int initialBalance) {
        this.initialBalance = initialBalance;
        this.numAccounts = numAccounts;
        accounts = new Account[numAccounts];
        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = new Account(i, initialBalance);
        }
        numTransactions = 0;
    }

    public void transfer(int from, int to, int amount) throws  InterruptedException{
        // accounts[from].waitForAvailableFunds(amount);
        
      
       if (accounts[from].withdraw(amount)) {
        accounts[to].deposit(amount);
       }
        
        // Uncomment line when race condition in test() is fixed.
         if (shouldTest()) test();
    }

    public void test() {
        Thread testThread = new TestingThread(this, this.accounts, this.numAccounts, this.initialBalance);
        testThread.start();
    }

    public int getNumAccounts() {
        return numAccounts;
    }
    
    
    public boolean shouldTest() {
        return ++numTransactions % NTEST == 0;
    }

}
