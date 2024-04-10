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
        return "/adminsede/inicio";
    }

    @GetMapping(value = {"/doctores"})
    public String showDoctors(){
        return "/adminsede/doctores";
    }

    @GetMapping(value = {"/farmacista"})
    public String showFarmacistas(){
        return "/adminsede/farmacistas";
    }

    @GetMapping(value = {"/ordenes"})
    public String showOrders(){
        return "/adminsede/ordenes_reposicion";
    }

    @GetMapping(value = {"/editar_farmacista"})
    public String editFarmacista(){
        return "/adminsede/editar_farmacista";
    }

    @GetMapping(value = {"/editar_orden_reposicion"})
    public String editOrden(){
        return "/adminsede/editar_orden_reposicion";
    }

    @GetMapping(value = {"/medicamentos"})
    public String showMedicamentos(){
        return "/adminsede/medicamentos_sede";
    }

    @GetMapping(value = {"/solicitud_farmacista"})
    public String solicitudFarmacista(){
        return "/adminsede/solicitud_agregar_farmacista";
    }



}
