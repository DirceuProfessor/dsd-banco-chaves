package br.unip.dsd.banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseConnection {

    static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    static final String DB_URL = "jdbc:mariadb://172.17.0.2/banco_chaves";

    static final String USUARIO = "root";
    static final String SENHA = "mypass";

    public static Connection criaConexao() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Statement stmt = null;

        Class.forName("org.mariadb.jdbc.Driver");

        //STEP 3: Open a connection
        System.out.println("Conectando ao banco...");
        conn = DriverManager.getConnection(
                DB_URL, USUARIO, SENHA);
        System.out.println("Conexao ao banco realizada com sucesso...");
        return conn;
    }
}
