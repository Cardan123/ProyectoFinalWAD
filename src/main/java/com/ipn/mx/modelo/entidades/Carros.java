/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipn.mx.modelo.entidades;

import java.io.Serializable;
import javax.persistence.Column;
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
@Table(name = "Carros")
public class Carros implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCarro;
    
    @Column(name = "nombreCarro", length = 100, nullable = false)
    private String nombreCarro;
    private int modelo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idMarca")
    private Marcas idMarca;
 
    @Transient
    private Long idMar;
    
}
