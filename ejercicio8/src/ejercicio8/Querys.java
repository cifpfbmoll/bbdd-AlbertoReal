/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio8;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.Scanner;

/**
 *
 * @author Alberto Real
 */
public class Querys {

    public static void query(String busqueda) throws SQLException, IOException {
        String escribir = "";
        Connection salida = conexionDB.obtenerConexion();
        Statement st = salida.createStatement();
        ResultSet resultados = st.executeQuery("select * from bar where address like " + busqueda);
        while (resultados.next()) {
            System.out.println(resultados.getString("name"));
            System.out.println(resultados.getString("address"));
            escribir += "Nombre:" + resultados.getString("name") + " Direccion:" + resultados.getString("address") + "\r\n";
        }
        try (BufferedWriter escritura = new BufferedWriter(new FileWriter("d:\\texto.txt", true))) {
            escritura.write("parametros:" + busqueda + "\r\n");
            escritura.write(escribir);
            escritura.write("\r\n");
        }
    }

    public static void queryPrepared(String busqueda) throws SQLException, IOException {
        String escribir = "";
        Connection salida = conexionDB.obtenerConexion();
        PreparedStatement st = salida.prepareStatement("select * from bar where name like ?");
        st.setString(1, busqueda);
        ResultSet resultados = st.executeQuery();
        System.out.println(resultados.getString("name"));
        System.out.println(resultados.getString("address"));
        escribir += "Nombre:" + resultados.getString("name") + " Direccion:" + resultados.getString("address") + "\r\n";
        try (BufferedWriter escritura = new BufferedWriter(new FileWriter("d:\\texto.txt", true))) {
            escritura.write("parametros:" + busqueda + "\r\n");
            escritura.write(escribir);
            escritura.write("\r\n");
        }
    }

    public static void updateQuery(String campo, String valorFinal, String valor) throws SQLException {
        Connection salida = conexionDB.obtenerConexion();
        PreparedStatement st = salida.prepareStatement("update bar set address = ? where address = ?");
        st.setString(1, valorFinal);
        st.setString(2, valor);
        int filas = (int) st.executeLargeUpdate();
        System.out.println("columnas afectadas" + filas);
    }

    public static void simple() throws SQLException {
        String[] Array = new String[2];
        for (int i = 0; i < 2; i++) {
            System.out.println("campo a modificar");
            Scanner entrada = new Scanner(System.in);
            String campo = entrada.nextLine();
            System.out.println("nuevo valor");
            String valor = entrada.nextLine();
            Array[i] = "update bar set address =  " + "'" + valor + "'" + " where address = " + "'" + campo + "'";
        }
        transaccion(Array);
    }
    public static void transaccion1(int tipo) throws SQLException {
        String[] Array = new String[3];
        for (int i = 0; i < 3; i++) {
            System.out.println("campo a modificar");
            Scanner entrada = new Scanner(System.in);
            String campo = entrada.nextLine();
            System.out.println("nuevo valor");
            String valor = entrada.nextLine();
            Array[i] = "update bar set address =  " + "'" + valor + "'" + " where address = " + "'" + campo + "'";
        }
        if (tipo==1){
        transaccionMasQuery(Array);
        }else{
        transaccionMasSavepoint(Array);    
        }
    }

    public static void transaccion(String[] entrada) throws SQLException {
        Connection salida = conexionDB.obtenerConexion();
        try {
            Statement st = salida.createStatement();
            salida.setAutoCommit(false);
            int filas = 0;
            for (int i = 0; i < entrada.length; i++) {
                filas += st.executeUpdate(entrada[i]);
            }
            System.out.println("filas actualizadas " + filas);
            salida.commit();
        } catch (SQLException ex) {
            System.out.println("sqlstate " + ex.getSQLState() + " sql mensage " + ex.getMessage());
            System.out.println("roll back");
            salida.rollback();
        } finally {
            salida.setAutoCommit(true);
            salida.close();
        }
    }
        public static void transaccionMasQuery(String[] entrada) throws SQLException {
        Connection salida = conexionDB.obtenerConexion();
        try {
            Statement st = salida.createStatement();
            salida.setAutoCommit(false);
            int filas = 0;
            for (int i = 0; i < entrada.length; i++) {
                filas += st.executeUpdate(entrada[i]);
            }
            System.out.println("filas actualizadas " + filas);
            salida.commit();
        } catch (SQLException ex) {
            System.out.println("sqlstate " + ex.getSQLState() + " sql mensage " + ex.getMessage());
            System.out.println("roll back");
            salida.rollback();
        } finally {         
            salida.setAutoCommit(true);
            Statement st2 = salida.createStatement();
            st2.execute("update bar set address = 'fuera del comit where' address = '108 Morris Street'");
            salida.close();
        }
    }
        public static void transaccionMasSavepoint(String[] entrada) throws SQLException {
        Connection salida = conexionDB.obtenerConexion();
        Savepoint svpnt =salida.setSavepoint();
        try {
            Statement st = salida.createStatement();
            salida.setAutoCommit(false);
            int filas = 0;
            try{
            filas += st.executeLargeUpdate(entrada[0]);
            filas += st.executeLargeUpdate(entrada[1]);
            }catch (SQLException ex){
                System.out.println("sqlstate " + ex.getSQLState() + " sql mensage " + ex.getMessage());
            }
            svpnt = salida.setSavepoint();
            filas += st.executeLargeUpdate(entrada[2]);
            System.out.println("filas actualizadas " + filas);
            salida.commit();
        } catch (SQLException ex) {
            System.out.println("sqlstate " + ex.getSQLState() + " sql mensage " + ex.getMessage());
            System.out.println("to save");
            salida.rollback(svpnt);
            salida.commit();
        } finally {
            salida.setAutoCommit(true);
            salida.close();
        }
    }
}
