package pe.sanmiguel.bienestar.proyecto_gtics.Controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.SessionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import pe.sanmiguel.bienestar.proyecto_gtics.Entity.Medicamento;
import pe.sanmiguel.bienestar.proyecto_gtics.Repository.MedicamentoRepository;

import java.io.IOException;
import java.util.Base64;
import java.util.List;


@Controller
@RequestMapping(value="/paciente", method= RequestMethod.GET)
public class pacienteController {



    /*----------------- Repositories -----------------*/
    final MedicamentoRepository medicamentoRepository;

    public pacienteController(MedicamentoRepository medicamentoRepository){
        this.medicamentoRepository = medicamentoRepository;
    }




    /*----------------- Method: GET -----------------*/

    @GetMapping(value="")
    public String preOrdenes(){return "/paciente/pre_ordenes";}

    @GetMapping(value="/tracking")
    public String tracking(){ return "/paciente/tracking";}

    @GetMapping(value="/ordenes")
    public String ordenes(){return "/paciente/ordenes";}

    @GetMapping(value="/new_orden")
    public String new_orden(Model model){

        List<Medicamento> listaMedicamentos = medicamentoRepository.findAll();
        model.addAttribute("listaMedicamentos", listaMedicamentos);

        return "/paciente/new_orden";}

    @GetMapping(value="/mensajeria")
    public String mensajeria(){return "/paciente/mensajeria";}

    @GetMapping(value = "/chatbot")
    public String chatbot(){return "/paciente/chatbot";}

    @GetMapping(value = "/orden_paciente")
    public String ordenPaciente(){
        return "/paciente/orden_paciente";
    }

    @GetMapping(value = "/orden_paciente_stock")
    public String ordenPacienteStock(){
        return "/paciente/orden_paciente_stock";
    }

    @GetMapping(value = "/reemplazar_medicamentos")
    public String ordenReemplazoMedicamentos(){
        return "/paciente/reemplazar_medicamentos";
    }


    /*----------------- Method: POST -----------------*/

    @PostMapping(value = "/guardarOrden")
    public String guardarOrden(@RequestParam(value = "name", required = false) String name ,
                               @RequestParam(value = "lastname", required = false) String lastname,
                               @RequestParam(value = "dni", required = false) String dni,
                               @RequestParam(value = "edad", required = false) String edad,
                               @RequestParam(value = "doctor", required = false) String doctor,
                               @RequestParam(value = "fecha", required = false) String fecha,
                               @RequestParam(value = "seguro", required = false) String seguro,
                               @RequestParam(value = "correo", required = false) String correo,
                               @RequestParam(value = "genero", required = false) String genero,
                               @RequestParam(value = "imagen", required = false) MultipartFile archivo,
                               @RequestParam(value = "listaIds", required = false) List<String> lista,
                               Model model, RedirectAttributes redirectAttributes){


        System.out.println("Nombre: " + name);
        System.out.println("Lista: " + lista);

        try{
            byte[] bytes = archivo.getBytes();
            String base64Encoded = Base64.getEncoder().encodeToString(bytes);
            model.addAttribute("imagen", base64Encoded);
        } catch (IOException e){
            e.printStackTrace();
            return "error";
        }


        redirectAttributes.addFlashAttribute("msg", "Orden Creada");
        return "redirect:/paciente/ordenes";
        //return "/paciente/prueba";
    }
}
