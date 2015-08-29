/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

/**
 *
 * @author Simon
 */
public class Account {
    private long balance;

    public long getBalance() {
        return balance;
    }

    public void deposit(long amount){
        long balance = this.balance;
        try { Thread.sleep(5000); }
        catch (Exception ex) { ex.printStackTrace(); }
        balance += amount;
        this.balance = balance;
    }

    public void withdraw(long amount) {
        long balance = this.balance;
        try { Thread.sleep(5000); }
        catch (Exception ex) { ex.printStackTrace(); }
        balance -= amount;
        if (balance < 0) {
            balance -= 35; //apply fees
        }
        this.balance = balance;
    }

}
