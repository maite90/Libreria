
package edu.egg.libreria.controladores;

import edu.egg.libreria.entidades.Autor;
import edu.egg.libreria.exepciones.ExcepcionPropia;
import edu.egg.libreria.repositorios.AutorRepositorio;
import edu.egg.libreria.servicios.AutorServicio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/autor")
public class AutorControlador {
   
    @Autowired
    private AutorServicio autorServicio;
    
    @Autowired
    private AutorRepositorio autorRepositorio;

    @GetMapping("/registro")
    public String formulario(ModelMap modelo) {
        modelo.addAttribute("nombre");
        return "autor";
    }
    
    @PostMapping("/registro")
    public String registro(ModelMap modelo, @RequestParam String nombre){
        
        try {
            autorServicio.guardar(nombre);
            modelo.put("exito", "Registro exitoso: ¡USUARIO GUARDADO CON EXITO :) !");
            return "autor";
        } catch (Exception e) {
        e.printStackTrace();
        modelo.put("error", "Falló la carga de un nuevo autor :( ");
        return "autor";
        }
    }     
    
    @GetMapping("/listadoAutores")
    public String listadoAutores(ModelMap modelo) {
     List<Autor> autores = autorServicio.mostrarTodos();
        modelo.addAttribute("autores", autores);
        return "listadoAutores";
    }
    
    
    @GetMapping("/modificarGet")
    public String modificar(ModelMap modelo, String id){
    
    Optional<Autor> respuesta = autorRepositorio.findById(id);
    if(respuesta.isPresent()){
    Autor autor = respuesta.get();
    modelo.addAttribute("autor", autor);
    modelo.put("id", autor.getId());   
    }        
    return "autorModificar";
    }
    
    @PostMapping("/modificarPost")
    public String modificar(ModelMap modelo,String id, @RequestParam(defaultValue = "", required = true) String nombreNuevo ){
       
        try{
            autorServicio.modificar(id, nombreNuevo);
            modelo.put("exito", "Autor editado con éxito");
        } catch (Exception e) {            
            e.printStackTrace();
            modelo.put("error", "Falló la edición del autor :( ");
            modelo.put("nombre", nombreNuevo);
        }
        return "autor";
    }
        
    @GetMapping("/baja")
    public String baja(String id, ModelMap modelo){
        try {
            autorServicio.baja(id);
            modelo.put("exito", "Alta de la autor editada con éxito :)");
        } catch (Exception e) {
            modelo.put("error", "Falló la edición del alta de la autor :( ");
            return "listadoAutor";
        }
        return "index";
    }
    
    @GetMapping("/alta")
    public String alta(String id, ModelMap modelo){
        try {
            autorServicio.alta(id);
            modelo.put("exito", "Alta de la autor editado con éxito :)");
        } catch (ExcepcionPropia e) {
            modelo.put("error", "Falló la edición del alta de la autor :( ");
            return "listadoAutor";
        }
        return "index";
    }
    
}
 