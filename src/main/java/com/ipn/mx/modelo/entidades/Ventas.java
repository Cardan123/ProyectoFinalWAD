/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipn.mx.modelo.entidades;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author cardan_mac
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Ventas")
public class Ventas implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVentas;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idVendedor")
    private Vendedor idVendedor;
 
    @Transient
    private Long idVen;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idAgencia")
    private Agencias idAgencia;
 
    @Transient
    private Long idAgen;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idCarro")
    private Carros idCarro;
 
    @Transient
    private Long idCar;
}
