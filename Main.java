package com.mathias;

import java.sql.SQLException;
import java.util.Objects;

public class Main {

    public static void main(String[] args) throws SQLException {
        var login = new LoginPage();
        login.login();
    }
}
