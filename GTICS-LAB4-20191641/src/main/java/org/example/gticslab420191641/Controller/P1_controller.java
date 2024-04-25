package org.example.gticslab420191641.Controller;

import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
public class P1_controller {
    @GetMapping(value = "")
    public String pregunta1(){return "/P1/Recursos_Humanos-GTICS";}

    @GetMapping(value = "/Empleados")
    public String empleados(){return "/P1/Empleados";}

    @GetMapping(value = "/Historial")
    public String historial(){return "/P1/Historial";}

    @GetMapping(value = "/Reportes")
    public String reportes(){return "/P1/Reportes";}
}
