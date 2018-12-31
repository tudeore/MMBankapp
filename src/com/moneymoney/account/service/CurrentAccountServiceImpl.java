package com.moneymoney.account.service;

import java.sql.SQLException;
import java.util.List;

import com.moneymoney.account.CurrentAccount;
import com.moneymoney.account.dao.CurrentAccountDAO;
import com.moneymoney.account.dao.CurrentAccountDAOImpl;
import com.moneymoney.account.factory.AccountFactory;
import com.moneymoney.account.util.DBUtil;
import com.moneymoney.exception.AccountNotFoundException;
import com.moneymoney.exception.InsufficientFundsException;
import com.moneymoney.exception.InvalidInputException;

public class CurrentAccountServiceImpl implements CurrentAccountService {
	private AccountFactory factory;
	private CurrentAccountDAO currentAccountDAO;
	
	public CurrentAccountServiceImpl() {
		factory = AccountFactory.getInstance();
		currentAccountDAO = new CurrentAccountDAOImpl();
	}
	@Override
	public CurrentAccount createNewCurrentAccount(String accountHolderName, double accountBalance) throws ClassNotFoundException, SQLException {
		CurrentAccount account = factory.createNewCurrentAccount(accountHolderName, accountBalance);
		currentAccountDAO.createNewAccount(account);
		return null;
	}

	@Override
	public CurrentAccount getAccountById(int accountNumber)
			throws ClassNotFoundException, AccountNotFoundException, SQLException {
		return currentAccountDAO.getAccountById(accountNumber);
	}

	@Override
	public CurrentAccount deleteAccount(int accountNumber) throws ClassNotFoundException, SQLException {
		return currentAccountDAO.deleteAccount(accountNumber);
	}

	@Override
	public List<CurrentAccount> getAllCurrentAccount() throws ClassNotFoundException, SQLException {
		return currentAccountDAO.getAllCurrentAccount();
	}

	@Override
	public void fundTransfer(CurrentAccount sender, CurrentAccount receiver, double amount)
			throws ClassNotFoundException, SQLException {
		try {
			withdraw(sender, amount);
			deposit(receiver, amount);
			DBUtil.commit();
		} catch (InvalidInputException | InsufficientFundsException e) {
			e.printStackTrace();
			DBUtil.rollback();
		} catch(Exception e) {
			e.printStackTrace();
			DBUtil.rollback();
		}		
	}

	@Override
	public void deposit(CurrentAccount account, double amount) throws ClassNotFoundException, SQLException {
		if (amount > 0) {
			double currentBalance = account.getBankAccount().getAccountBalance();
			currentBalance += amount;
			currentAccountDAO.updateBalance(account.getBankAccount().getAccountNumber(), currentBalance);
			//savingsAccountDAO.commit();
		}else {
			throw new InvalidInputException("Invalid Input Amount!");
		}		
	}

	@Override
	public void withdraw(CurrentAccount account, double amount) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CurrentAccount updateAccount(int accountNumber, int choice, String name)
			throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CurrentAccount searchAccount(String name, int choice) throws ClassNotFoundException, SQLException {
		return currentAccountDAO.serachAccount(name,choice);		
	}

	@Override
	public List<CurrentAccount> searchAccountBySalary(int minSalary, int maxSalary)
			throws ClassNotFoundException, SQLException {
		return currentAccountDAO.searchAccountBySalary(minSalary,maxSalary);
	}

	@Override
	public List<CurrentAccount> sortAccount(int choice, int choice2) throws ClassNotFoundException, SQLException {
		return currentAccountDAO.sortAccount(choice,choice2);
	}

}
