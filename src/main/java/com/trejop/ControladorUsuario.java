/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trejop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author root    
 */
@RestController
@RequestMapping("/")
public class ControladorUsuario {
    
    @Autowired 
    private DAOUsuario daoUsuario;
    @CrossOrigin
    @RequestMapping(value="usuario-nombre/{nombre}", method=RequestMethod.GET, headers={"Accept=text/html"})
    //@ResponseStatus(HttpStatus.FOUND)
    public String findByName(@PathVariable String nombre) throws Exception{
        ArrayList<Usuario> u = new ArrayList();
        ObjectMapper mapper = new ObjectMapper();
            u = daoUsuario.findByNombreIsLike(nombre);
        return mapper.writeValueAsString(u);
    }
    @CrossOrigin
    @RequestMapping(value="usuario-nombre/", method=RequestMethod.GET, headers={"Accept=text/html"})
    public String findByNameU() throws Exception{
        ArrayList<Usuario> u = new ArrayList();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(u);
    }
    @CrossOrigin
    @RequestMapping(value="usuario-add/", method=RequestMethod.POST)
    public ResponseEntity<Usuario> addUser(@RequestBody Usuario usuario,UriComponentsBuilder ucBuilder)throws Exception{
        Usuario u = daoUsuario.save(usuario);
        //HttpHeaders headers = new HttpHeaders();
        //headers.setLocation(ucBuilder.path("usuario/{id}").buildAndExpand(usuario.getIdUsuario()).toUri());
        return new ResponseEntity(u,HttpStatus.CREATED);
        
    }
    
    @CrossOrigin
    @RequestMapping(value="usuario-update/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Usuario> updateUser(@PathVariable Integer id,@RequestBody Usuario usuario) throws Exception{        
        Usuario u = daoUsuario.findOne(id);
        u.setNombre(usuario.getNombre());
        u.setEmail(usuario.getEmail());
        u.setPassword(usuario.getPassword());
        daoUsuario.save(u);
        return new ResponseEntity<Usuario>(usuario,HttpStatus.OK);
    }
    
    @CrossOrigin
    @RequestMapping(value="usuario/{id}", method = RequestMethod.GET)
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Integer id) throws Exception{
        Usuario u = daoUsuario.findOne(id);
        if(u == null){
            System.out.println("No hay resultados para el usuario con id = " + id);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(u,HttpStatus.OK);
    }
    @CrossOrigin
    @RequestMapping(value="usuario-delete/{id}",method=RequestMethod.DELETE)
    public ResponseEntity<Usuario> borrarPorId(@PathVariable Integer id) throws Exception{
            Usuario u = daoUsuario.findOne(id);
            if(u==null){
                System.out.println(" no hay resultados para el usuario con id = " + id);
                return new ResponseEntity(u,HttpStatus.NOT_FOUND);
            }else{
                System.out.println(" hay resultados para el usuario con id = " + id);
                daoUsuario.delete(id);
            }
            return new ResponseEntity(u,HttpStatus.NO_CONTENT);
        
    }
    @CrossOrigin
    @RequestMapping(value="usuario/{email}/{password}", method = RequestMethod.GET,headers={"Accept=application/json"})
    public String buscarPorEmailAndPassword(@PathVariable String email,@PathVariable String password) throws Exception{
        Usuario u = daoUsuario.findByEmailAndPassword(email,password);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(u);
    }
    @CrossOrigin
    @RequestMapping(value="usuariosP", method = RequestMethod.GET, headers={"Accept=application/json"})
    public String buscarTodos() throws JsonProcessingException{
        ArrayList<Usuario> u = new ArrayList();
        u =  (ArrayList<Usuario>) daoUsuario.findAll();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(u);
    }
    
    
}
