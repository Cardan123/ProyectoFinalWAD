/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipn.mx.modelo.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Table(name = "Agencias")
public class Agencias implements Serializable { 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgencia;
    
    @Column(name = "nombreAgencia", length = 100, nullable = false)
    private String nombreAgencia;
    
    @Column(name = "direccionAgencia", length = 200, nullable = false)
    private String direccionAgencia;
    
}
