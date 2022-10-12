package com.mathias;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {
        var console = new Console();
        var jdbc = new JDBC();
        var login = new LoginPage();
        var balanceConfigure = new BalanceConfigure();
        login.login();
        String customerName = jdbc.reader("select customer_fullname from customer where user_name='" + login.getUserName() +"'", "customer_fullname");
        console.consolePrintLn("Full-name: " + customerName);

        while (true) {
            console.consolePrintLn("Options:\n1. Show Balance\n2. Make Transactions\n3. Quit");
            String choice = console.consoleInput("Your choice: ");
            console.consolePrintLn("----------------------------");
            String balance = jdbc.reader("select balance from customer where user_name='" + login.getUserName() + "'", "balance");
            if (Objects.equals(choice, "1")){
                double balanceConverted = Double.parseDouble(balance);
                balanceConfigure.balanceReader(balanceConverted);
                console.consolePrintLn("Balance: $" + new String(String.valueOf(balanceConfigure.getBalance())));
                console.consolePrintLn("----------------------------");
            }
            else if (Objects.equals(choice, "2")){
                String toWhom = console.consoleInput("Enter recipients full-name: ");
                if (jdbc.userReader(toWhom)){
                    String amount = console.consoleInput("Enter amount in numbers: ");
                    double amountDouble = Double.parseDouble(amount);
                    double balanceConverted = Double.parseDouble(balance);
                    balanceConfigure.balanceReader(balanceConverted);
                    if (amountDouble >= balanceConfigure.getBalance()){
                        console.consolePrintLn("----------------------------");
                        console.consolePrintLn("You don't have that much balance in your account. Try with legitimate amount.");
                    }
                    else{
                        double deposit = balanceConfigure.getBalance() - amountDouble;
                        jdbc.updater("update customer set balance = '" + deposit + "'where user_name='" + login.getUserName() + "'");
                        jdbc.updater("update customer set balance=balance + '"+ amount +"'where customer_fullname='" + toWhom+"'");
                        console.consolePrintLn("You have deposited $" + amountDouble + " to " + toWhom + ".");
                        String updatedBalance = jdbc.reader("select balance from customer where user_name='" + login.getUserName() + "'", "balance");
                        console.consolePrintLn("Balance: $" + updatedBalance);
                        console.consolePrintLn("----------------------------");
                    }
                }
                else{
                    console.consolePrintLn("The Name provided could not be directed to any one. Try a valid name.");
                }
            }
            else if(Objects.equals(choice, "3")){
                console.consolePrintLn("Thank-you for choosing this system.\nExiting..........");
                break;
            }
            else{
                console.consolePrintLn("Wrong choice, try again.");
            }
        }

    }
}
