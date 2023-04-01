/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ProveedorDAO {
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    
    public boolean registrarProveedor(Proveedor pr){
        String sql = "INSERT INTO proveedor(cuit, nombre, telefono, dirección, razon) VALUES (?, ?, ?, ?, ?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, pr.getCuit());//Estos metodos lo que hacen es registrar en la base de datos la info que nosotros ponemos en la interfaz del programa
            ps.setString(2, pr.getNombre());
            ps.setInt(3, pr.getTelefono());
            ps.setString(4, pr.getDireccion());
            ps.setString(5, pr.getRazon());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
    public List listarProveedor(){
        List<Proveedor> listaProveedor = new ArrayList();
        String sql = "SELECT * FROM proveedor";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while( rs.next()){
                Proveedor pr = new Proveedor();
                pr.setId(rs.getInt("id"));
                pr.setCuit(rs.getString("cuit"));
                pr.setNombre(rs.getString("nombre"));
                pr.setTelefono(rs.getInt("telefono"));
                pr.setDireccion(rs.getString("dirección"));
                pr.setRazon(rs.getString("razon"));
                listaProveedor.add(pr);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return listaProveedor;
    }
    
    public boolean eliminarProveedor(int id){
        String sql = "DELETE FROM proveedor WHERE id = ?";
         try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
             System.out.println(e.toString());
             return false;
        }finally{//La palabra reservada finaly sirve para no consumir tantos recursos
             try {
                 con.close();
             } catch (SQLException e) {
                 System.out.println(e.toString());
             }
         }
    }
    public boolean modificarProveedor(Proveedor pr){
        String sql = "UPDATE proveedor SET cuit = ?, nombre = ?, telefono = ?, dirección = ?,razon = ? WHERE id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, pr.getCuit());
            ps.setString(2, pr.getNombre());
            ps.setInt(3, pr.getTelefono());
            ps.setString(4, pr.getDireccion());
            ps.setString(5, pr.getRazon());
            ps.setInt(6, pr.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }finally{
            try {
                con.close();
            } catch (SQLException ee) {
                System.out.println(ee.toString());
            }
        }
    }
}
