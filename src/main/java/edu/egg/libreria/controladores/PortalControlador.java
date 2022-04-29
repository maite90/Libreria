
package edu.egg.libreria.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller 
@RequestMapping("/")
public class PortalControlador {
    
//@GetMapping("/")
//public String index(){
//return "index";
//}

@GetMapping("")
public String index(ModelMap modelo){
modelo.put("nombreControlador" , "L I B R E R I A");
return "index";
}

@GetMapping("registroAutor")
public String registroAutor(){
    return "autor/registro";
}

@GetMapping("registroEditorial")
public String registroEditorial(){
    return "editorial/registro";    
}

@GetMapping("registroLibro")
public String registroLibro(){
    return "libro/registro";    
}

}