package pe.sanmiguel.bienestar.proyecto_gtics.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = {"/adminsede"}, method = RequestMethod.GET)
public class adminSedeController {

    @GetMapping(value = {""})
    public String showIndexAdminSede(){
        return "/adminSede/inicio";
    }

    @GetMapping(value = {"/doctores"})
    public String showDoctors(){
        return "/adminSede/doctores";
    }

    @GetMapping(value = {"/ordenes"})
    public String showOrders(){
        return "/adminSede/ordenes_reposicion";
    }

    @GetMapping(value = {"/editar_farmacista"})
    public String editFarmacista(){
        return "/adminSede/editar_farmacista";
    }

    @GetMapping(value = {"/editar_orden_reposicion"})
    public String editOrden(){
        return "/adminSede/editar_orden_reposicion";
    }

    @GetMapping(value = {"/medicamentos"})
    public String showMedicamentos(){
        return "/adminSede/medicamentos_sede";
    }

    @GetMapping(value = {"/solicitud_farmacista"})
    public String solicitudFarmacista(){
        return "/adminSede/solicitud_agregar_farmacista";
    }



}
