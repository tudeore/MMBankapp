package com.moneymoney.account;

public class CurrentAccount {

	private static double odLimit = 5000;
	private BankAccount bankAccount;

	public CurrentAccount(String accountHolderName, double accountBalance, double odLimit) {
		System.out.println(accountHolderName);
		System.out.println(accountBalance);
		bankAccount = new BankAccount(accountHolderName, accountBalance);
		this.odLimit = odLimit;
	}
	public CurrentAccount(String accountHolderName, double accountBalance) {
		bankAccount = new BankAccount(accountHolderName,accountBalance);
	}

	public CurrentAccount(int accountNumber, String accountHolderName, double accountBalance, double odLimit) {
		bankAccount = new BankAccount(accountNumber, accountHolderName, accountBalance);
		this.odLimit = odLimit;
	}
	public double odLimit() {
		return odLimit;
	}

	public void setodLimit(double odLimit) {
		this.odLimit = odLimit;
	}
	
	public BankAccount getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}
	@Override
	public String toString() {
		return "CurrentAccount [odLimit=" + odLimit + ", bankAccount=" + bankAccount + "]";
	}

	
}


