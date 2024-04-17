package pe.sanmiguel.bienestar.proyecto_gtics.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pe.sanmiguel.bienestar.proyecto_gtics.CurrentTimeSQL;
import pe.sanmiguel.bienestar.proyecto_gtics.Entity.Medicamento;
import pe.sanmiguel.bienestar.proyecto_gtics.Entity.Orden;
import pe.sanmiguel.bienestar.proyecto_gtics.Entity.Usuario;
import pe.sanmiguel.bienestar.proyecto_gtics.Repository.MedicamentoRepository;
import pe.sanmiguel.bienestar.proyecto_gtics.Repository.OrdenContenidoRepository;
import pe.sanmiguel.bienestar.proyecto_gtics.Repository.OrdenRepository;
import pe.sanmiguel.bienestar.proyecto_gtics.Repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class farmacistaController {

    final UsuarioRepository usuarioRepository;
    final MedicamentoRepository medicamentoRepository;
    final OrdenRepository ordenRepository;
    final OrdenContenidoRepository ordenContenidoRepository;

    public farmacistaController(UsuarioRepository usuarioRepository, MedicamentoRepository medicamentoRepository, OrdenRepository ordenRepository, OrdenContenidoRepository ordenContenidoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.medicamentoRepository = medicamentoRepository;
        this.ordenRepository = ordenRepository;
        this.ordenContenidoRepository = ordenContenidoRepository;
    }

    ArrayList<Medicamento> medicamentosSeleccionados = new ArrayList<>();
    ArrayList<String> listaCantidades = new ArrayList<>();

    @GetMapping("/farmacista")
    public String farmacistaInicio(Model model) {
        List<Medicamento> listaMedicamentos = medicamentoRepository.findAll();
        model.addAttribute("listaMedicamentos", listaMedicamentos);
        return "/farmacista/inicio";
    }

    @PostMapping("/farmacista/continuar_compra")
    public String continuarCompra(@RequestParam("listaIds") List<String> listaSelectedIds){

        ArrayList<Optional<Medicamento>> OptSeleccionados = new ArrayList<>();
        listaCantidades = new ArrayList<>();

        for (int i = 0; i < listaSelectedIds.size(); i += 2) {
            OptSeleccionados.add(medicamentoRepository.findById(Integer.valueOf(listaSelectedIds.get(i))));
        }

        for (int i = 0; i + 1 < listaSelectedIds.size(); i += 2) {
            listaCantidades.add(listaSelectedIds.get(i + 1));
        }

        medicamentosSeleccionados = (ArrayList<Medicamento>) OptSeleccionados.stream().flatMap(Optional::stream).collect(Collectors.toList());

        System.out.println(listaCantidades);

        return "redirect:/farmacista/formulario_paciente";
    }

    @GetMapping("/farmacista/formulario_paciente")
    public String forPaciente(Model model) {
        model.addAttribute("medicamentosSeleccionados", medicamentosSeleccionados);
        model.addAttribute("listaCantidades", listaCantidades);
        return "/farmacista/formularioPaciente";
    }

    @PostMapping("/farmacista/finalizar_compra")
    public String finalizarCompra(@RequestParam(value = "name", required = false) String name ,
                                  @RequestParam(value = "lastname", required = false) String lastname,
                                  @RequestParam(value = "dni", required = false) String dni,
                                  @RequestParam(value = "edad", required = false) String edad,
                                  @RequestParam(value = "doctor", required = false) String doctor,
                                  @RequestParam(value = "fecha", required = false) String fecha,
                                  @RequestParam(value = "seguro", required = false) String seguro,
                                  @RequestParam(value = "correo", required = false) String correo,
                                  @RequestParam(value = "genero", required = false) String genero,
                                  @RequestParam(value = "listaIds", required = false) List<String> listaMedicamentos,
                                  Model model) {

        Usuario pacienteOrden = new Usuario();

        if (usuarioRepository.findByDniAndCorreo(dni, correo) == null){
            pacienteOrden.setIdUsuario(usuarioRepository.findLastUsuarioId()+1);
            pacienteOrden.setIdRol(1);
            pacienteOrden.setCorreo(correo);
            pacienteOrden.setContrasena("");
            pacienteOrden.setNombres(name);
            pacienteOrden.setApellidos(lastname);
            pacienteOrden.setDni(dni);
            pacienteOrden.setDireccion("San Miguel");
            pacienteOrden.setDistrito("San Miguel");
            pacienteOrden.setSeguro(seguro);
            pacienteOrden.setAceptado(false);
            pacienteOrden.setBaneado(false);
            usuarioRepository.save(pacienteOrden);
        } else {
            pacienteOrden = usuarioRepository.findByDniAndCorreo(dni, correo);
        }

        Orden nuevaOrden = new Orden();
        nuevaOrden.setIdOrden(ordenRepository.findLastOrdenId()+1);
        nuevaOrden.setFechaIni(CurrentTimeSQL.getCurrentDate());

        for (String id : listaMedicamentos){
            
        }


        ordenRepository.save(nuevaOrden);

        return "redirect:/farmacista/ver_orden_venta";
    }

    @GetMapping("/farmacista/ver_orden_venta")
    public String verOrdenesVenta() {
        return "/farmacista/ver_orden_venta";
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
    @GetMapping("/farmacista/detallesOrdenWeb")
    public String detaOrdenWeb() {return "/farmacista/detallesOrdenWeb";}
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
