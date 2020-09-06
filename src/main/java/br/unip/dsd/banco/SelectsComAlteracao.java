package br.unip.dsd.banco;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SelectsComAlteracao {
    public static void main(String[] args) {
        Connection conn = null;
        Statement st = null;
        try {
            conn = BaseConnection.criaConexao();
            final Connection conn3 = conn;
            final Connection conn2 = BaseConnection.criaConexao();
            st = conn.createStatement();
            Thread select  = new Thread(){
                @Override
                public void run() {
                    super.run();
                    try {
                        Statement st = conn3.createStatement();

                        List<Long> deltas = new ArrayList<>();
                        List<Long> resultSetDeltas = new ArrayList<>();
                        StringBuffer buff = new StringBuffer();
                        int mod = 5;
                        int id = 1;
                        for (int indice = 0; indice < 12000; indice++) {
//                            if(indice>5){
//                                id = 1+ indice % mod;
//                            }
//                            System.out.println(id);
                            String sql = "SELECT * FROM banco_chaves.teste_simples WHERE ID ="+id;
                            long before = System.nanoTime();
                            ResultSet rs = st.executeQuery(sql);
                            long after = System.nanoTime();
                            buff.append("Tempo gasto na execução da query ");
                            buff.append(after - before);
                            buff.append("\n");
                            deltas.add(after - before);
                            before = System.nanoTime();
                            rs.next();
                            String result = rs.getString(2);
                            after = System.nanoTime();
                            System.out.println("Resultado "+result);
                            buff.append("Tempo gasto na execução da query ");
                            buff.append(after - before);
                            buff.append("\n");
                            resultSetDeltas.add(after - before);
                        }
                        buff = new StringBuffer();
                        Double average = deltas.stream().mapToDouble(Long::doubleValue).average().orElse(0d);
                        buff.append("Tempo medio gasto na execução do select com update foi:");
                        buff.append(average);
                        buff.append("\n");
                        average = resultSetDeltas.stream().mapToDouble(Long::doubleValue).average().orElse(0d);
                        buff.append("Tempo medio gasto na extração dos resuldados do select com update foi:");
                        buff.append(average);
                        System.out.println(buff.toString());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            };
            Thread update  = new Thread(){
                @Override
                public void run() {
                    super.run();
                    try {
                        Statement st = conn2.createStatement();
                        List<Long> deltas = new ArrayList<Long>();
                        for(int indice = 0; indice < 1000; indice++){
                            StringBuffer buff = new StringBuffer();
                            buff.append("update banco_chaves.teste_simples set nome='nome");
                            buff.append(indice);
                            buff.append("' WHERE ID =1");
                            long before = System.nanoTime();
                            int res = st.executeUpdate(buff.toString());
                            long after = System.nanoTime();

                            deltas.add(after - before);

                        }
//                        deltas.stream().forEach(System.out::println);
                        Double average = deltas.stream().mapToDouble(Long::doubleValue).average().orElse(0d);
                        System.out.println("A média de tempo de update foi "+average);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            };
            update.start();
            //59671 - 4734 | 49913 - 4539
            select.start();   //135326 - 114418 - 110325 - 116532 - 124272 - 86782

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
