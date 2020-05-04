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
     * "los cambios del ejercio 2 se realizan sobre el campo address de la tabla bar"
     * ejercicio 2 b 1 en caso de fallar cualquiera de las tres consultas
     * no se realiza niguna y se ejecuta un roollback
     * ejercicio 2 b 2 en caso de fallar la 4 consulta que no esta en la transaccion
     * y puesto que ya se an ejecutado las anteriores las anteriores quedan realizadas
     * y la 4 no
     * ejercicio 2 b 3 se guardaran las 4 consultas 
     * ejercicio 3 en caso de fallar la tercera consulta se gurdan las dos primeras gracias 
     * al save point en cambio si falla la segunda no se modifica nada
     */
    /**
     * @param args the command line arguments
     */
    public static void mostrarMenu() throws SQLException, IOException {
        Scanner entrada = new Scanner(System.in);
        System.out.println("1 consultas\n"+
                "2 actualizacion\n"
                + "3 transacciones");
        int menu = entrada.nextInt();
        switch (menu) {
            case 1: {
                mostrarMenuQuery();
            }case 2:{
                try{
                System.out.println("campo a modificar");
                Scanner entrada2 = new Scanner(System.in);
                String campo = entrada2.nextLine();
                System.out.println("valor a modificar");
                String valor = entrada2.nextLine();
                System.out.println("nuevo valor");
                String valorFinal = entrada2.nextLine();
                Querys.updateQuery(campo, valorFinal, valor);
                }catch(SQLException ex){
                    System.out.println(ex.getErrorCode());
                    System.out.println(ex.getSQLState());
                }
                mostrarMenu();
                break;
            }case 3:{
                transacciones();
                mostrarMenu();
                break;
            }
        }
    }

    public static void mostrarMenuQuery() throws SQLException, IOException {
        Scanner entrada = new Scanner(System.in);
        System.out.println("1 consultas\n"
                + "2 consulta clave primaria\n");
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
    public static void transacciones() throws SQLException{
        System.out.println("1 actualizacion simple\n"
                + "2 transaccion 1\n"
                + "3 transaccion 2");
        Scanner entrada = new Scanner(System.in);
        int menu = entrada.nextInt();
        switch(menu){
            case 1:{
                Querys.simple();
                transacciones();
                break;
            }case 2:{
                Querys.transaccion1(1);
                transacciones();
                break;
            }case 3:{
                Querys.transaccion1(0);
                transacciones();
                break;
            }
        }
    }

    public static void main(String[] args) throws SQLException, IOException {

        mostrarMenu();
    }

}
