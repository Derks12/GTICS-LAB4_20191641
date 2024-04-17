package pe.sanmiguel.bienestar.proyecto_gtics.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pe.sanmiguel.bienestar.proyecto_gtics.Entity.Medicamento;
import pe.sanmiguel.bienestar.proyecto_gtics.Repository.MedicamentoRepository;
import pe.sanmiguel.bienestar.proyecto_gtics.Repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class farmacistaController {


    final UsuarioRepository usuarioRepository;
    final MedicamentoRepository medicamentoRepository;
    public farmacistaController(UsuarioRepository usuarioRepository, MedicamentoRepository medicamentoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.medicamentoRepository = medicamentoRepository;
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

    @PostMapping("/farmacista/finalizar_compra")
    public String finalizarCompra(@RequestParam("listaIds") List<String> listaSelectedIds){

        ArrayList<Optional<Medicamento>> medicamentosSeleccionados = new ArrayList<>();

        for (int i = 0; i < listaSelectedIds.size(); i += 2) {
            medicamentosSeleccionados.add(medicamentoRepository.findById(Integer.valueOf(listaSelectedIds.get(i))));
        }

        return "redirect:/farmacista/detallesOrdenWeb";
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
    public String forPaciente(Model model) {
        model.addAttribute("medicamentosSeleccionados", medicamentosSeleccionados);
        model.addAttribute("listaCantidades", listaCantidades);
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
