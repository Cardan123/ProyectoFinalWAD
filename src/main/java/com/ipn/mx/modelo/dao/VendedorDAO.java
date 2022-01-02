/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipn.mx.modelo.dao;

import com.ipn.mx.modelo.entidades.Vendedor;
import com.ipn.mx.utilerias.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author cardan_mac
 */
public class VendedorDAO {
    public void create(Vendedor dto) {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction t = s.getTransaction();
        try {
            t.begin();
            s.save(dto);
            t.commit();
        } catch (HibernateException he) {
            if (t != null && t.isActive()) {
                t.rollback();
            }
        }
    }
    public void update(Vendedor dto) {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction t = s.getTransaction();
        try {
            t.begin();
            s.update(dto);
            t.commit();
        } catch (HibernateException he) {
            if (t != null && t.isActive()) {
                t.rollback();
            }
        }
    }
    public void delete(Vendedor dto) {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction t = s.getTransaction();
        try {
            t.begin();
            s.delete(dto);
            t.commit();
        } catch (HibernateException he) {
            if (t != null && t.isActive()) {
                t.rollback();
            }
        }
    }

    
    public Vendedor read(Vendedor dto){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        try{
            transaction.begin();
            dto = session.get(dto.getClass(), dto.getIdVendedor());
            transaction.commit();
        }catch(HibernateException he){
            if(transaction != null && transaction.isActive()){
                transaction.rollback();
            }
        }
        return dto;
    }
    
    public List readAll() {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction t = s.getTransaction();
        List lista = new ArrayList();
        try{
            t.begin();
            Query q = s.createQuery("from Vendedor v order by v.idVendedor");
            lista = q.list();
            t.commit();
        }catch (HibernateException he) {
            if (t != null && t.isActive()) {
                t.rollback();
            }
        }
        
        return lista;
    }
}
