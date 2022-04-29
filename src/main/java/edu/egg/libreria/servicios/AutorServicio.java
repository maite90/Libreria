
package edu.egg.libreria.servicios;

import edu.egg.libreria.entidades.Autor;
import edu.egg.libreria.exepciones.ExcepcionPropia;
import edu.egg.libreria.repositorios.AutorRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorServicio {
    
    @Autowired
    private AutorRepositorio autorRepositorio;
    
    @Transactional
    public Autor guardar(String nombre)throws ExcepcionPropia{    
   
        validar(nombre);
        
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setAlta(Boolean.TRUE);
        
        return autorRepositorio.save(autor);        
    }   
    
    @Transactional
    public Autor modificar(String id, String nombre)throws ExcepcionPropia{    
   
        validar(nombre);
        
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            
        Autor autor = respuesta.get();
        autor.setNombre(nombre);
        
        return autorRepositorio.save(autor);  
        
        } else {
            throw new ExcepcionPropia("EDITORIAL INEXISTENTE");
        }   
    }  
    
    @Transactional
    public Autor alta(String id)throws ExcepcionPropia{    
   
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
        
        Autor autor = respuesta.get();
        autor.setAlta(Boolean.TRUE);
        
        return autorRepositorio.save(autor);

        } else {
            throw new ExcepcionPropia("EDITORIAL INEXISTENTE");
        }           
    }  
    
    @Transactional
    public Autor baja(String id)throws ExcepcionPropia{    
   
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
        
        Autor autor = respuesta.get();
        autor.setAlta(Boolean.FALSE);
        
        return autorRepositorio.save(autor); 
        
        } else {
            throw new ExcepcionPropia("EDITORIAL INEXISTENTE");
        }   
    }
    
    public Autor buscarPorNombre(String nombre) {
        return autorRepositorio.buscarPorNombre(nombre);
    }

    public Optional<Autor> buscarPorId(String id) {
        return autorRepositorio.findById(id);
    }
    
    @Transactional(readOnly = true)
    public List<Autor> mostrarTodos() {
        return autorRepositorio.findAll();
    }  
    
    public void validar(String nombre) throws ExcepcionPropia {
        if (nombre == null || nombre.isEmpty()) {
            throw new ExcepcionPropia("EL NOMBRE NO PUEDE SER NULO");
        }
    }
} 

