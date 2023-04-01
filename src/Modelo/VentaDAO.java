/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import com.mysql.cj.jdbc.result.ResultSetImpl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Valentino Borgo
 */
public class VentaDAO {
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    int r;
    
    //Selecciona el id maximo de la venta
    public int idVenta(){
        int id = 0;
        String sql = "SELECT MAX(id) FROM ventas";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return id;
    }
    
    public int registrarVenta(Venta v){
        String sql = "INSERT INTO ventas (clientes_nombre,Vendedor,Total,fecha) VALUES (?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1,v.getCliente());
            ps.setString(2,v.getVendedor());
            ps.setDouble(3,v.getTotal());
            ps.setString(4,v.getFecha());
            ps.execute();//EL METODO EXECUTE SOLO MODIFICA LA TALBA(AGREGA O ELIMINA UNA FILA).
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return r;
    }
    public int registrarDetalle(Detalle d){
        String sql = "INSERT INTO detalle (materiales_codigo,cantidad, precio,id_venta) VALUES (?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1,d.getCod_pro());
            ps.setInt(2,d.getCantidad());
            ps.setDouble(3,d.getPrecio());
            ps.setInt(4,d.getId());
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return r;
    }
    
    public boolean actualizarStock(int cant, String cod){
        String sql = "UPDATE materiales  SET stock = ? WHERE codigo = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1,cant);
            ps.setString(2,cod);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }
    
    public List listarVentas(){
        List<Venta> ListaVenta = new ArrayList();
        String sql = "SELECT * FROM ventas";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);//SE PARA ARRIBA DE LA CONSULTA EN LA TALBA CLIENTES;
            rs = ps.executeQuery();//EL QUERY SOLO DEVUELVE DATOS, EN TIPO RESULT SET;Recorre las columnas de la tabla
            while(rs.next()){//El next se desplaza por la fila mostrando la informacion de cada campo
                Venta ven = new Venta();
                ven.setId(rs.getInt("id"));
                ven.setCliente(rs.getString("clientes_nombre"));
                ven.setVendedor(rs.getString("Vendedor"));
                ven.setTotal(rs.getDouble("Total"));
                ListaVenta.add(ven);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return ListaVenta;
    }
}
