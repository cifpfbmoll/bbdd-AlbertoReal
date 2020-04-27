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
import java.sql.Statement;

/**
 *
 * @author Alberto Real
 */
public class Querys {

    public static void query(String busqueda) throws SQLException, IOException {
        String escribir = "";
        Connection salida = conexionDB.obtenerConexion();
        Statement st = salida.createStatement();
        ResultSet resultados = st.executeQuery("select * from bar where address like " +busqueda);
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

}
