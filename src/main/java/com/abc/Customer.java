package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    public String getName() {
        return name;
    }

    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    public String getStatement() {
        String statement = null;
        statement = BankConstant.STATEMENT.type() + " " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.sumTransactions();
        }
        statement += "\n"+BankConstant.TOTAL_IN.type() + " " + toDollars(total);
        return statement;
    }

    private String statementForAccount(Account a) {
        String s = "";

       //Translate to pretty account type
        switch(a.getAccountType()){
            case Account.CHECKING:
                s += BankConstant.CHECKING_ACCOUNT.type() + "\n";
                break;
            case Account.SAVINGS:
                s += BankConstant.SAVINGS_ACCOUNT.type() + "\n";
                break;
            case Account.MAXI_SAVINGS:
                s += BankConstant.MAXI_SAVINGS_ACCOUNT.type() + "\n";
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.transactions) {
            s += "  " + (t.amount < 0 ? BankConstant.WITHDRAWAL.type() : BankConstant.DEPOSIT.type()) + " " + toDollars(t.amount) + "\n";
            total += t.amount;
        }
        s += BankConstant.TOTAL.type() + " " + toDollars(total);
        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }

    public void transferBetweenAccounts(Account sourceAcount,Account targetAcount, double amount ) throws AbcBankException {
        sourceAcount.withdraw(amount);
        targetAcount.deposit(amount);

    }
}
