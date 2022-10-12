package com.mathias;

import java.sql.Connection;
import java.sql.*;
public class JDBC {
    public String reader(String queries, String columnLabel) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mobile_banking","root","4779");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(queries);
        resultSet.next();
        return resultSet.getString(columnLabel);
}
    public boolean loginReader(String userName, String password) throws SQLException{
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mobile_banking","root","4779");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from users where user_name='" + userName + "' and _password='" + password + "'");
        return resultSet.next();
    }
    public void updater(String queries) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mobile_banking","root","4779");
        Statement statement = connection.createStatement();
        statement.executeUpdate(queries);

    }


}

