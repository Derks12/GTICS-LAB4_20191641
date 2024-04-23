package pe.sanmiguel.bienestar.proyecto_gtics.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class login1 {
    @GetMapping("/hola")
    public String login() {
        return "login";
    }
    @GetMapping("/hola1")
    public String formReg() {
        return "registro";
    }
}
