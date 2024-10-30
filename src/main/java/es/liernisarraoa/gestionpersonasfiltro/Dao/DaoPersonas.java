package es.liernisarraoa.gestionpersonasfiltro.Dao;

import es.liernisarraoa.gestionpersonasfiltro.BBDD.ConexionBBDD;
import es.liernisarraoa.gestionpersonasfiltro.Modelo.Personas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoPersonas {

    public static ObservableList<Personas> cargarListado(){

        ConexionBBDD conexion = null;
        ObservableList<Personas> listaPersonas = FXCollections.observableArrayList();
        try {
            conexion = new ConexionBBDD();
            String sqlConsulta = "SELECT nombre, apellidos, edad FROM personas.persona";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(sqlConsulta);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                Personas p = new Personas(rs.getString(1), rs.getString(2), rs.getInt(3));
                listaPersonas.add(p);
            }
            rs.close();
            conexion.cerrarConexion();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listaPersonas;
    }

    public static boolean aniadirPersona(String nombre, String apellidos, Integer edad){
        ConexionBBDD conexion;
        try {
            conexion = new ConexionBBDD();
            String sqlConsulta = "INSERT INTO personas.persona(nombre, apellidos, edad) VALUES (?,?,?)";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(sqlConsulta);
            pstmt.setString(1, nombre);
            pstmt.setString(2, apellidos);
            pstmt.setInt(3, edad);
            int lineasAgregadas = pstmt.executeUpdate();
            pstmt.close();
            conexion.cerrarConexion();
            return lineasAgregadas > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
