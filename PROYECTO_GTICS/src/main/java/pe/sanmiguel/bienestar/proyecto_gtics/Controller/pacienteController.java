package pe.sanmiguel.bienestar.proyecto_gtics.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/paciente", method= RequestMethod.GET)
public class pacienteController {

    @GetMapping(value="")
    public String preOrdenes(){return "/paciente/pre_ordenes";}


    @GetMapping(value="/tracking")
    public String tracking(){ return "/paciente/tracking";}

    @GetMapping(value="/ordenes")
    public String ordenes(){return "/paciente/ordenes";}

    @GetMapping(value="/new_orden")
    public String new_orden(){return "/paciente/new_orden";}




}
