/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author TAFITA
 */
public class ConnexionBdd {
    public static Connection connexionOracle(String user, String mdp, String database)
    throws Exception {
        Connection con=null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:"+database,user, mdp); 
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        } catch(SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return con;
    }

    public static Connection connexionPostgress(String user, String mdp, String database)
    throws Exception {
        Connection con=null;
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+database,user,mdp); 
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        } catch(SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return con;
    }

    public static Connection connexionMySQL(String user, String mdp, String database) throws Exception {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String jdbcUrl = "jdbc:mysql://localhost:3306/" + database;
            con = DriverManager.getConnection(jdbcUrl, user, mdp);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return con;
    }

    public static Connection connexionBdd(String sgbd, String user, String mdp, String database)
    throws Exception {
        if(sgbd.compareToIgnoreCase("oracle")==0) {
            return ConnexionBdd.connexionOracle(user, mdp, database);
        } else if(sgbd.compareToIgnoreCase("Postgre")==0) {
            return ConnexionBdd.connexionPostgress(user, mdp, database);
        } else if(sgbd.compareToIgnoreCase("My")==0) {
            return ConnexionBdd.connexionMySQL(user, mdp, database);
        }
        throw new Exception("Veuillez entrer une sgbd existante");
    }
}
