package com.mathias;

import java.sql.SQLException;
import java.util.Objects;

public class LoginPage {
    private String userName;
    private String password;

    public void login() throws SQLException {
        var jdbc = new JDBC();
        var console = new Console();
        console.consolePrintLn("----------------------------");
        while (true){
            this.userName = console.consoleInput("Enter Username: ");
            this.password = console.consoleInput("Enter Password: ");
            console.consolePrintLn("----------------------------");
            if(jdbc.loginReader(userName, password)){
                console.consolePrintLn("Welcome, " + userName);
                break;
            }
            else
                console.consolePrintLn("Incorrect username or password. Try Again.");



    }
}}
