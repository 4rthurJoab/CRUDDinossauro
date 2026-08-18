package com.template.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    // Ajuste com os dados do seu banco PostgreSQL
    private static final String URL = "jdbc:postgresql://localhost:5432/BancoDino";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}