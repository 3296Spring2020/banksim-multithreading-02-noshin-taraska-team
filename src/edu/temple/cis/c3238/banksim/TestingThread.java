/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.temple.cis.c3238.banksim;

class TestingThread extends Thread {

    private final Bank bank;
    private final Account[] accounts;
    private final int numAccounts;
    private final int initialBalance;
    

    public TestingThread(Bank b, Account[] Bankaccounts, int numberAccounts, int InitialBalance) {
        bank = b;
        accounts = Bankaccounts;
        numAccounts = numberAccounts;
        initialBalance = InitialBalance;
        
    }

    @Override
    public void run() {
        
        int totalBalance = 0;
        for (Account account : accounts) {
            System.out.printf("%-30s %s%n", 
                    Thread.currentThread().toString(), account.toString());
            totalBalance += account.getBalance();
        }
        System.out.printf("%-30s Total balance: %d\n", Thread.currentThread().toString(), totalBalance);
        if (totalBalance != numAccounts * initialBalance) {
            System.out.printf("%-30s Total balance changed!\n", Thread.currentThread().toString());
            //System.exit(0);
        } else {
            System.out.printf("%-30s Total balance unchanged.\n", Thread.currentThread().toString());
        }
        bank.num_blocked = 0;
        bank.shouldBlock = false;
        
        doNotify();
        
    }
    
    public void doWait(){
    synchronized(bank){
      try{
        bank.wait();
      } catch(InterruptedException e){
      }
      }
    }
  
    
    
    public void doNotify(){
    synchronized(bank){
        bank.notifyAll();
    }
  }
}
