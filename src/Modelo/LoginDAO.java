/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Valentino Borgo
 */
public class LoginDAO {
    Connection con;
    PreparedStatement ps;//Prepara la sentencia sql para que se procese la base de datos.
    ResultSet rs;
    Conexion cn = new Conexion();
    
    public login log(String correo, String pass){
        login l = new login();
        String sql  = "SELECT * FROM usuarios WHERE correo = ? AND pass = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);//PREPARA LA CONSULTA A LA BASE DE DATOS
            ps.setString(1, correo);
            ps.setString(2, pass);
            rs = ps.executeQuery();//EJECUTA LA CONSULTA EFECTIVAMENTE, el executequery devuelve un RESULT SET
            //executeQuery() solo te permite hacer sentencias de recuperacion de informacion.
            if (rs.next()){
                l.setId(rs.getInt("id"));
                l.setNombre(rs.getString("nombre"));
                l.setCorreo(rs.getString("correo"));
                l.setPass(rs.getString("pass"));
                l.setRol(rs.getString("rol"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return l;
    }
    
    public boolean registrar(login l){
        String sql = "INSERT INTO usuarios (nombre,correo,pass,rol) VALUES (?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1,l.getNombre());
            ps.setString(2,l.getCorreo());
            ps.setString(3,l.getPass());
            ps.setString(4,l.getRol());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }
}
