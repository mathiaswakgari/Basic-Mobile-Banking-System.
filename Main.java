package com.mathias;

import java.sql.SQLException;
import java.util.Objects;

public class Main {

    public static void main(String[] args) throws SQLException {
        var console = new Console();
        var jdbc = new JDBC();
        var login = new LoginPage();
        var balanceConfigure = new BalanceConfigure();
        login.login();
        String customerName = jdbc.reader("select customer_fullname from customer, users where user_name='" + login.getUserName() +"'", "customer_fullname");
        console.consolePrintLn("Full-name: " + customerName);

        while (true){
            console.consolePrintLn("Options:\n1. Show Balance\n2. Make Transactions\n3. Quit");
            String choice = console.consoleInput("Your choice: ");
            console.consolePrintLn("----------------------------");
            String balance = jdbc.reader("select balance from customer, users where user_name='" + login.getUserName() + "'", "balance");
            if (Objects.equals(choice, "1")){
                double balanceConverted = Double.parseDouble(balance);
                balanceConfigure.balanceReader(balanceConverted);
                console.consolePrintLn("Balance: $" + new String(String.valueOf(balanceConfigure.getBalance())));
                console.consolePrintLn("----------------------------");
            }
            else if (Objects.equals(choice, "2")){
                String amount = console.consoleInput("Enter amount in numbers: ");
                double amountDouble = Double.parseDouble(amount);
                double balanceConverted = Double.parseDouble(balance);
                balanceConfigure.balanceReader(balanceConverted);
                if (amountDouble >= balanceConfigure.getBalance()){
                    console.consolePrintLn("----------------------------");
                    console.consolePrintLn("You don't have that much balance in your account. Try with legitimate amount.");
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
