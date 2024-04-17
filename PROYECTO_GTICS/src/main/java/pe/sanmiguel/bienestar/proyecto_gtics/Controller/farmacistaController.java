package pe.sanmiguel.bienestar.proyecto_gtics.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pe.sanmiguel.bienestar.proyecto_gtics.CurrentTimeSQL;
import pe.sanmiguel.bienestar.proyecto_gtics.Entity.Medicamento;
import pe.sanmiguel.bienestar.proyecto_gtics.Entity.Orden;
import pe.sanmiguel.bienestar.proyecto_gtics.Entity.OrdenContenido;
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
    public String finalizarCompra(@RequestParam(value = "name") String name,
                                  @RequestParam(value = "lastname") String lastname,
                                  @RequestParam(value = "dni") String dni,
                                  @RequestParam(value = "edad") String edad,
                                  @RequestParam(value = "doctor") String doctor,
                                  @RequestParam(value = "fecha") String fecha,
                                  @RequestParam(value = "seguro") String seguro,
                                  @RequestParam(value = "correo") String correo,
                                  @RequestParam(value = "genero") String genero,
                                  @RequestParam(value = "listaIds") List<String> listaSelectedIds,
                                  @RequestParam(value = "priceTotal") String priceTotal,
                                  Model model) {

        System.out.println(priceTotal);

        ArrayList<Optional<Medicamento>> listaFinalMedicamentosOpt = new ArrayList<>();
        ArrayList<Medicamento> listaFinalMedicamentos = new ArrayList<>();
        ArrayList<String> listaFinalCantidades = new ArrayList<>();

        for (int i = 0; i < listaSelectedIds.size(); i += 2) {
            listaFinalMedicamentosOpt.add(medicamentoRepository.findById(Integer.valueOf(listaSelectedIds.get(i))));
        }

        for (int i = 0; i + 1 < listaSelectedIds.size(); i += 2) {
            listaFinalCantidades.add(listaSelectedIds.get(i + 1));
        }

        listaFinalMedicamentos = (ArrayList<Medicamento>) listaFinalMedicamentosOpt.stream().flatMap(Optional::stream).collect(Collectors.toList());

        System.out.println(listaFinalCantidades);


        Usuario pacienteOrden = new Usuario();

        if (usuarioRepository.findByDniAndCorreo(dni, correo) != null){
            pacienteOrden = usuarioRepository.findByDniAndCorreo(dni, correo);

        } else {

            if (usuarioRepository.findLastUsuarioId() == null){
                pacienteOrden.setIdUsuario(1);
            } else {
                pacienteOrden.setIdUsuario(usuarioRepository.findLastUsuarioId()+1);
            }
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
        }

        Integer idPaciente = pacienteOrden.getIdUsuario();

        Orden nuevaOrden = new Orden();

        Integer nuevaOrdenId = 1;
        if (ordenRepository.findLastOrdenId() != null){
            nuevaOrdenId = ordenRepository.findLastOrdenId()+1;
        }

        nuevaOrden.setIdOrden(nuevaOrdenId);
        nuevaOrden.setFechaIni(CurrentTimeSQL.getCurrentDate());
        nuevaOrden.setNoStock(false); /*Falta comprobar*/
        nuevaOrden.setPagado(true);
        nuevaOrden.setPrecioTotal(Float.parseFloat(priceTotal));
        nuevaOrden.setIdFarmacista(1);
        nuevaOrden.setIdPaciente(idPaciente);
        nuevaOrden.setIdTipo(1);
        nuevaOrden.setIdEstado(1);
        nuevaOrden.setIdSede(1);
        nuevaOrden.setIdDoctor(1);

        ordenRepository.save(nuevaOrden);

        for (int i = 0; i < listaFinalMedicamentos.size(); i++){
            OrdenContenido contenido = new OrdenContenido();
            contenido.setIdOrden(nuevaOrdenId);
            contenido.setIdMedicamento(listaFinalMedicamentos.get(i).getIdMedicamento());
            contenido.setCantidad(Integer.parseInt(listaFinalCantidades.get(i)));
            ordenContenidoRepository.save(contenido);
        }

        model.addAttribute("idOrden",nuevaOrdenId);
        return "redirect:/farmacista/ver_orden_venta";
    }

    @GetMapping("/farmacista/ver_orden_venta")
    public String verOrdenesVenta(@RequestParam(value = "idOrden", required = false) Integer nuevaOrdenId, Model model) {

        if (nuevaOrdenId != null){
            model.addAttribute("ordenVenta", ordenRepository.findById(nuevaOrdenId));
            return "/farmacista/ver_orden_venta";
        } else {
            return "/farmacista/errorPages/no_existe_orden";
        }


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
