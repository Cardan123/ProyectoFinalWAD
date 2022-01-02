/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.modelo.dao;


import com.ipn.mx.modelo.dto.GraficaDTO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos Villena
 */
public class GraficaDAO {

    private Connection conexion;

    private static final String SQL_GRAFICAR = "select nombreMarca, count(*) as cantidad from Marcas inner join Carros on Marcas.idmarca = Carros.idmarca group by nombremarca;";

    private void obtenerConexion() {
        String user = "udtgyvrffzmoif";
        String password = "99fad350c1ebe63778407aaa771b9ec8be2251eb7079ef9ed791dcf5f80425af";
        String url = "jdbc:postgresql://ec2-52-54-167-8.compute-1.amazonaws.com/deja1jvn8k993";
        String driverPostgreSql = "org.postgresql.Driver";

        try {
            Class.forName(driverPostgreSql);
            conexion = DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(GraficaDAO.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public List obtenerDatosGrafica() throws SQLException {
        obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List list = new ArrayList();

        try {
            ps = conexion.prepareStatement(SQL_GRAFICAR);
            rs = ps.executeQuery();
            while(rs.next()){
                GraficaDTO dto = new GraficaDTO();
                dto.setNombreCarrosString(rs.getString("nombreMarca"));
                dto.setCantidad(rs.getInt("cantidad"));
                list.add(dto);
            }
        } finally {
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conexion != null) conexion.close();
        }

        return list;
    }

    public static void main(String[] args) {
        GraficaDAO dao = new GraficaDAO();
        try {
            System.out.println(dao.obtenerDatosGrafica());
        } catch (SQLException ex) {
            Logger.getLogger(GraficaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
