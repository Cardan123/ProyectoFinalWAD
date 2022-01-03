/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.ipn.mx.controlador.web;

import static com.ipn.mx.controlador.web.BaseBean.ACC_ACTUALIZAR;
import static com.ipn.mx.controlador.web.BaseBean.ACC_CREAR;
import com.ipn.mx.modelo.dao.GraficaDAO;
import com.ipn.mx.modelo.dao.MarcasDAO;
import com.ipn.mx.modelo.dto.GraficaDTO;
import com.ipn.mx.modelo.entidades.Marcas;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 *
 * @author cardan_mac
 */
@ManagedBean(name = "marcasMB")
@SessionScoped
public class MarcasMB extends BaseBean implements Serializable {

    private final MarcasDAO dao = new MarcasDAO();

    private Marcas dto;
    private List<Marcas> listaMarcas;
    
    /**
     * Creates a new instance of MarcasMB
     */
    public MarcasMB() {
    }
    
    public Marcas getDto() {
        return dto;
    }

    public void setDto(Marcas dto) {
        this.dto = dto;
    }

    public List<Marcas> getListaMarcas() {
        return listaMarcas;
    }

    public void setListaMarcas(List<Marcas> listaMarcas) {
        this.listaMarcas = listaMarcas;
    }

    @PostConstruct
    public void init() {
        listaMarcas = new ArrayList<>();
        listaMarcas = dao.readAll();
    }

    public String prepareAdd() {
        dto = new Marcas();
        setAccion(ACC_CREAR);
        return "/marcas/marcasForm?faces-redirect=true";
    }

    public String prepareUpdate() {
        setAccion(ACC_ACTUALIZAR);
        return "/marcas/marcasForm?faces-redirect=true";
    }

    public String prepareIndex() {
        init();
        return "/marcas/listadoMarcas?faces-redirect=true";
    }

    public boolean validate() {
        boolean valido = true;
        //Aqui les toca a ustedes poner todas las reglas de validacion 

        return valido;
    }

    public String add() {
        boolean valido = validate();
        if (valido) {
            dao.create(dto);
            if (valido) {
                return prepareIndex();
            } else {
                return prepareAdd();
            }
        }
        return prepareAdd();
    }

    public String update() {
        boolean valido = validate();
        if (valido) {
            dao.update(dto);
            if (valido) {
                return prepareIndex();
            } else {
                return prepareUpdate();
            }
        }
        return prepareUpdate();
    }

    public String delete() {
        dao.delete(dto);
        return prepareIndex();
    }

    public void seleccionarMarcas(ActionEvent event) {
        String claveSeleccionada = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap().get("claveSel");
        dto = new Marcas();
        dto.setIdMarca(Long.parseLong(claveSeleccionada));
        try {
            dto = dao.read(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 
    public void reporteMarcas() {
        String user = "udtgyvrffzmoif";
        String password = "99fad350c1ebe63778407aaa771b9ec8be2251eb7079ef9ed791dcf5f80425af";
        String url = "jdbc:postgresql://ec2-52-54-167-8.compute-1.amazonaws.com/deja1jvn8k993";
        String driverPostgreSql = "org.postgresql.Driver";

        String jasperPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/reporteMarcas.jasper");

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

    public void reporteMarcasOne() {
        String user = "udtgyvrffzmoif";
        String password = "99fad350c1ebe63778407aaa771b9ec8be2251eb7079ef9ed791dcf5f80425af";
        String url = "jdbc:postgresql://ec2-52-54-167-8.compute-1.amazonaws.com/deja1jvn8k993";
        String driverPostgreSql = "org.postgresql.Driver";

        String jasperPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/reporteMarca.jasper");

        try {
            Class.forName(driverPostgreSql);
            Connection conexion = DriverManager.getConnection(url, user, password);

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            ServletOutputStream sos = response.getOutputStream();
            File reporte = new File(jasperPath);
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("id", dto.getIdMarca().intValue());
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
    
    public void graficar() {
        JFreeChart grafica = ChartFactory.createPieChart("Carros por Marcas", obtenerDatosGraficaProductosPorCategoria(), true, true, Locale.getDefault());
        String archivo = FacesContext.getCurrentInstance().getExternalContext().getRealPath(".grafica.png");
        System.out.println(FacesContext.getCurrentInstance().getExternalContext().getRealPath("./grafica.png"));
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        try {
            ChartUtils.saveChartAsPNG(new File(archivo), grafica, 500, 500);
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + "/grafica.xhtml");

//            RequestDispatcher vista = request.getRequestDispatcher("../grafica.xhtml");
//            vista.forward(request, response);
//            RequestDispatcher vista = ServletActionContext.getRequest().getRequestDispatcher("grafica.jsp");
//            vista.forward(ServletActionContext.getRequest(), ServletActionContext.getResponse());
        } catch (IOException ex) {
            Logger.getLogger(MarcasMB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private PieDataset obtenerDatosGraficaProductosPorCategoria() {
        DefaultPieDataset dsPie = new DefaultPieDataset();

        GraficaDAO dao = new GraficaDAO();
        try {
            List datos = dao.obtenerDatosGrafica();
            for (int i = 0; i < datos.size(); i++) {
                GraficaDTO dto = (GraficaDTO) datos.get(i);
                dsPie.setValue(dto.getNombreCarrosString(), dto.getCantidad());
            }
        } catch (SQLException ex) {
            Logger.getLogger(MarcasMB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return dsPie;
    }
    
}
