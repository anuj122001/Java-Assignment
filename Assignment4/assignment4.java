package Assignment4;

import java.util.*;

class BankAccount {
    private String holderName;
    private int currentBalance;

    public BankAccount(String holderName, int currentBalance) {
        this.holderName = holderName;
        this.currentBalance = currentBalance;
    }

    public void withdraw(int amount, String user) {
        if (currentBalance >= amount) {
            System.out.println(user + " is withdrawing ₹" + amount);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            currentBalance -= amount;
            System.out.println(user + " successfully withdrew ₹" + amount + ". Remaining Balance: ₹" + currentBalance);
        } else {
            System.out.println(user + " attempted to withdraw ₹" + amount + " but insufficient funds. Balance: ₹"
                    + currentBalance);
        }
    }

    public synchronized void safeWithdraw(int amount, String user) {
        if (currentBalance >= amount) {
            System.out.println(user + " is withdrawing ₹" + amount);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            currentBalance -= amount;
            System.out.println(user + " successfully withdrew ₹" + amount + ". Remaining Balance: ₹" + currentBalance);
        } else {
            System.out.println(user + " attempted to withdraw ₹" + amount + " but insufficient funds. Balance: ₹"
                    + currentBalance);
        }
    }
}

class UnsafeTransaction extends Thread 
{
    private BankAccount bankAccount;
    private int withdrawalAmount;
    private String user;

    public UnsafeTransaction(BankAccount bankAccount, int withdrawalAmount, String user) {
        this.bankAccount = bankAccount;
        this.withdrawalAmount = withdrawalAmount;
        this.user = user;
    }

    @Override
    public void run() {
        bankAccount.withdraw(withdrawalAmount, user);
    }
}

class SafeTransaction extends Thread {
    private BankAccount bankAccount;
    private int withdrawalAmount;
    private String user;

    public SafeTransaction(BankAccount bankAccount, int withdrawalAmount, String user) {
        this.bankAccount = bankAccount;
        this.withdrawalAmount = withdrawalAmount;
        this.user = user;
    }

    @Override
    public void run() {
        bankAccount.safeWithdraw(withdrawalAmount, user);
    }
}

public class assignment4 {
    public static void main(String[] args) {
        System.out.println("===== Unsynchronized Withdrawal =====");
        simulateUnsafeTransactions();

        System.out.println("\n===== Synchronized Withdrawal =====");
        simulateSafeTransactions();
    }

    private static void simulateUnsafeTransactions() {
        BankAccount sharedAccount = new BankAccount("Shared Account", 1000);

        UnsafeTransaction user1 = new UnsafeTransaction(sharedAccount, 800, "User 1");
        UnsafeTransaction user2 = new UnsafeTransaction(sharedAccount, 700, "User 2");

        user1.start();
        user2.start();

        try {
            user1.join();
            user2.join();
        } catch (InterruptedException e) {
        }
    }

    private static void simulateSafeTransactions() {
        BankAccount sharedAccount = new BankAccount("Shared Account", 1000);

        SafeTransaction user1 = new SafeTransaction(sharedAccount, 800, "User 1");
        SafeTransaction user2 = new SafeTransaction(sharedAccount, 700, "User 2");

        user1.start();
        user2.start();

        try {
            user1.join();
            user2.join();
        } catch (InterruptedException e) {
        }
    }
}
