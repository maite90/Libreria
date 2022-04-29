
package edu.egg.libreria.servicios;

import edu.egg.libreria.entidades.Editorial;
import edu.egg.libreria.exepciones.ExcepcionPropia;
import edu.egg.libreria.repositorios.EditorialRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialServicio {
   
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    
    @Transactional
    public Editorial guardar(String nombre)throws ExcepcionPropia{    
   
        validar(nombre);
        
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorial.setAlta(Boolean.TRUE);
        
        return editorialRepositorio.save(editorial); 
    }   
    
    @Transactional
    public Editorial modificar(String id, String nombre)throws ExcepcionPropia{    
           
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {      
        Editorial editorial = respuesta.get();
        editorial.setNombre(nombre);
        
        return editorialRepositorio.save(editorial); 
        
        } else {
            throw new ExcepcionPropia("EDITORIAL INEXISTENTE");
        }
    }  
    
    @Transactional
    public Editorial alta(String id)throws ExcepcionPropia{    
   
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {

        Editorial editorial = respuesta.get();
        editorial.setAlta(Boolean.TRUE);
        
        return editorialRepositorio.save(editorial);   
        
        } else {
            throw new ExcepcionPropia("EDITORIAL INEXISTENTE");
        }
    }  
    
    @Transactional
    public Editorial baja(String id)throws ExcepcionPropia{    
        
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
     
        Editorial editorial = respuesta.get();
        editorial.setAlta(Boolean.FALSE);
        
        return editorialRepositorio.save(editorial);
        
        } else {
            throw new ExcepcionPropia("EDITORIAL INEXISTENTE");
        }        
    }
    
    public Editorial buscarPorNombre(String nombre) {
        return editorialRepositorio.buscarPorNombre(nombre);
    }

    public Optional<Editorial> buscarPorId(String id) {
        return editorialRepositorio.findById(id);
    }
    
    @Transactional(readOnly = true)
    public List<Editorial> mostrarTodos() {
        return editorialRepositorio.findAll();
    }  
        
    public void validar(String nombre) throws ExcepcionPropia {
        if (nombre == null || nombre.isEmpty()) {
            throw new ExcepcionPropia("EL NOMBRE NO PUEDE SER NULO");
        }
    }
    
}
