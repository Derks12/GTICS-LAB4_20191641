package pe.sanmiguel.bienestar.proyecto_gtics.Controller;


import jakarta.servlet.annotation.MultipartConfig;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping(value="/mensajeria")
    public String mensajeria(){return "/paciente/mensajeria";}

    @GetMapping(value = "/chatbot")
    public String chatbot(){return "/paciente/chatbot";}





    @PostMapping(value = "/guardarOrden")
    public String guardarOrden(@RequestParam(value = "name", required = false) String name ,
                               @RequestParam(value = "lastname", required = false) String lastname,
                               @RequestParam(value = "dni", required = false) String dni,
                               @RequestParam(value = "edad", required = false) String edad,
                               @RequestParam(value = "doctor", required = false) String doctor,
                               @RequestParam(value = "fecha", required = false) String fecha,
                               @RequestParam(value = "seguro", required = false) String seguro,
                               @RequestParam(value = "correo", required = false) String correo,
                               @RequestParam(value = "genero", required = false) String genero ){


        System.out.println("Nombre:" + name);
        System.out.println("Apellido: " + lastname);
        System.out.println("Doctor: " + doctor);
        System.out.println("Fecha: " + fecha);
        System.out.println("genero: " + genero);

        return "redirect:/paciente/ordenes";
    }


}
