package es.liernisarraoa.gestionpersonasfiltro.Dao;

import es.liernisarraoa.gestionpersonasfiltro.BBDD.ConexionBBDD;
import es.liernisarraoa.gestionpersonasfiltro.Modelo.Personas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Clase para las consultas de la base de datos.
 *
 * @author Lierni
 * @version 1.0
 */
public class DaoPersonas {
    private static ConexionBBDD conexion;

    /**
     * Metodo que obtiene una lista de los datos de la base de datos.
     *
     * @return ObservableList de personas que es un listado de personas de la bbdd.
     */
    public static ObservableList<Personas> cargarListado(){

        ObservableList<Personas> listaPersonas = FXCollections.observableArrayList();
        try {
            conexion = new ConexionBBDD();
            String sqlConsulta = "SELECT * FROM personas.persona";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(sqlConsulta);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                Personas p = new Personas(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getInt(4));
                listaPersonas.add(p);
            }
            rs.close();
            conexion.cerrarConexion();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listaPersonas;
    }

    /**
     * Metodo que añade una persona a la base de datos
     *
     * @return boolean true si se ha añadido false si no ha podido ser.
     */
    public static boolean aniadirPersona(Personas p){
        try {
            conexion = new ConexionBBDD();
            String sqlInsert = "INSERT INTO personas.persona(nombre, apellidos, edad) VALUES (?,?,?)";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(sqlInsert);
            pstmt.setString(1, p.getNombre());
            pstmt.setString(2, p.getApellido());
            pstmt.setInt(3, p.getEdad());
            int lineasAgregadas = pstmt.executeUpdate();
            pstmt.close();
            conexion.cerrarConexion();
            return lineasAgregadas > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo que modifica una persona a la base de datos
     *
     * @return boolean true si se ha modificado false si no ha podido ser.
     */
    public static boolean modificarPersona(Personas p, String nombre, String apellido, Integer edad){
        try {
            conexion = new ConexionBBDD();
            String sqlUpdate = "UPDATE personas.persona SET nombre = ?, apellidos = ?, edad = ? WHERE id = ?";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(sqlUpdate);
            pstmt.setString(1, nombre);
            pstmt.setString(2, apellido);
            pstmt.setInt(3, edad);
            pstmt.setInt(4, p.getId());
            int lineasAfectadas = pstmt.executeUpdate();
            pstmt.close();
            conexion.cerrarConexion();
            return lineasAfectadas > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo que elimina una persona a la base de datos
     *
     */
    public static void eliminarPersona(Personas p){
        String sql = "DELETE FROM personas.persona WHERE id = ?";
        try {
            conexion = new ConexionBBDD();
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(sql);
            pstmt.setInt(1, p.getId());
            int lineasEliminadas = pstmt.executeUpdate();
            conexion.cerrarConexion();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
