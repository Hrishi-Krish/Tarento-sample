package org.example;

import java.sql.*;

public class TableThread extends Thread {
    private final String tableName;
    private final String jdbcUrl;
    private final String username;
    private final String password;

    public TableThread(String tableName, String jdbcUrl, String username, String password) {
        this.tableName = tableName;
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    @Override
    public void run() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from " + tableName);

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int i=1; i<=columnCount; i++) {
                System.out.print(metaData.getColumnName(i) + "\t");
            }
            System.out.println();

            while(resultSet.next()) {
                for(int i=1; i<=columnCount; i++) {
                    Object row = resultSet.getObject(i);
                    System.out.print(row + "\t");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception\n" + e);
        } catch (Exception e) {
            System.out.println("Other Exception\n" + e);
        }
    }
}

