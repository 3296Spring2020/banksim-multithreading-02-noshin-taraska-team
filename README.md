# BankSim

## Requirements:
In this program we are supposed to protect a critical part of the code and resolve a race condition. Since threads were being run in parallel, they would be preempted by other threads causing race conditions. We solved this by creating synchronized methods inside the Account class which acts as a lock for the object. This way, while a thread is depositing or withdrawing, other threads will be blocked until the transaction is complete. This allows for parallel transactions for unrelated accounts. We also used locks on the bank for the test method that prevented transactions to occur while the test thread was being run. All requirements for this assignment have been completed.


### TASK 1
A race condition may occur when a thread becomes preempted during parallel execution because of multiple threads running at the same time.
You can see parallel execution when the race condition begins in TransferThread.java by calling the transfer method in Bank.java.
This method tries to transfer randomized amounts to and from randomized accounts by calling Account.java.

### TASK 2

So in Account.java we implemented protection code to resolve this race condition issue by naming the withdraw function and deposit
method as synchronized. This placed a lock on the individual account object which prevented other threads from depositing or withdrawing when a thread is in one of these threads.

### TASK 3

In the Bank class, the shouldTest method sets a variable named shouldBlock that indicates to the threads that they should wait on bank. The num_blocked variable indicates the number of threads that have been blocked. The final thread to block first calls the test method which launches a test thread to perform the testing. Once the testing is done, the thread calls notifyAll on the bank to wake all the threads up. It resets shouldBlock and num_blocked.

### TASK 4


### TASK 5

Once a TransferThread exits the for loop, it calls bank.closeBank(). This method sets the open boolean to false indicating that the bank is now closed. This method then notifies all threads that are possibly blocked to continue running and it loops through all the accounts and releases any locks set on them. In the transfer method, if open is false, then the thread will exit.

## Team Work
The collaboration was good as each member contributed a good amount towards the project.

#### Sarah
I did task 1, 3, 5 and worked on the README.

#### Dominic 
I did task 2, 4, testing and worked on the README.

## Testing:





## Sequence UML Diagram

![](https://github.com/3296Spring2020/banksim-multithreading-02-noshin-taraska-team/blob/newBranch/lab5UML.PNG?raw=true)


## UML Sequence Diagram- Test() 
![](https://github.com/3296Spring2020/banksim-multithreading-02-noshin-taraska-team/blob/newBranch/lab5_updated.PNG?raw=true)


