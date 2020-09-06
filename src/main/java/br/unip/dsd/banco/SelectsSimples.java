package br.unip.dsd.banco;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SelectsSimples {


    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = BaseConnection.criaConexao();
            //STEP 4: Execute a query
            System.out.println("Criando um statement para executar as queries...");
            stmt = conn.createStatement();
            String sql = "SELECT * FROM banco_chaves.teste_simples WHERE ID =1";
            List<Long> deltas = new ArrayList<>();
            StringBuilder buff = new StringBuilder();

            for (int indice = 0; indice < 100; indice++) {
                long before = System.nanoTime();
                stmt.executeUpdate(sql);
                long after = System.nanoTime();
                buff.append("Tempo gasto na execução da query ");
                buff.append(after - before);
                buff.append("\n");
                deltas.add(after - before);
            }

            Double average = deltas.stream().mapToDouble(Long::doubleValue).average().orElse(0d);
            buff.append("Tempo medio gasto na execução foi:");
            buff.append(average);
            System.out.println(buff.toString());
        } catch (Exception se) {
            //Handle errors for JDBC
            se.printStackTrace();
        }//Handle errors for Class.forName
        finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }// do nothing
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
