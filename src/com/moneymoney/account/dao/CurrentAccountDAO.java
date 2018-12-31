package com.moneymoney.account.dao;

import java.sql.SQLException;
import java.util.List;

import com.moneymoney.account.CurrentAccount;
import com.moneymoney.exception.AccountNotFoundException;

public interface CurrentAccountDAO {

	public CurrentAccount createNewAccount(CurrentAccount account) throws SQLException, ClassNotFoundException;

	CurrentAccount getAccountById(int accountNumber)
			throws ClassNotFoundException, SQLException, AccountNotFoundException;

	CurrentAccount deleteAccount(int accountNumber) throws ClassNotFoundException, SQLException;

	List<CurrentAccount> getAllCurrentAccount() throws ClassNotFoundException, SQLException;

	void updateBalance(int accountNumber, double currentBalance) throws ClassNotFoundException, SQLException;

	void commit() throws SQLException;

	CurrentAccount updateAccount(int accountNumber, int choice, String name)
			throws SQLException, ClassNotFoundException;

	CurrentAccount serachAccount(String name, int choice) throws ClassNotFoundException, SQLException;

	List<CurrentAccount> searchAccountBySalary(int minSalary, int maxSalary)
			throws SQLException, ClassNotFoundException;

	List<CurrentAccount> sortAccount(int choice, int choice2) throws ClassNotFoundException, SQLException;

}
