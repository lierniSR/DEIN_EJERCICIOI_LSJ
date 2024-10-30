package es.liernisarraoa.gestionpersonasfiltro.BBDD;

import java.sql.DriverManager;
import java.util.Properties;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Clase para la conexion a la BBDD de MariaDB.
 *
 * @author Lierni
 * @version 1.0
 */
public class ConexionBBDD {
    private final Connection conexion;

    public ConexionBBDD() throws SQLException {
        // los parametros de la conexion
        Properties connConfig = new Properties();
        connConfig.setProperty("user", "root");
        connConfig.setProperty("password", "WinRar3009*.");
        //la conexion en sí
        conexion = DriverManager.getConnection("jdbc:mariadb://127.0.0.1/personas?serverTimezone=Europe/Madrid", connConfig);
        conexion.setAutoCommit(true);
    }

    public Connection getConexion(){return this.conexion;}

    public Connection cerrarConexion(){
        try {
            conexion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conexion;
    }
}
