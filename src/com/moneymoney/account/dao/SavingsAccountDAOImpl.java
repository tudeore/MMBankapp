package com.moneymoney.account.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.moneymoney.account.SavingsAccount;
import com.moneymoney.account.util.DBUtil;
import com.moneymoney.exception.AccountNotFoundException;

public class SavingsAccountDAOImpl implements SavingsAccountDAO {

	public SavingsAccount createNewAccount(SavingsAccount account) throws ClassNotFoundException, SQLException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ACCOUNT VALUES(?,?,?,?,?,?)");
		preparedStatement.setInt(1, account.getBankAccount().getAccountNumber());
		preparedStatement.setString(2, account.getBankAccount().getAccountHolderName());
		preparedStatement.setDouble(3, account.getBankAccount().getAccountBalance());
		preparedStatement.setBoolean(4, account.isSalary());
		preparedStatement.setObject(5, null);
		preparedStatement.setString(6, "SA");
		preparedStatement.executeUpdate();
		preparedStatement.close();
		DBUtil.commit();
		return account;
	}

	public List<SavingsAccount> getAllSavingsAccount() throws ClassNotFoundException, SQLException {
		List<SavingsAccount> savingsAccounts = new ArrayList<>();
		Connection connection = DBUtil.getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM ACCOUNT");
		while (resultSet.next()) {// Check if row(s) is present in table
			int accountNumber = resultSet.getInt(1);
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			boolean salary = resultSet.getBoolean("isSalary");
			SavingsAccount savingsAccount = new SavingsAccount(accountNumber, accountHolderName, accountBalance,
					salary);
			savingsAccounts.add(savingsAccount);
		}
		DBUtil.commit();
		return savingsAccounts;
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
	public SavingsAccount getAccountById(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement
				("SELECT * FROM account where accountId=?");
		preparedStatement.setInt(1, accountNumber);
		ResultSet resultSet = preparedStatement.executeQuery();
		SavingsAccount savingsAccount = null;
		if(resultSet.next()) {
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			boolean salary = resultSet.getBoolean("isSalary");
			savingsAccount = new SavingsAccount(accountNumber, accountHolderName, accountBalance,
					salary);
			return savingsAccount;
		}
		throw new AccountNotFoundException("Account with account number "+accountNumber+" does not exist.");
	}
	
	/*public SavingsAccount updateAccount(SavingsAccount account) {
		// TODO Auto-generated method stub
		return null;
	}*/

	

	@Override
	public SavingsAccount deleteAccount(int accountNumber) throws ClassNotFoundException, SQLException  {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM account WHERE accountId = ?");

		preparedStatement.setInt(1, accountNumber);

		boolean deleteAccount = preparedStatement.execute();
		SavingsAccount savingAccount = null;
		DBUtil.commit();
		return null;
	}

	@Override
	public void commit() throws SQLException {
		DBUtil.commit();
	}

	@Override
	public SavingsAccount updateAccount(int accountNumber, int choice, String name) throws SQLException, ClassNotFoundException 
	{
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = null;
		switch(choice){
		
		case 1:
			preparedStatement = connection.prepareStatement("UPDATE account SET account_hn =? WHERE accountId = ?");
			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, accountNumber);
			preparedStatement.executeUpdate();
			break;
			
		case 2:
			preparedStatement = connection.prepareStatement("UPDATE account SET isSalary =? WHERE accountId = ?");
			
			if(name.equals("y"))
				preparedStatement.setBoolean(1,true);
			else
				preparedStatement.setBoolean(1,false);
			preparedStatement.setInt(2, accountNumber);
			preparedStatement.executeUpdate();
		}
		DBUtil.commit();
	
		return null;
	}

	@Override
	public SavingsAccount serachAccount(String name, int choice) throws ClassNotFoundException, SQLException {
		Connection connection = DBUtil.getConnection();
		SavingsAccount savingsAccount = null;
		switch(choice){
		case 1:
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM account WHERE account_hn =?");
			preparedStatement.setString(1,name);
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()) {
				int accountId = rs.getInt(1);
				String accountHolderName = rs.getString("account_hn");
				double accountBalance = rs.getDouble(3);
				boolean salary = rs.getBoolean("isSalary");
				savingsAccount = new SavingsAccount(accountId,accountHolderName, accountBalance,salary);
				DBUtil.commit();
				
				
			}
			preparedStatement.close();
			return savingsAccount;
		}
		
		return null;
	}

	@Override
	public  List<SavingsAccount> searchAccountBySalary(int minSalary, int maxSalary) throws SQLException, ClassNotFoundException 
	{
		Connection connection = DBUtil.getConnection();
		SavingsAccount savingsAccount = null;
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM account WHERE account_balance BETWEEN ? AND ?");
			preparedStatement.setInt(1,minSalary);
			preparedStatement.setInt(2,maxSalary);
			ResultSet rs = preparedStatement.executeQuery();
			int count=0;
			List<SavingsAccount> savingAccountsList = new ArrayList();
			while(rs.next()) {
				int accountId = rs.getInt(1);
				String accountHolderName = rs.getString("account_hn");
				double accountBalance = rs.getDouble(3);
				boolean salary = rs.getBoolean("isSalary");
				savingsAccount = new SavingsAccount(accountId,accountHolderName, accountBalance,salary);
				savingAccountsList.add(savingsAccount);
				}
			preparedStatement.close();
			return savingAccountsList;
	}

	@Override
	public List<SavingsAccount> sortAccount(int choice,int  choice2) throws ClassNotFoundException, SQLException {		
		Connection connection = DBUtil.getConnection();
		SavingsAccount savingsAccount = null;
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
		List<SavingsAccount> sortedList = new ArrayList<>();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		while (resultSet.next()) {		// Check if row(s) is present in table
			int accountNumber = resultSet.getInt(1);
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			boolean salary = resultSet.getBoolean("isSalary");
			savingsAccount = new SavingsAccount(accountNumber, accountHolderName, accountBalance,salary);
			sortedList.add(savingsAccount);
		}
		statement.close();
		return (sortedList);
	
	}
	
}