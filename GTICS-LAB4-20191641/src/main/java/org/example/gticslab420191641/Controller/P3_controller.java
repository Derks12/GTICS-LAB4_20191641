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
public class P3_controller {
    @GetMapping(value = "/P3/Reportes1")
    public String reportes(){return "/P3/Reportes1";}

    @GetMapping(value = "/P3/Reporte_sueldos")
    public String reportesSueldos(){return "/P3/Reporte_sueldos";}

}
