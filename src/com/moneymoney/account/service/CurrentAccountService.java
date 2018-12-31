package com.moneymoney.account.service;

import java.sql.SQLException;
import java.util.List;

import com.moneymoney.account.CurrentAccount;
import com.moneymoney.exception.AccountNotFoundException;

public interface CurrentAccountService {
	//CurrentAccount createNewAccount(String accountHolderName, double accountBalance) throws ClassNotFoundException, SQLException;

//	SavingsAccount updateAccount(SavingsAccount account);

	CurrentAccount getAccountById(int accountNumber) throws ClassNotFoundException, AccountNotFoundException, SQLException;

	CurrentAccount deleteAccount(int accountNumber) throws ClassNotFoundException, SQLException;
	
	List<CurrentAccount> getAllCurrentAccount() throws ClassNotFoundException, SQLException;

	void fundTransfer(CurrentAccount sender, CurrentAccount receiver, double amount) throws ClassNotFoundException, SQLException;
	void deposit(CurrentAccount account, double amount) throws ClassNotFoundException, SQLException;
	void withdraw(CurrentAccount account, double amount) throws ClassNotFoundException, SQLException;

	CurrentAccount updateAccount(int accountNumber, int choice, String name) throws SQLException, ClassNotFoundException;

	CurrentAccount searchAccount(String name, int choice) throws ClassNotFoundException, SQLException;

	List<CurrentAccount> searchAccountBySalary(int minSalary, int maxSalary) throws ClassNotFoundException, SQLException;

	List<CurrentAccount> sortAccount( int choice, int choice2) throws ClassNotFoundException, SQLException;

	CurrentAccount createNewCurrentAccount(String accountHolderName, double accountBalance) throws ClassNotFoundException, SQLException;

}