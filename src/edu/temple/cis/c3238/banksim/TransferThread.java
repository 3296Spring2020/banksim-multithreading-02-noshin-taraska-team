package edu.temple.cis.c3238.banksim;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Cay Horstmann
 * @author Modified by Paul Wolfgang
 * @author Modified by Charles Wang
 * @author Modified by Alexa Delacenserie
 * @author Modified by Tarek Elseify
 */
class TransferThread extends Thread {

    private final Bank bank;
    private final int fromAccount;
    private final int maxAmount;

    public TransferThread(Bank b, int from, int max) {
        bank = b;
        fromAccount = from;
        maxAmount = max;
    }

    @Override
    public void run() {
   
        
        
        for (int i = 0; bank.isOpen() && i < 10000; i++) {
            int toAccount = (int) (bank.getNumAccounts() * Math.random());
            int amount = (int) (maxAmount * Math.random());
            try {
                bank.transfer(fromAccount, toAccount, amount);
            } catch (InterruptedException ex) {
                Logger.getLogger(TransferThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        bank.closeBank();


       
         
        System.out.printf("%-30s Account[%d] has finished with its transactions.\n", Thread.currentThread().toString(), fromAccount);
        
        
    }

    
    public void doWait(){
    synchronized(bank){
      try{
        
        bank.wait();
      } catch(InterruptedException e){
      }
      }
    }
    
    public void doNotify() {
        synchronized (bank) {
            System.out.println("2");
            bank.notifyAll();
        }
    }
}