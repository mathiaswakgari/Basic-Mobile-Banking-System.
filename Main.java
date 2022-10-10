package com.mathias;

import java.sql.SQLException;
import java.util.Objects;

public class Main {

    public static void main(String[] args) throws SQLException {
        var console = new Console();
        var jdbc = new JDBC();
        var login = new LoginPage();
        login.login();
        String customerName = jdbc.reader("select customer_fullname from customer, users where user_name='" + login.getUserName() +"'", "customer_fullname");
        console.consolePrintLn(customerName);
        String balance = jdbc.reader("select balance from customer, users where user_name='" + login.getUserName() + "'", "balance");
        console.consolePrintLn("Balance: $" + balance);
    }
}
