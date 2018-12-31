package com.moneymoney.account.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.moneymoney.account.CurrentAccount;
import com.moneymoney.account.util.DBUtil;
import com.moneymoney.exception.AccountNotFoundException;

public class CurrentAccountDAOImpl implements CurrentAccountDAO {

	@Override
	public CurrentAccount createNewAccount(CurrentAccount account) throws SQLException, ClassNotFoundException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ACCOUNT VALUES(?,?,?,?,?,?)");
		preparedStatement.setInt(1, account.getBankAccount().getAccountNumber());
		preparedStatement.setString(2, account.getBankAccount().getAccountHolderName());
		preparedStatement.setDouble(3, account.
				getBankAccount().getAccountBalance());
		preparedStatement.setDouble(4, account.odLimit());
		preparedStatement.setObject(5, null);
		preparedStatement.setString(6, "CA");
		preparedStatement.executeUpdate();
		preparedStatement.close();
		DBUtil.commit();
		return account;
	}

	@Override
	public CurrentAccount getAccountById(int accountNumber)
			throws ClassNotFoundException, SQLException, AccountNotFoundException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement
				("SELECT * FROM account where accountId=?");
		preparedStatement.setInt(1, accountNumber);
		ResultSet resultSet = preparedStatement.executeQuery();
		CurrentAccount currentAccount = null;
		if(resultSet.next()) {
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			double odLimit = resultSet.getDouble("odLimit");
			currentAccount = new CurrentAccount(accountNumber, accountHolderName, accountBalance,
					odLimit);
			return currentAccount;
		}
		throw new AccountNotFoundException("Account with account number "+accountNumber+" does not exist.");
	}

	@Override
	public CurrentAccount deleteAccount(int accountNumber) throws ClassNotFoundException, SQLException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM account WHERE accountId = ?");

		preparedStatement.setInt(1, accountNumber);

		boolean deleteAccount = preparedStatement.execute();
		CurrentAccount currentAccount = null;
		DBUtil.commit();
		return null;
	}

	@Override
	public List<CurrentAccount> getAllCurrentAccount() throws ClassNotFoundException, SQLException {
		List<CurrentAccount> currentAccounts = new ArrayList<>();
		Connection connection = DBUtil.getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM ACCOUNT");
		while (resultSet.next()) {// Check if row(s) is present in table
			int accountNumber = resultSet.getInt(1);
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			double odLimit = resultSet.getDouble("odLimit");
			CurrentAccount currentAccount = new CurrentAccount(accountNumber, accountHolderName, accountBalance,
					odLimit);
			currentAccounts.add(currentAccount);
		}
		DBUtil.commit();
		return currentAccounts;	
	}

	@Override
	public void updateBalance(int accountNumber, double currentBalance) throws ClassNotFoundException, SQLException {
		Connection connection = DBUtil.getConnection();
		connection.setAutoCommit(false);
		PreparedStatement preparedStatement = connection.prepareStatement
				("UPDATE ACCOUNT SET account_bal=? where accountId=?");
		preparedStatement.setDouble(1, currentBalance);
		preparedStatement.setInt(2, accountNumber);
		preparedStatement.executeUpdate();
		
	}

	@Override
	public void commit() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CurrentAccount updateAccount(int accountNumber, int choice, String name)
			throws SQLException, ClassNotFoundException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = null;
		switch(choice){
		
		case 1:
			preparedStatement = connection.prepareStatement("UPDATE account SET account_hn =? WHERE accountId = ?");
			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, accountNumber);
			preparedStatement.executeUpdate();
			break;
			
		/*case 2:								// ask to sir
			preparedStatement = connection.prepareStatement("UPDATE account SET odLimit WHERE accountId = ?");
			
			
				preparedStatement.setDouble(1,name);
			preparedStatement.setInt(2, accountNumber);
			preparedStatement.executeUpdate();*/
		}
		DBUtil.commit();
	
		return null;
	}

	@Override
	public CurrentAccount serachAccount(String name, int choice) throws ClassNotFoundException, SQLException {
		Connection connection = DBUtil.getConnection();
		CurrentAccount currentAccount = null;
		switch(choice){
		case 1:
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM account WHERE account_hn =?");
			preparedStatement.setString(1,name);
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()) {
				int accountId = rs.getInt(1);
				String accountHolderName = rs.getString("account_hn");
				double accountBalance = rs.getDouble(3);
				double odLimit= rs.getDouble("odLimit");
				currentAccount = new CurrentAccount(accountId,accountHolderName, accountBalance,odLimit);
				DBUtil.commit();
				
				
			}
			preparedStatement.close();
			return currentAccount;
		}
		
		return null;
	}

	@Override
	public List<CurrentAccount> searchAccountBySalary(int minSalary, int maxSalary)
			throws SQLException, ClassNotFoundException {
		Connection connection = DBUtil.getConnection();
		CurrentAccount currentAccount = null;
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM account WHERE account_balance BETWEEN ? AND ?");
			preparedStatement.setInt(1,minSalary);
			preparedStatement.setInt(2,maxSalary);
			ResultSet rs = preparedStatement.executeQuery();
			int count=0;
			List<CurrentAccount> currentAccountsList = new ArrayList();
			while(rs.next()) {
				int accountId = rs.getInt(1);
				String accountHolderName = rs.getString("account_hn");
				double accountBalance = rs.getDouble(3);
				double odLimit = rs.getDouble("odLimit");
				currentAccount = new CurrentAccount(accountId,accountHolderName, accountBalance,odLimit);
				currentAccountsList.add(currentAccount);
				}
			preparedStatement.close();
			return currentAccountsList;
		
	}

	@Override
	public List<CurrentAccount> sortAccount(int choice, int choice2) throws ClassNotFoundException, SQLException {
		Connection connection = DBUtil.getConnection();
		CurrentAccount currentAccount = null;
		String query = "";
		switch(choice) {
		case 1:
			if(choice2 == 1)
			query  = "SELECT * FROM ACCOUNT ORDER BY accountId ASC";
			else
			query  = "SELECT * FROM ACCOUNT ORDER BY accountId DESC";
			break;	

		case 2:
			if(choice2 == 1)
			query  = "SELECT * FROM ACCOUNT ORDER BY account_hn ASC";
			else
			query  = "SELECT * FROM ACCOUNT ORDER BY account_hn DESC";
			break;
		case 3 :
			if(choice2 == 1)
			query  = "SELECT * FROM ACCOUNT ORDER BY account_Bal ASC";
			else
			query  = "SELECT * FROM ACCOUNT ORDER BY account_Bal DESC";
			break;
	}
		List<CurrentAccount> sortedList = new ArrayList<>();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		while (resultSet.next()) {		// Check if row(s) is present in table
			int accountNumber = resultSet.getInt(1);
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			double odLimit = resultSet.getDouble("odLimit");
			currentAccount = new CurrentAccount(accountNumber, accountHolderName, accountBalance, odLimit);
			sortedList.add(currentAccount);
		}
		statement.close();
		return (sortedList);
	
	}
	
	

}
