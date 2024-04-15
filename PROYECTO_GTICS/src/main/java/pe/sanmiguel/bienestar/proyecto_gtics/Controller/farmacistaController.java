package pe.sanmiguel.bienestar.proyecto_gtics.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pe.sanmiguel.bienestar.proyecto_gtics.Entity.Usuario;
import pe.sanmiguel.bienestar.proyecto_gtics.Repository.UsuarioRepository;

import java.util.List;


@Controller
public class farmacistaController {


    final UsuarioRepository usuarioRepository;
    public farmacistaController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/farmacista")
    public String farmacistaInicio() {

        List<Usuario> listaUsuarios = usuarioRepository.findAll();

        for (Usuario u : listaUsuarios){
            System.out.println(u.getIdusuario());
        }

        return "/farmacista/inicio";
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
