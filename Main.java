package com.mathias;

import java.sql.SQLException;
import java.util.Locale;
import java.util.Objects;


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
                String input = console.consoleInput("Would you like to select other options(Yes(y)/No(n))?: ");
                input = input.toLowerCase(Locale.ROOT);
                if (Objects.equals(input, "n")) {
                    console.consolePrintLn();
                    break;
                }
            }
            else if (Objects.equals(choice, "2")){
                String toWhom = console.consoleInput("Enter recipient's full-name: ");
                if (Objects.equals(toWhom, customerName)) {
                    console.consolePrintLn("Transaction Failed. Can't transfer to current account from current account.");
                    String input = console.consoleInput("Would you like to select other options(Yes(y)/No(n))?: ");
                    input = input.toLowerCase(Locale.ROOT);
                    if (Objects.equals(input, "n")) {
                        console.consolePrintLn();
                        break;
                    }
                }
                else{
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
                            LocalDate transactionDate = LocalDate.now();
                            String updatedBalance = jdbc.reader("select balance from customer where user_name='" + login.getUserName() + "'", "balance");
                            console.consolePrintLn("Balance: $" + updatedBalance);
                            jdbc.updater("insert into p_transaction(transaction_date, deposit_amount, customer_depositedTo, customer_depositedFrom) values ('"+transactionDate+"','" + amountDouble +"','"+toWhom+"', '"+customerName+"')");
                            console.consolePrintLn("----------------------------");
                        }
                    }
                    else{
                        console.consolePrintLn("The Name provided could not be directed to any one. Try a valid name.");
                    }
                    String input = console.consoleInput("Would you like to select other options(Yes(y)/No(n))?: ");
                    input = input.toLowerCase(Locale.ROOT);
                }
            }
            else if(Objects.equals(choice, "3")){
                console.consolePrintLn();
                break;
            }
            else{
                console.consolePrintLn("Wrong choice, try again.");
            }
        }

    }
}
