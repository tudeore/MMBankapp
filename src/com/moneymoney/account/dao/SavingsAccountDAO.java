package com.moneymoney.account.dao;

import java.sql.SQLException;
import java.util.List;

import com.moneymoney.account.SavingsAccount;
import com.moneymoney.exception.AccountNotFoundException;

public interface SavingsAccountDAO {
	
	SavingsAccount createNewAccount(SavingsAccount account) throws ClassNotFoundException, SQLException;

	SavingsAccount getAccountById(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException;
	SavingsAccount deleteAccount(int accountNumber) throws ClassNotFoundException, SQLException;
	List<SavingsAccount> getAllSavingsAccount() throws ClassNotFoundException, SQLException;
	void updateBalance(int accountNumber, double currentBalance) throws ClassNotFoundException, SQLException;
	void commit() throws SQLException;
	SavingsAccount updateAccount(int accountNumber, int choice, String name) throws SQLException, ClassNotFoundException;

	SavingsAccount serachAccount(String name, int choice) throws ClassNotFoundException, SQLException;

	List<SavingsAccount> searchAccountBySalary(int minSalary, int maxSalary) throws SQLException, ClassNotFoundException;

	List<SavingsAccount> sortAccount(int choice, int choice2) throws ClassNotFoundException, SQLException;

}
