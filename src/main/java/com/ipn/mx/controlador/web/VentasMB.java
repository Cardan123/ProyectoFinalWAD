/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.ipn.mx.controlador.web;

import static com.ipn.mx.controlador.web.BaseBean.ACC_ACTUALIZAR;
import static com.ipn.mx.controlador.web.BaseBean.ACC_CREAR;
import com.ipn.mx.modelo.dao.AgenciasDAO;
import com.ipn.mx.modelo.dao.CarrosDAO;
import com.ipn.mx.modelo.dao.VendedorDAO;
import com.ipn.mx.modelo.dao.VentasDAO;
import com.ipn.mx.modelo.entidades.Agencias;
import com.ipn.mx.modelo.entidades.Carros;
import com.ipn.mx.modelo.entidades.Vendedor;
import com.ipn.mx.modelo.entidades.Ventas;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

/**
 *
 * @author cardan_mac
 */
@ManagedBean(name = "ventasMB")
@SessionScoped
public class VentasMB extends BaseBean implements Serializable {

    private final VentasDAO dao = new VentasDAO();
    private final VendedorDAO venDAO = new VendedorDAO();
    private final CarrosDAO carDAO = new CarrosDAO();    
    private final AgenciasDAO ageDAO = new AgenciasDAO();    
    
    private Ventas dto;
    private List<Ventas> listaVentas;
    private List<Vendedor> listaVendedor;
    private List<Carros> listaCarros;
    private List<Agencias> listaAgencias;
    
    /**
     * Creates a new instance of VentasMB
     */
    public VentasMB() {
    }
    
    public Ventas getDto() {
        return dto;
    }

    public void setDto(Ventas dto) {
        this.dto = dto;
    }

    public List<Ventas> getListaVentas() {
        return listaVentas;
    }

    public void setListaVentas(List<Ventas> listaVentas) {
        this.listaVentas = listaVentas;
    }
    
    public List<Vendedor> getListaVendedor() {
        return listaVendedor;
    }

    public void setListaVendedor(List<Vendedor> listaVendedor) {
        this.listaVendedor = listaVendedor;
    }

    public List<Carros> getListaCarros() {
        return listaCarros;
    }

    public void setListaCarros(List<Carros> listaCarros) {
        this.listaCarros = listaCarros;
    }
    
    public List<Agencias> getListaAgencias() {
        return listaAgencias;
    }

    public void setListaAgencias(List<Agencias> listaAgencias) {
        this.listaAgencias = listaAgencias;
    }
    
    @PostConstruct
    public void init(){
        listaVentas = new ArrayList<>();
        listaVentas = dao.readAll();
        
        listaVendedor = new ArrayList<>();
        listaVendedor = venDAO.readAll();
        
        listaCarros = new ArrayList<>();
        listaCarros = carDAO.readAll();
        
        listaAgencias = new ArrayList<>();
        listaAgencias = ageDAO.readAll();
    }
    
    public String prepareAdd(){
        dto = new Ventas();
        setAccion(ACC_CREAR);
        return "/ventas/ventasForm?faces-redirect=true";
    }
    
    public String prepareUpdate(){
        setAccion(ACC_ACTUALIZAR);
        return "/ventas/ventasForm?faces-redirect=true";
    }
    
    public String prepareIndex(){
        init();
        return "/ventas/listadoVentas?faces-redirect=true";
    }
    
    public boolean validate(){
        boolean valido = true;
        //Aqui les toca a ustedes poner todas las reglas de validacion 
       
        return valido;
    }
    
    public String add(){
        boolean valido = validate();
        if(valido){
            Vendedor ven = new Vendedor();
            ven.setIdVendedor(dto.getIdVen());
            ven = venDAO.read(ven);
            dto.setIdVendedor(ven);
            
            Carros car = new Carros();
            car.setIdCarro(dto.getIdCar());
            car = carDAO.read(car);
            dto.setIdCarro(car);
            
            Agencias age = new Agencias();
            age.setIdAgencia(dto.getIdAgen());
            age = ageDAO.read(age);
            dto.setIdAgencia(age);
            
            dao.create(dto);
            if(valido){
                return prepareIndex();
            }else{
                return prepareAdd();
            }
        }
        return prepareAdd();
    }
    
    public String update(){
        boolean valido = validate();
        if(valido){
            Vendedor ven = new Vendedor();
            ven.setIdVendedor(dto.getIdVen());
            ven = venDAO.read(ven);
            dto.setIdVendedor(ven);
            
            Carros car = new Carros();
            car.setIdCarro(dto.getIdCar());
            car = carDAO.read(car);
            dto.setIdCarro(car);
            
            Agencias age = new Agencias();
            age.setIdAgencia(dto.getIdAgen());
            age = ageDAO.read(age);
            dto.setIdAgencia(age);
            
            dao.update(dto);
            if(valido){
                return prepareIndex();
            }else{
                return prepareUpdate();
            }
        }
        return prepareUpdate();
    }
    
    public String delete(){
        dao.delete(dto);
        return prepareIndex();
    }
    
    
    public void seleccionarVentas(ActionEvent event){
        String claveSeleccionada = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap().get("claveSel");
        dto = new Ventas();
        dto.setIdVentas(Long.parseLong(claveSeleccionada));
        try{
            dto = dao.read(dto);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void reporteVentas() {
        String user = "udtgyvrffzmoif";
        String password = "99fad350c1ebe63778407aaa771b9ec8be2251eb7079ef9ed791dcf5f80425af";
        String url = "jdbc:postgresql://ec2-52-54-167-8.compute-1.amazonaws.com/deja1jvn8k993";
        String driverPostgreSql = "org.postgresql.Driver";

        String jasperPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/reporteVentas.jasper");

        try {
            Class.forName(driverPostgreSql);
            Connection conexion = DriverManager.getConnection(url, user, password);

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            ServletOutputStream sos = response.getOutputStream();
            File reporte = new File(jasperPath);
//            System.out.println(reporte.getPath());
            byte[] b = JasperRunManager.runReportToPdf(reporte.getPath(), null, conexion);

            response.setContentType("application/pdf");
            response.setContentLength(b.length);

            sos.write(b, 0, b.length);
            sos.flush();
            sos.close();
            FacesContext.getCurrentInstance().responseComplete();
            conexion.close();
        } catch (IOException | ClassNotFoundException | JRException | SQLException ex) {
            Logger.getLogger(AgenciasMB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void reporteVentasOne() {
        String user = "udtgyvrffzmoif";
        String password = "99fad350c1ebe63778407aaa771b9ec8be2251eb7079ef9ed791dcf5f80425af";
        String url = "jdbc:postgresql://ec2-52-54-167-8.compute-1.amazonaws.com/deja1jvn8k993";
        String driverPostgreSql = "org.postgresql.Driver";

        String jasperPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/reporteVenta.jasper");

        try {
            Class.forName(driverPostgreSql);
            Connection conexion = DriverManager.getConnection(url, user, password);

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            ServletOutputStream sos = response.getOutputStream();
            File reporte = new File(jasperPath);
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("id", dto.getIdVentas().intValue());
            byte[] b = JasperRunManager.runReportToPdf(reporte.getPath(), parameters, conexion);

            response.setContentType("application/pdf");
            response.setContentLength(b.length);

            sos.write(b, 0, b.length);
            sos.flush();
            sos.close();
            FacesContext.getCurrentInstance().responseComplete();
            conexion.close();

        } catch (IOException | ClassNotFoundException | JRException | SQLException ex) {
            Logger.getLogger(AgenciasMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
