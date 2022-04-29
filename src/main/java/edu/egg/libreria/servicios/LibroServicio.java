package edu.egg.libreria.servicios;

import edu.egg.libreria.entidades.Autor;
import edu.egg.libreria.entidades.Editorial;
import edu.egg.libreria.entidades.Libro;
import edu.egg.libreria.exepciones.ExcepcionPropia;
import edu.egg.libreria.repositorios.LibroRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;
    
    @Transactional
    public Libro guardar(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Autor autor, Editorial editorial)throws ExcepcionPropia{    
   
        validar(isbn, titulo, anio, ejemplares, ejemplaresPrestados, autor, editorial);
        
        Libro libro = new Libro();
        
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(ejemplaresPrestados);
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        libro.setAlta(Boolean.TRUE);
        
        return libroRepositorio.save(libro);        
    }   
    
    @Transactional
    public Libro modificar(String id, Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Autor autor, Editorial editorial)throws ExcepcionPropia{    
   
        validar(isbn, titulo, anio, ejemplares, ejemplaresPrestados, autor, editorial);
        
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
        Libro libro = new Libro();
        
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(ejemplaresPrestados);
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        
        return libroRepositorio.save(libro); 
        
        } else {
            throw new ExcepcionPropia("LIBRO INEXISTENTE");
        }
        
        
    }  
    
    @Transactional
    public Libro alta(String id)throws ExcepcionPropia{    
   
        
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
        Libro libro = respuesta.get();
        
        libro.setAlta(Boolean.TRUE);
        
        return libroRepositorio.save(libro); 
        
        } else {
            throw new ExcepcionPropia("LIBRO INEXISTENTE");
        }
    }  
    
    @Transactional
    public Libro baja(String id)throws ExcepcionPropia{    
   
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            
        Libro libro = respuesta.get();
        libro.setAlta(Boolean.FALSE);
        
        return libroRepositorio.save(libro);  
        
        } else {
        throw new ExcepcionPropia("LIBRO INEXISTENTE");
        }
    }
    
        public Libro buscarPorNombre(String titulo) {
        return libroRepositorio.buscarPorTitulo(titulo);
    }

    public Optional<Libro> buscarPorId(String id) {
        return libroRepositorio.findById(id);
    }
    
    @Transactional(readOnly = true)
    public List<Libro> mostrarTodos() {
        return libroRepositorio.findAll();
    }  
    
    public void validar(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Autor autor, Editorial editorial) throws ExcepcionPropia {
        if (isbn == null) {
            throw new ExcepcionPropia("EL ISBN NO PUEDE SER NULO");
        }
        if (titulo == null || titulo.isEmpty()) {
            throw new ExcepcionPropia("EL TITULO DEL LIBRO NO PUEDE SER NULO");
        }
        if (anio == null) {
            throw new ExcepcionPropia("EL AÑO NO PUEDE SER NULO");
        }
        if (ejemplares == null) {
            throw new ExcepcionPropia("LOS EJEMPLARES NO PUEDEN SER NULOS");
        }
        if (ejemplaresPrestados == null) {
            throw new ExcepcionPropia("LOS EJEMPLARES PRESTADOS NO PUEDEN SER NULOS");
        }
        if (autor == null) {
            throw new ExcepcionPropia("EL AUTOR NO PUEDE SER NULO");
        }
        if (editorial == null) {
            throw new ExcepcionPropia("EL EDITORIAL NO PUEDE SER NULO");
        }
    }
    
}
