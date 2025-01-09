/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.util.List;

/**
 *
 * @author angsaegim
 */
public interface GenericDAO<T> {

    void create(T entity);

    void update(T entity);

    void delete(T entity);

    T findById(Object id);

    List<T> findAll();
}
