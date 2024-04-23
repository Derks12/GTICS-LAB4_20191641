package pe.sanmiguel.bienestar.proyecto_gtics.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pe.sanmiguel.bienestar.proyecto_gtics.CurrentTimeSQL;
import pe.sanmiguel.bienestar.proyecto_gtics.Entity.*;
import pe.sanmiguel.bienestar.proyecto_gtics.Repository.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class FarmacistaController {


    /* Repositorios */
    final UsuarioRepository usuarioRepository;
    final MedicamentoRepository medicamentoRepository;
    final OrdenRepository ordenRepository;
    final OrdenContenidoRepository ordenContenidoRepository;
    final ReposicionRepository reposicionRepository;
    final  EstadoPreOrdenRepository estadoPreOrdenRepository;

    public FarmacistaController(UsuarioRepository usuarioRepository, MedicamentoRepository medicamentoRepository, OrdenRepository ordenRepository, OrdenContenidoRepository ordenContenidoRepository, ReposicionRepository reposicionRepository,EstadoPreOrdenRepository estadoPreOrdenRepository) {
        this.usuarioRepository = usuarioRepository;
        this.medicamentoRepository = medicamentoRepository;
        this.ordenRepository = ordenRepository;
        this.ordenContenidoRepository = ordenContenidoRepository;
        this.reposicionRepository=reposicionRepository;
        this.estadoPreOrdenRepository=estadoPreOrdenRepository;
    }

    /* Repositorios */


    /* Variables Internas */

    List<Medicamento> medicamentosSeleccionados = new ArrayList<>();
    List<String> listaCantidades = new ArrayList<>();

    Integer idVerOrdenCreada;

    /* Variables Internas */



    @GetMapping("/farmacista")
    public String farmacistaInicio(Model model) {
        List<Medicamento> listaMedicamentos = medicamentoRepository.findAll();
        model.addAttribute("listaMedicamentos", listaMedicamentos);
        return "/farmacista/inicio";
    }

    /*@PostMapping("/farmacista/continuar_compra")
    public String fillContentOrder(@RequestParam("listaIds") List<String> listaSelectedIds){
        if (!listaSelectedIds.isEmpty()){
            medicamentosSeleccionados = fillMedicamentosFromLista(listaSelectedIds);
            listaCantidades = fillCantidadesFromLista(listaSelectedIds);
            return "redirect:/farmacista/formulario_paciente";
        } else {
            return "redirect:/farmacista";
        }
    }*/

    /*@GetMapping("/farmacista/formulario_paciente")
    public String formPacienteData(Model model) {
        model.addAttribute("medicamentosSeleccionados", medicamentosSeleccionados);
        model.addAttribute("listaCantidades", listaCantidades);
        return "/farmacista/formulario_paciente";
    }*/

    /*@PostMapping("/farmacista/finalizar_compra")

    public String createOrdenVenta(@RequestParam(value = "name") String name,
                                   @RequestParam(value = "lastname") String lastname,
                                   @RequestParam(value = "dni") String dni,
                                   @RequestParam(value = "distrito") String distrito,
                                   @RequestParam(value = "direccion") String direccion,
                                   @RequestParam(value = "doctor") String doctor,
                                   @RequestParam(value = "seguro") String seguro,
                                   @RequestParam(value = "correo") String correo,
                                   @RequestParam(value = "telefono") String telefono,

                                   @RequestParam(value = "listaIds") List<String> listaSelectedIds,
                                   @RequestParam(value = "priceTotal") String priceTotal) {


        //crearOrdenVentaFromData(name, lastname, dni, distrito, direccion, telefono, doctor, seguro, correo, listaSelectedIds, priceTotal);

        return "redirect:/farmacista/ver_orden_venta";
    }*/

    @PostMapping("/farmacista/ver_orden_venta_tabla")
    public String verOrdenesVentaTabla(@RequestParam(value = "idOrden") String idOrdenTabla){

        idVerOrdenCreada = Integer.valueOf(idOrdenTabla);

        return "redirect:/farmacista/ver_orden_venta";
    }

    /*@GetMapping("/farmacista/verOrdenVenta")
    public String verOrdenVenta(Model model) {

        Optional<Orden> ordenOptional = ordenRepository.findById(idVerOrdenCreada);

        if (ordenOptional.isPresent()){
            Orden ordenComprobada = ordenOptional.get();
            getOrdenVenta(model, ordenComprobada);
            return "/farmacista/ver_orden_venta";
        } else {

            return "/farmacista/errorPages/no_existe_orden";
        }
    }*/
    @GetMapping("/verOrdenVenta")
    public String verOrdenVenta(Model model,
                              @RequestParam("id") Integer id) {
        Optional<Orden> optionalOrden = ordenRepository.findById(id);
        if(optionalOrden.isPresent()){
            Orden ordenVenta = optionalOrden.get();
            model.addAttribute("ordenVenta", ordenVenta);
            return "farmacista/ver_orden_venta";
        }else {
            return  "redirect:/farmacista/ordenes_venta";
        }

    }
    @GetMapping("/farmacista/ordenes_venta")
    public String tablaOrdenesVenta(Model model) {
        List<Orden> listaOrdenesVenta = ordenRepository.findAll();
        model.addAttribute("listaOrdenesVenta", listaOrdenesVenta);
        return "/farmacista/ordenes_venta";
    }

    @GetMapping("/farmacista/ordenes_web")
    public String tablaOrdenesWeb(Model model) {
        List<Orden> listaOrdenesWeb = ordenRepository.findAllOrdenesWeb();
        model.addAttribute("listaOrdenesWeb", listaOrdenesWeb);
        return "/farmacista/ordenes_web";
    }
    @GetMapping("/verOrdenWeb")
    public String verOrdenWeb(Model model,
                              @RequestParam("id") Integer id) {
        Optional<Orden> optionalOrden = ordenRepository.findById(id);
        if(optionalOrden.isPresent()){
            Orden ordenWeb = optionalOrden.get();
            model.addAttribute("ordenWeb", ordenWeb);
            return "farmacista/ver_orden_web";
        }else {
            return  "redirect:/farmacista/ordenes_web";
        }

    }
    @GetMapping("/farmacista/pre_ordenes")
    public String tablaPreOrdenes(Model model) {
        List<Orden> listaPreOrdenes = ordenRepository.findAllPreOrdenes();
        model.addAttribute("listaPreOrdenes", listaPreOrdenes);
        return "/farmacista/pre_ordenes";
    }
    @GetMapping("/verPreOrden")
    public String verPreOrden(Model model,
                              @RequestParam("id") Integer id) {
        Optional<Orden> optionalOrden = ordenRepository.findById(id);
        if(optionalOrden.isPresent()){
            Orden preorden = optionalOrden.get();
            model.addAttribute("preorden", preorden);
            return "farmacista/ver_pre_orden";
        }else {
            return  "redirect:/farmacista/pre_ordenes";
        }

    }
    @GetMapping("/farmacista/perfil")
    public String profile() {
        return "/farmacista/perfil";
    }
    @GetMapping("/farmacista/facturacion")
    public String facturacion() {
        return "/farmacista/facturacion";
    }
    @GetMapping("/farmacista/cambioContraseña")
    public String cambioContra() {
        return "/farmacista/cambioContraseña";
    }




    /*public void listOrdenesVenta(Model model){
        List<Orden> listaOrdenes = ordenRepository.findBySedeId(1); //Adaptar segun session del farmacista
        List<Integer> IdsUsuarios = new ArrayList<>();

        for (Orden orden : listaOrdenes){
            IdsUsuarios.add(orden.getIdPaciente());
        }

        List<Optional<Usuario>> listaUsuariosOptional = new ArrayList<>();

        for (Integer idU : IdsUsuarios){
            listaUsuariosOptional.add(usuarioRepository.findById(idU));
        }

        ArrayList<Usuario> listaUsuarios = listaUsuariosOptional.stream().flatMap(Optional::stream).collect(Collectors.toCollection(ArrayList::new));

        model.addAttribute("listaOrdenes", listaOrdenes);
        model.addAttribute("listaUsuarios", listaUsuarios);
    }


    public void getOrdenVenta(Model model, Orden ordenComprobada){
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(ordenComprobada.getIdPaciente());
        Usuario usuarioComprobado = usuarioOptional.get();
        ArrayList<OrdenContenido> medicamentosOnOrden = ordenContenidoRepository.findAllByIdOrden(idVerOrdenCreada);
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
    }


    public List<Medicamento> fillMedicamentosFromLista(List<String> listaSelectedIds) {
        List<Optional<Medicamento>> optionals = new ArrayList<>();
        List<Medicamento> seleccionados;
        for (int i = 0; i < listaSelectedIds.size(); i += 2) {
            optionals.add(medicamentoRepository.findById(Integer.valueOf(listaSelectedIds.get(i))));
        }
        seleccionados = optionals.stream().flatMap(Optional::stream).collect(Collectors.toList());

        return seleccionados;
    }

    public List<String> fillCantidadesFromLista(List<String> listaSelectedIds) {
        List<String> cantidades = new ArrayList<>();
        for (int i = 0; i + 1 < listaSelectedIds.size(); i += 2) {
            cantidades.add(listaSelectedIds.get(i + 1));
        }
        return cantidades;
    }

    /*public void crearOrdenVentaFromData(String name, String lastname, String dni, String distrito, String direccion, String telefono, String doctor, String seguro, String correo, List<String> listaSelectedIds, String priceTotal){

        idVerOrdenCreada = 0;

        List<Medicamento> medicamentosDeOrden;
        List<String> cantidadesDeOrden;

        medicamentosDeOrden = fillMedicamentosFromLista(listaSelectedIds);
        cantidadesDeOrden = fillCantidadesFromLista(listaSelectedIds);


        Usuario pacienteOrden = new Usuario();

        if (usuarioRepository.findByCorreoAndDni(correo, dni) != null){
            pacienteOrden = usuarioRepository.findByCorreoAndDni(correo, dni);

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
        /*nuevaOrden.setPagado(true);
        nuevaOrden.setPrecioTotal(Float.parseFloat(priceTotal));
        nuevaOrden.setIdFarmacista(1);
        nuevaOrden.setIdPaciente(idPaciente);
        nuevaOrden.setIdTipo(1);
        nuevaOrden.setIdEstado(1);
        nuevaOrden.setIdSede(1);
        nuevaOrden.setIdDoctor(1);

        ordenRepository.save(nuevaOrden);

        for (int i = 0; i < medicamentosDeOrden.size(); i++){
            OrdenContenido contenido = new OrdenContenido();

            if (ordenContenidoRepository.findLastOrdenContenidoId() != null){
                contenido.setIdEntrada(ordenContenidoRepository.findLastOrdenContenidoId()+1);
            }
            contenido.setIdOrden(nuevaOrdenId);
            contenido.setIdMedicamento(medicamentosDeOrden.get(i).getIdMedicamento());
            contenido.setCantidad(Integer.parseInt(cantidadesDeOrden.get(i)));
            ordenContenidoRepository.save(contenido);
        }

        idVerOrdenCreada = nuevaOrdenId;
    }*/


}


