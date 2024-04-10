package pe.sanmiguel.bienestar.proyecto_gtics.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = {"/superadmin"}, method = RequestMethod.GET)
public class superAdminController {
    @GetMapping(value = {""})
    public String showIndexSuperAdmin(){
        return "/superAdmin/paginaInicio";
    }

    @GetMapping(value = {"/administradoresSede"})
    public String showAdministradoresSede(){
        return "/superAdmin/listaAdministSede";
    }
    @GetMapping(value = {"/farmacistas"})
    public String showFarmacistas(){
        return "/superAdmin/listaFarmacistas";
    }
    @GetMapping(value = {"/pacientes"})
    public String showPacientes(){
        return "/superAdmin/listaPacientes";
    }
    @GetMapping(value = {"/doctores"})
    public String showDoctores(){
        return "/superAdmin/listaDoctores";
    }

    @GetMapping(value = {"/pedidos"})
    public String showPedidos(){
        return "/superAdmin/pedidos";
    }
    @GetMapping(value = {"/solicitudes"})
    public String showSolicitudes(){
        return "/superAdmin/solicitudes";
    }

    @GetMapping(value = {"/medicamentos"})
    public String showMedicamentos(){
        return "/superAdmin/medicamentos";
    }

    @GetMapping(value = {"/cambiarContraseña"})
    public String cambiarContraseña(){
        return "/superAdmin/cambiarcontraseña";
    }

}
