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

import java.util.*;
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
    Integer ordenIdFind;

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

        return "redirect:/farmacista/formulario_paciente";
    }

    @GetMapping("/farmacista/formulario_paciente")
    public String forPaciente(Model model) {
        model.addAttribute("medicamentosSeleccionados", medicamentosSeleccionados);
        model.addAttribute("listaCantidades", listaCantidades);
        return "/farmacista/formulario_paciente";
    }

    @PostMapping("/farmacista/finalizar_compra")

    public String finalizarCompra(@RequestParam(value = "name") String name,
                                  @RequestParam(value = "lastname") String lastname,
                                  @RequestParam(value = "dni") String dni,
                                  @RequestParam(value = "distrito") String distrito,
                                  @RequestParam(value = "direccion") String direccion,
                                  @RequestParam(value = "doctor") String doctor,
                                  @RequestParam(value = "seguro") String seguro,
                                  @RequestParam(value = "correo") String correo,

                                  @RequestParam(value = "listaIds") List<String> listaSelectedIds,
                                  @RequestParam(value = "priceTotal") String priceTotal) {

        ordenIdFind = 0;

        ArrayList<Optional<Medicamento>> listaFinalMedicamentosOpt = new ArrayList<>();
        ArrayList<String> listaFinalCantidades = new ArrayList<>();

        for (int i = 0; i < listaSelectedIds.size(); i += 2) {
            listaFinalMedicamentosOpt.add(medicamentoRepository.findById(Integer.valueOf(listaSelectedIds.get(i))));
        }

        for (int i = 0; i + 1 < listaSelectedIds.size(); i += 2) {
            listaFinalCantidades.add(listaSelectedIds.get(i + 1));
        }

        ArrayList<Medicamento> listaFinalMedicamentos = (ArrayList<Medicamento>) listaFinalMedicamentosOpt.stream().flatMap(Optional::stream).collect(Collectors.toList());


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
            pacienteOrden.setDireccion(direccion);
            pacienteOrden.setDistrito(distrito);
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

            if (ordenContenidoRepository.findLastOrdenContenidoId() != null){
                contenido.setIdEntrada(ordenContenidoRepository.findLastOrdenContenidoId()+1);
            }
            contenido.setIdOrden(nuevaOrdenId);
            contenido.setIdMedicamento(listaFinalMedicamentos.get(i).getIdMedicamento());
            contenido.setCantidad(Integer.parseInt(listaFinalCantidades.get(i)));
            ordenContenidoRepository.save(contenido);
        }

        ordenIdFind = nuevaOrdenId;

        return "redirect:/farmacista/ver_orden_venta";
    }

    @GetMapping("/farmacista/ver_orden_venta")
    public String verOrdenesVenta(Model model) {

        if (ordenIdFind != null){

            Optional<Orden> ordenOptional = ordenRepository.findById(ordenIdFind);

            if (ordenOptional.isPresent()){
                Orden ordenComprobada = ordenOptional.get();

                Optional<Usuario> usuarioOptional = usuarioRepository.findById(ordenComprobada.getIdPaciente());
                Usuario usuarioComprobado = usuarioOptional.get();
                ArrayList<OrdenContenido> medicamentosOnOrden = ordenContenidoRepository.findAllByIdOrden(ordenIdFind);
                ArrayList<Optional<Medicamento>> medicamentosListOptional = new ArrayList<>();

                for (OrdenContenido cont : medicamentosOnOrden){
                    Integer idMed = cont.getIdMedicamento();
                    medicamentosListOptional.add(medicamentoRepository.findById(idMed));
                }
                ArrayList<Medicamento> medicamentosList = (ArrayList<Medicamento>) medicamentosListOptional.stream().flatMap(Optional::stream).collect(Collectors.toList());

                model.addAttribute("OrdenComprobada", ordenComprobada);
                model.addAttribute("usuarioComprobado", usuarioComprobado);
                model.addAttribute("medicamentosOnOrden", medicamentosOnOrden);
                model.addAttribute("medicamentosList", medicamentosList);
                return "/farmacista/ver_orden_venta";
            }
        }
        return "/farmacista/errorPages/no_existe_orden";
    }


    @GetMapping("/farmacista/ordenes_venta")
    public String OrdenesVenta(Model model) {

        List<Orden> listaOrdenes = ordenRepository.findAll();
        List<Integer> IdsUsuarios = new ArrayList<>();

        for (Orden orden : listaOrdenes){
            IdsUsuarios.add(orden.getIdPaciente());
        }

        List<Optional<Usuario>> listaUsuariosOptional = new ArrayList<>();

        for (Integer idU : IdsUsuarios){
            listaUsuariosOptional.add(usuarioRepository.findById(idU));
        }

        ArrayList<Usuario> listaUsuarios = (ArrayList<Usuario>) listaUsuariosOptional.stream().flatMap(Optional::stream).collect(Collectors.toList());

        model.addAttribute("listaOrdenes",listaOrdenes);
        model.addAttribute("listaUsuarios",listaUsuarios);
        return "/farmacista/ordenes_venta";
    }

    @PostMapping("/farmacista/ver_orden_venta_tabla")
    public String verOrdenesVentaTabla(@RequestParam(value = "idOrden") String idOrdenTabla){

        ordenIdFind = Integer.valueOf(idOrdenTabla);

        return "redirect:/farmacista/ver_orden_venta";
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
