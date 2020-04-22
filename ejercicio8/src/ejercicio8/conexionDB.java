/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ejercicio8;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 
 * @author Alberto Real
 */
public class conexionDB {
    public static Connection obtenerConexion() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/java";
        return DriverManager.getConnection(url, "root", "");
    }
}
