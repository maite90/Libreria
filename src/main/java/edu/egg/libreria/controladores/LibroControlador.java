
package edu.egg.libreria.controladores;

import edu.egg.libreria.entidades.Autor;
import edu.egg.libreria.entidades.Editorial;
import edu.egg.libreria.entidades.Libro;
import edu.egg.libreria.exepciones.ExcepcionPropia;
import edu.egg.libreria.servicios.AutorServicio;
import edu.egg.libreria.servicios.EditorialServicio;
import edu.egg.libreria.servicios.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/libro")
public class LibroControlador {
    
    @Autowired
    private LibroServicio libroServicio;
    
    @Autowired
    private AutorServicio autorServicio;
    
    @Autowired
    private EditorialServicio editorialServicio;
    
    @GetMapping("/registro")
    public String formulario(ModelMap modelo){
    List<Autor> autor = autorServicio.mostrarTodos();
    modelo.addAttribute("autores", autor);
    
    List<Editorial> editorial = editorialServicio.mostrarTodos();
    modelo.addAttribute("editoriales", editorial);
        
    return "libro";
    }
    
    @PostMapping("/registro")
    public String guardar(ModelMap modelo,@RequestParam(defaultValue = "", required = true) Long isbn,@RequestParam(defaultValue = "", required = true)  String titulo, @RequestParam(defaultValue = "", required = true) Integer anio, @RequestParam(defaultValue = "", required = true) Integer ejemplares, @RequestParam(defaultValue = "", required = true) Integer ejemplaresPrestados, @RequestParam(defaultValue = "", required = true) String autor, @RequestParam(defaultValue = "", required = true)  String editorial){
        Autor autorObjeto = autorServicio.buscarPorNombre(autor);
        Editorial editorialObjeto = editorialServicio.buscarPorNombre(editorial);
        try{
            libroServicio.guardar(isbn, titulo, anio, ejemplares, ejemplaresPrestados, autorObjeto, editorialObjeto);
            modelo.put("exito", "Libro guardado con éxito");
        } catch (Exception e) {
            
            e.printStackTrace();
            modelo.put("error", "Falló la carga de un nuevo libro :( ");

            modelo.put("isbn", isbn);
            modelo.put("titulo", titulo);
            modelo.put("anio", anio);
            modelo.put("ejemplares", ejemplares);
            modelo.put("ejemplaresPrestados", ejemplaresPrestados);
            modelo.put("autores", autorServicio.mostrarTodos());
            modelo.put("editoriales", editorialServicio.mostrarTodos());
            
        }
        return "libro";
    }
    
         
    @GetMapping("/listadoLibros")
    public String listadoLibros(ModelMap modelo) {
     List<Libro> libros = libroServicio.mostrarTodos();
        modelo.addAttribute("libros", libros);
        return "listadoLibros";
    }
    
    @GetMapping("/modificar")
    public String modificar(ModelMap modelo){
    List<Autor> autor = autorServicio.mostrarTodos();
    modelo.addAttribute("autor", autor);
    
    List<Editorial> editorial = editorialServicio.mostrarTodos();
    modelo.addAttribute("editorial", editorial);
        
    return "libro";
    }
    
    @PostMapping("/modificar")
    public String modificar(ModelMap modelo,String id, @RequestParam Long isbn, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam Integer ejemplaresPrestados, @RequestParam String autor, @RequestParam String editorial){
        Autor autorObjeto = autorServicio.buscarPorNombre(autor);
        Editorial editorialObjeto = editorialServicio.buscarPorNombre(editorial);
        try{
            libroServicio.modificar(id, isbn, titulo, anio, ejemplares, ejemplaresPrestados, autorObjeto, editorialObjeto);
            modelo.put("exito", "Libro editado con éxito");
        } catch (Exception e) {
            
            e.printStackTrace();
            modelo.put("error", "Falló la edición del libro :( ");
            modelo.put("isbn", isbn);
            modelo.put("titulo", titulo);
            modelo.put("anio", anio);
            modelo.put("ejemplares", ejemplares);
            modelo.put("ejemplaresPrestados", ejemplaresPrestados);
            modelo.put("autores", autorServicio.mostrarTodos());
            modelo.put("editoriales", editorialServicio.mostrarTodos());
            
        }
        return "libro";
    }
        
    @GetMapping("/baja")
    public String baja(String id, ModelMap modelo){
        try {
            libroServicio.baja(id);
            modelo.put("exito", "Alta del libro editado con éxito :)");
        } catch (ExcepcionPropia e) {
            modelo.put("error", "Falló la edición del alta del libro :( ");
            return "listadoLibros";
        }
        return "index";
    }
    
    @GetMapping("/alta")
    public String alta(String id, ModelMap modelo){
        System.out.println("el id es:" + id);
        try {
            libroServicio.alta(id);
            modelo.put("exito", "Alta del libro editado con éxito :)");
        } catch (ExcepcionPropia e) {
            modelo.put("error", "Falló la edición del alta del libro :( ");
            return "listadoLibros";
        }
        return "index";
    }
}
    
   