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
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author Valentino Borgo
 */
public class ProductosDAO {
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    
    
    public boolean registrarProductos(Productos pro){
        //ERROR ACA !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
        String sql = "INSERT INTO materiales (codigo, nombre, proveedor_nombre, stock, precio) VALUES (?, ?, ?, ?, ?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, pro.getCodigo());
            ps.setString(2, pro.getNombre());
            ps.setString(3, pro.getProveedor());
            ps.setInt(4, pro.getStock());
            ps.setDouble(5, pro.getPrecio());
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
    
    public void consultarProveedor(JComboBox proveedor){
        String sql = "SELECT nombre FROM proveedor";//SOLUCIONAR PROBLEMA 
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();//EL rs trae los resultados, no modifica nada
            while(rs.next()){
                proveedor.addItem(rs.getString("nombre"));//Esta linea lo que hace es agregar al JCombox el nombre del proveedor
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
    public List listarProductos(){
        List<Productos> ListaPro = new ArrayList();
        String sql = "SELECT * FROM materiales ";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);//SE PARA ARRIBA DE LA CONSULTA EN LA TALBA CLIENTES;
            rs = ps.executeQuery();//EL QUERY SOLO DEVUELVE DATOS, EN TIPO RESULT SET;Recorre las columnas de la tabla
            while(rs.next()){//El next se desplaza por la fila mostrando la informacion de cada campo
                Productos pro = new Productos();
                pro.setId(rs.getInt("id"));
                pro.setCodigo(rs.getString("codigo"));
                pro.setNombre(rs.getString("nombre"));
                pro.setProveedor(rs.getString("proveedor_nombre"));
                pro.setStock(rs.getInt("Stock"));
                pro.setPrecio(rs.getDouble("precio"));
                ListaPro.add(pro);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return ListaPro;
    }
    
    public boolean EliminarProducto(int id){
        String sql = "DELETE FROM materiales  WHERE id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e){
            System.out.println(e.toString());
            return false; 
        }finally{
            try {
                con.close();//CUANDO HAGO UNA CONSULTA DE MODIFICACION TENGO QUE HACER UN CIERRE DE LA CONECCION A LA BASE DE DATOS
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
    }
    
    
    public boolean modificarProductos(Productos pro){
        String sql = "UPDATE materiales  SET codigo = ?,nombre = ?, proveedor_nombre = ?, stock = ?, precio = ?  WHERE id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1,pro.getCodigo());
            ps.setString(2,pro.getNombre());
            ps.setString(3,pro.getProveedor());
            ps.setInt(4,pro.getStock());
            ps.setDouble(5,pro.getPrecio());
            ps.setInt(6,pro.getId());
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
    
    public Productos buscarPro(String cod){
        Productos producto = new Productos();
        String sql = "SELECT * FROM materiales  WHERE codigo = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1,cod);
            rs = ps.executeQuery();
            if(rs.next()){
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setStock(rs.getInt("stock"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return producto;
    }
    
    public Config buscarDatos(){
        Config conf = new Config();
        String sql = "SELECT * FROM configuración";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();//Solo hace una consulta y no modifica nada
            if(rs.next()){
                conf.setId(rs.getInt("id"));
                conf.setCuit(rs.getString("cuit"));
                conf.setNombre(rs.getString("nombre"));
                conf.setTelefono(rs.getInt("telefono"));
                conf.setDireccion(rs.getString("dirección"));
                conf.setRazon(rs.getString("razon"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return conf;
    }
    
    
    public boolean modificarDatos(Config conf){
        String sql = "UPDATE configuración SET nombre = ?,cuit = ?, telefono = ?, dirección = ?, razon = ?  WHERE id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1,conf.getNombre());
            ps.setString(2,conf.getCuit());
            ps.setInt(3,conf.getTelefono());
            ps.setString(4,conf.getDireccion());
            ps.setString(5,conf.getRazon());
            ps.setInt(6,conf.getId());
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
}
