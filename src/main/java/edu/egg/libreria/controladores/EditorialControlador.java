
package edu.egg.libreria.controladores;

import edu.egg.libreria.entidades.Editorial;
import edu.egg.libreria.exepciones.ExcepcionPropia;
import edu.egg.libreria.servicios.EditorialServicio;
import java.util.List;
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
    
    @GetMapping("/modificar")
    public String modificar(ModelMap modelo){
    
    List<Editorial> editorial = editorialServicio.mostrarTodos();
    modelo.addAttribute("editorial", editorial);
        
    return "editorial";
    }
    
    @PostMapping("/modificar")
    public String modificar(ModelMap modelo,String id, @RequestParam(defaultValue = "", required = true) String nombre ){
       
        try{
            editorialServicio.modificar(id, nombre);
            modelo.put("exito", "Editorial editada con éxito");
        } catch (Exception e) {            
            e.printStackTrace();
            modelo.put("error", "Falló la edición de la editorial :( ");
            modelo.put("nombre", nombre);
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
