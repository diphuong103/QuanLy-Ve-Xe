package com.example.phuongbusbooking.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
        private static final String DB_URL = "jdbc:sqlite:D:/Sqlite/MyDatabase/QLVXBUS.db";
        private static Connection connection;

        public static Connection getConnection() {
            try {
                if (connection == null || connection.isClosed()) {
                    connection = DriverManager.getConnection(DB_URL);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return connection;
        }

        public static void main(String[] args) {

        }
    }
