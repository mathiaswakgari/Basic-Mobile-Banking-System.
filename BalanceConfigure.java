package com.mathias;

import java.sql.SQLException;

public class BalanceConfigure {
    private double balance;
    public void balanceReader(double balance) throws SQLException {
        this.balance = balance;
    }
    public double getBalance(){
        return this.balance;
    }
}
