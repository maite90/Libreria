
package edu.egg.libreria.controladores;

import edu.egg.libreria.entidades.Editorial;
import edu.egg.libreria.exepciones.ExcepcionPropia;
import edu.egg.libreria.repositorios.EditorialRepositorio;
import edu.egg.libreria.servicios.EditorialServicio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {
       
    @Autowired
    private EditorialServicio editorialServicio;
    
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    

    @GetMapping("/registro")
    public String formulario(ModelMap modelo) {
        modelo.addAttribute("nombre");
        return "editorial";
    }
    
    @PostMapping("/registro")
    public String registro(ModelMap modelo, @RequestParam String nombre){
      
        try {
            editorialServicio.guardar(nombre);
            modelo.put("exito", "Registro exitoso: ¡EDITORIAL GUARDADA CON EXITO :) !");
            return "editorial";
        } catch (Exception e) {
        e.printStackTrace();
        modelo.put("error", "Falló la carga de una nueva editorial :( ");
        return "editorial";       
        }       
    }     
     
    @GetMapping("/listadoEditoriales")
    public String listadoEditoriales(ModelMap modelo) {
     List<Editorial> editoriales = editorialServicio.mostrarTodos();
        modelo.addAttribute("editoriales", editoriales);
        return "listadoEditoriales";
    }
    
    @GetMapping("/modificarGet")
    public String modificar(ModelMap modelo, String id){

     Optional<Editorial> respuesta = editorialRepositorio.findById(id);    
     if (respuesta.isPresent()) {
     Editorial editorial = respuesta.get();
     modelo.addAttribute("editorial", editorial);   
     modelo.put("id", editorial.getId());    
     }        
     return "editorialModificar";
    }
    
    
    @PostMapping("/modificarPost")
    public String modificar(ModelMap modelo,String id, @RequestParam(defaultValue = "", required = true) String nombreNuevo ){
       
        try{
            editorialServicio.modificar(id, nombreNuevo);
            modelo.put("exito", "Editorial editada con éxito");
            
        } catch (Exception e) {            
            e.printStackTrace();
            modelo.put("error", "Falló la edición de la editorial :( ");
            modelo.put("nombre", nombreNuevo);
        }
        return "editorial";
    }
        
    @GetMapping("/baja")
    public String baja(String id, ModelMap modelo){
        try {
            editorialServicio.baja(id);
            modelo.put("exito", "Alta de la editorial editada con éxito :)");
        } catch (ExcepcionPropia e) {
            modelo.put("error", "Falló la edición del alta de la editorial :( ");
            return "listadoEditorial";
        }
        return "index";
    }
    
    @GetMapping("/alta")
    public String alta(String id, ModelMap modelo){
        try {
            editorialServicio.alta(id);
            modelo.put("exito", "Alta de la editorial editado con éxito :)");
        } catch (ExcepcionPropia e) {
            modelo.put("error", "Falló la edición del alta de la editorial :( ");
            return "listadoEditorial";
        }
        return "index";
    }
}
