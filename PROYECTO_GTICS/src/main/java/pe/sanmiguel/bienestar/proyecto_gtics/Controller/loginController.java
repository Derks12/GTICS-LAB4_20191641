package pe.sanmiguel.bienestar.proyecto_gtics.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class loginController {

    @GetMapping("/")
    public String login() {
        return "index";
    }

}
