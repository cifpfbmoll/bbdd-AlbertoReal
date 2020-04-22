/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio8;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author Alberto Real
 */
public class Ejercicio8 {

    /**
     * @param args the command line arguments
     */
    public static void mostrarMenu() throws SQLException, IOException {
        Scanner entrada = new Scanner(System.in);
        System.out.println("1 consultas\n"+
                "2 actualizacion");
        int menu = entrada.nextInt();
        switch (menu) {
            case 1: {
                mostrarMenuQuery();
            }case 2:{
                
            }
        }
    }

    public static void mostrarMenuQuery() throws SQLException, IOException {
        Scanner entrada = new Scanner(System.in);
        System.out.println("1 consultas\n"
                + "2 consulta clave primaria");
        int menu = entrada.nextInt();

        switch (menu) {
            case 1: {
                System.out.println("introduce el parametro de busqueda");
                Scanner entrada2 = new Scanner(System.in);
                String busqueda = entrada2.nextLine();
                String query = "'%" + busqueda + "%'";
                Querys.query(query);
                mostrarMenuQuery();
                break;
            }
            case 2: {
                System.out.println("introduce el parametro de busqueda");
                Scanner entrada2 = new Scanner(System.in);
                String busqueda = entrada2.nextLine();
                String query = "'%" + busqueda + "%'";
                Querys.query(query);
                mostrarMenuQuery();
                break;
            }
        }
    }

    public static void main(String[] args) throws SQLException, IOException {

        mostrarMenu();
    }

}
