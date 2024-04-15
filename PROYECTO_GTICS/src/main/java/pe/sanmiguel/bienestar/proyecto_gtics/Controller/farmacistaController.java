package pe.sanmiguel.bienestar.proyecto_gtics.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pe.sanmiguel.bienestar.proyecto_gtics.Entity.Medicamento;
import pe.sanmiguel.bienestar.proyecto_gtics.Entity.Usuario;
import pe.sanmiguel.bienestar.proyecto_gtics.Repository.MedicamentoRepository;
import pe.sanmiguel.bienestar.proyecto_gtics.Repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;


@Controller
public class farmacistaController {


    final UsuarioRepository usuarioRepository;
    final MedicamentoRepository medicamentoRepository;
    public farmacistaController(UsuarioRepository usuarioRepository, MedicamentoRepository medicamentoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.medicamentoRepository = medicamentoRepository;
    }

    @GetMapping("/farmacista")
    public String farmacistaInicio(Model model) {

        List<Medicamento> listaMedicamentos = medicamentoRepository.findAll();
        model.addAttribute("listaMedicamentos", listaMedicamentos);

        return "/farmacista/inicio";
    }

    @PostMapping("/farmacista/continuar_compra")
    public String continuarCompra(String listaSelectedIds){
        System.out.println("A:" + listaSelectedIds);
        return "redirect:/farmacista/formulario_paciente";
    }

    @GetMapping("/farmacista/ordenes_venta")
    public String OrdenesVenta() {
        return "/farmacista/ordenes_venta";
    }

    @GetMapping("/farmacista/ordenes_web")
    public String OrdenesWeb() {
        return "/farmacista/ordenes_web";
    }

    @GetMapping("/farmacista/pre_ordenes")
    public String preOrdenes() {
        return "/farmacista/pre_ordenes";
    }
    @GetMapping("/farmacista/formulario_paciente")
    public String forPaciente() {
        return "/farmacista/formularioPaciente";
    }
    @PostMapping("/farmacista/detallesOrdenWeb")
    public String detaOrdenWeb() {
        return "/farmacista/detallesOrdenWeb";
    }

    @GetMapping("/farmacista/perfil")
    public String profile() {
        return "/farmacista/perfil";
    }
    @GetMapping("/farmacista/facturacion")/*LO PODEMOS USAR EN PACIENTE */
    public String facturacion() {
        return "/farmacista/facturacion";
    }

    @GetMapping("/farmacista/cambioContraseña")
    public String cambioContra() {
        return "/farmacista/cambioContraseña";
    }

    @GetMapping("/farmacista/notificacion")
    public String notificaciones() {
        return "/farmacista/notificacion";
    }
}
