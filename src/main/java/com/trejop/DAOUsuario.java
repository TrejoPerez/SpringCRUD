/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trejop;

import java.io.Serializable;
import java.util.ArrayList;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author root
 */
@Transactional
public interface DAOUsuario extends CrudRepository<Usuario, Integer>{
    public Usuario findByEmailAndPassword(String email,String password);
    //public Usuario update(Usuario usuario);
    @Query("SELECT  u FROM Usuario u WHERE u.nombre  like %?1% ")
    public ArrayList<Usuario> findByNombreIsLike(String nombre);
   
    
}
